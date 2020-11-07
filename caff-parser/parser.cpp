#include <iostream>
#include <fstream>
#include <vector>
#include <regex>
#include <Magick++.h>

// Reasonable upper values
#define MAX_FILE_SIZE 1000000000 // 1 GB
#define MAX_CREATOR_LEN 10001 // 10000 Chars + '\0'
#define MAX_CAPTION_SIZE 10001 // 10000 Chars + '\0'
#define MAX_NUMER_OF_ANIMATIONS 1000000 // Reasonable?
#define MAX_DURATION_LENGTH 6000 // 1 Minute
#define MAX_CIFF_HEADER_SIZE 100000 // Meta + Caption + Tags
#define MAX_CIFF_CONTENT_SIZE 30000000 // Allows for 10.000.000 pixels in an image
#define MAX_TAGS_LENGTH 10000 // 10000 Chars altogether, including the '\0'-s

// Block IDs
#define CAFF_HEADER 0x1
#define CAFF_CREDITS 0x2
#define CAFF_ANIMATION 0x3

// The actual file size
unsigned int FILE_SIZE = 0;

using namespace std;
using namespace Magick;

int64_t parseInt64(char bytes[8]) {
    int64_t value = 0;
    for (int i = 0; i < 8; i++) {
        value = value | (unsigned char)(bytes[i]) << (i * 8);
    }
    return value;
}

struct Pixel {
    int R;
    int G;
    int B;

    Pixel(char R, char G, char B) {
        this->R = (unsigned char)R;
        this->G = (unsigned char)G;
        this->B = (unsigned char)B;
    }
};

struct Header {
    int64_t header_size;
    int64_t num_anim;

    void print() {
        // For testing purposes only!
        cout << "--- HEADER ---" << endl;
        cout << "Size: " << header_size << ", Anim: " << num_anim << endl << endl;
    }

    void parse(char *input_buffer, unsigned int &bytes_read) {
        char integer_of_8_bytes[8];

        // Header Magic
        if (bytes_read + 4 > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        char magic_value[4 + 1];
        strncpy(magic_value, input_buffer + bytes_read, sizeof(magic_value));
        magic_value[4] = '\0';
        bytes_read += 4;

        if (strcmp(magic_value, "CAFF") != 0) {
            cerr << "Incorrect magic value!";
            exit(1);
        }

        // Header Size
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        header_size = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        if (header_size != 20) {
            // Header should be exactly 4 + 8 + 8 bytes long!
            cerr << "Incorrect header format!";
            exit(1);
        }

        // Num Anim
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        num_anim = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        if (num_anim >= MAX_NUMER_OF_ANIMATIONS) {
            cerr << "The number of animation blocks should be less than " << MAX_NUMER_OF_ANIMATIONS << "!";
            exit(1);
        }
    }
};

struct Credits {
    int year;
    int month;
    int day;
    int hour;
    int minute;
    char creator[MAX_CREATOR_LEN];

    void print() {
        // For testing purposes only!
        cout << "--- CREDITS ---" << endl;
        cout << "Date: " << year << "." << month << "." << day << " " << hour << ":" << minute << endl;
        cout << "Creator: " << creator << endl << endl;
    }

    void parse(char *input_buffer, unsigned int &bytes_read) {
        char integer_of_8_bytes[8];

        // Checking for EOF in case of YYMDhm together.
        if (bytes_read + 6 > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        // Credits Year
        year = (unsigned char)(input_buffer[bytes_read]) | (unsigned char)(input_buffer[bytes_read + 1]) << 8;
        bytes_read += 2;

        // Credits Month
        month = input_buffer[bytes_read];
        bytes_read += 1;

        // Credits Day
        day = input_buffer[bytes_read];
        bytes_read += 1;

        // Credits Hour
        hour = input_buffer[bytes_read];
        bytes_read += 1;

        // Credits Minute
        minute = input_buffer[bytes_read];
        bytes_read += 1;

        // Creator Len
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        int64_t creator_len;
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        creator_len = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        if (creator_len >= MAX_CREATOR_LEN - 1) { // Space for the '\0'!
            cerr << "The length of the creator should be less than " << MAX_CREATOR_LEN - 1 << " characters!";
            exit(1);
        }

        // Creator
        if (bytes_read + creator_len > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(creator, input_buffer + bytes_read, creator_len);
        creator[creator_len] = '\0';
        bytes_read += creator_len;
    }

};

struct Animation {
    int64_t duration;
    int64_t header_size;
    int64_t content_size;
    int64_t width;
    int64_t height;
    char caption[MAX_CAPTION_SIZE];
    vector<string> tags;
    vector<Pixel> pixels;

    void print() {
        // For testing purposes only!
        cout << "--- ANIMATION ---" << endl;
        cout << "Duration: " << duration << ", Height: " << height << ", Width: " << width << endl;
        cout << "Caption: " << caption << endl;

        for (unsigned int i = 0; i < tags.size(); i++) {
            cout << "Tag" << i << ": " << tags[i] << endl;
        }

        cout << "First 2 Pixels:" << endl;

        for (unsigned int i = 0; i < pixels.size(); i++) {
            cout << "RGB(" << pixels[i].R << ", " << pixels[i].G << ", " << pixels[i].B << ")" << endl;
            if (i == 1) {
                break;
            }
        }

        cout << endl;
    }

    void parse(char *input_buffer, unsigned int &bytes_read) {
        char integer_of_8_bytes[8];

        // Duration
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        duration = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        if (duration >= MAX_DURATION_LENGTH) {
            cerr << "The duration of an animation should be less than " << MAX_DURATION_LENGTH << "ms!";
            exit(1);
        }

        // Header Magic
        if (bytes_read + 4 > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        char magic_value[4 + 1];
        strncpy(magic_value, input_buffer + bytes_read, sizeof(magic_value));
        magic_value[4] = '\0';
        bytes_read += 4;

        if (strcmp(magic_value, "CIFF") != 0) {
            cerr << "Incorrect magic value!";
            exit(1);
        }

        // Header Size
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        header_size = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // TODO: Max Header Size?
        if (header_size >= MAX_CIFF_HEADER_SIZE) {
            cerr << "The size of the CIFF header should be less than " << MAX_CIFF_HEADER_SIZE << " bytes!";
            exit(1);
        }

        // Content Size
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        content_size = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // TODO: Max Content Size?
        if (header_size >= MAX_CIFF_CONTENT_SIZE) {
            cerr << "The size of the CIFF content should be less than " << MAX_CIFF_CONTENT_SIZE << " bytes!";
            exit(1);
        }

        // Width
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        width = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // Height
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        height = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // This also verifies that if the width or the height is 0, then no pixels are present.
        if (content_size != width*height*3) {
            cerr << "Incorrect animation content size!";
            exit(1);
        }

        // Caption
        int caption_size = 0;
        for (int i = 0; i < header_size; i++) {
            if (bytes_read + i >= FILE_SIZE) {
                cerr << "Error, unexpectedly reached EOF!";
                exit(1);
            }
            if (input_buffer[bytes_read + i] == '\n') {
                caption_size = i;
                break;
            }
            // If there's no '\n' within header_size characters, then there's no caption.
        }

        if (caption_size >= MAX_CAPTION_SIZE - 1) { // Space for the '\0'!
            cerr << "The length of the caption should be less than " << MAX_CAPTION_SIZE - 1 << " characters!";
            exit(1);
        }

        if (bytes_read + caption_size > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(caption, input_buffer + bytes_read, caption_size);
        caption[caption_size] = '\0';
        bytes_read += caption_size;

        if (caption_size != 0) {
            bytes_read += 1; // Skip the '\n' because we didn't copy it!
        }

        // Tags
        int tag_number = 0;
        for (int i = 0; i < header_size; i++) {
            if (bytes_read + i >= FILE_SIZE) {
                cerr << "Error, unexpectedly reached EOF!";
                exit(1);
            }
            if (input_buffer[bytes_read + i] == '\0') {
                tag_number++;
            }
        }

        for (int i = 0; i < tag_number; i++) {
            int tag_size = 0;
            for (int j = 0; j < header_size; j++) {
                if (bytes_read + j >= FILE_SIZE) {
                    cerr << "Error, unexpectedly reached EOF!";
                    exit(1);
                }
                if (input_buffer[bytes_read + j] == '\0') {
                    tag_size = j;
                    char tag[tag_size + 1];
                    strncpy(tag, input_buffer + bytes_read, tag_size + 1); // We copy the '\0'-s also!
                    tags.push_back(tag);
                    bytes_read += tag_size + 1;
                    break;
                }
            }
        }

        // Pixels
        int i = 0;
        while (i < content_size - 2) {
            if (bytes_read + i >= FILE_SIZE || bytes_read + i + 1 >= FILE_SIZE || bytes_read + i + 2>= FILE_SIZE) {
                cerr << "Error, unexpectedly reached EOF!";
                exit(1);
            }
            Pixel pixel(input_buffer[bytes_read + i], input_buffer[bytes_read + i + 1], input_buffer[bytes_read + i + 2]);
            pixels.push_back(pixel);
            i += 3;
        }
        bytes_read += content_size;
    }
};

int main(int argc, char *argv[]) {

    if (argc < 2) {
        cerr << "File name not provided!";
        exit(1);
    }

    if (argc > 2) {
        cerr << "Too many arguments provided!";
        exit(1);
    }

    string file_name = argv[1];
    if (!regex_match(file_name, regex("^(.*).caff$"))) {
        cerr << "Invalid file name!";
        exit(1);
    }

    ifstream input_file(file_name, ios::in | ios::binary);
    if (!input_file.good()) {
        cerr << "File with the given name does not exists!";
        exit(1);
    }

    input_file.seekg(0, ios::end);
    FILE_SIZE = input_file.tellg();
    input_file.seekg(0, ios::beg);

    if (FILE_SIZE == 0) {
        // Well, this is a problem.
        cerr << "Error, the size of the file size is 0 bytes!";
        exit(1);
    }

    if (FILE_SIZE >= MAX_FILE_SIZE) {
        cerr << "File is too large! The size should be less than " << MAX_FILE_SIZE << " bytes!";
        exit(1);
    }

    char input_buffer[FILE_SIZE];
    input_file.read(input_buffer, FILE_SIZE);

    unsigned int bytes_read = 0; // Counts the number of bytes read so far.

    // Temp Buffers
    char block_id;
    char integer_of_8_bytes[8];
    int64_t block_length;

    /*
     * Read 1st block (Header)
     */

    // Block ID
    block_id = input_buffer[0];
    bytes_read += 1;

    if (block_id != CAFF_HEADER) {
        cerr << "A CAFF file should always start with a header!";
        exit(1);
    }

    // Block Length
    if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
        cerr << "Error, unexpectedly reached EOF!";
        exit(1);
    }

    strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
    block_length = parseInt64(integer_of_8_bytes);
    if (block_length < 0) {
        cerr << "Error!";
        exit(1);
    }
    bytes_read += 8;

    Header header;
    header.parse(input_buffer, bytes_read);

    /*
     * Read rest of the Blocks (Credits or Animations)
     */

    Credits credits;
    vector<Animation> animations;

    bool credits_block_read = false;
    int animation_blocks_read = 0;

    // There should be exactly 1 Credits Block and num_anim Animation Blocks left!
    for (int i = 0; i < header.num_anim + 1; i++) {
        // Block ID
        block_id = input_buffer[bytes_read];
        bytes_read += 1;

        // Block Length
        if (bytes_read + sizeof(integer_of_8_bytes) > FILE_SIZE) {
            cerr << "Error, unexpectedly reached EOF!";
            exit(1);
        }

        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        block_length = parseInt64(integer_of_8_bytes);
        if (block_length < 0) {
            cerr << "Error!";
            exit(1);
        }
        bytes_read += 8;

        if (block_id == CAFF_CREDITS) {
            if (credits_block_read) {
                cerr << "Invalid file format! There should be only one Credits Block!";
                exit(1);
            }
            credits.parse(input_buffer, bytes_read);
            credits_block_read = true;
        }
        else if (block_id == CAFF_ANIMATION) {
            Animation animation;
            animation.parse(input_buffer, bytes_read);
            animations.push_back(animation);
            animation_blocks_read++;
        }
        else {
            cout << "BLOCK ID: " << hex << (int)(unsigned char)block_id;
            cerr << "Invalid file format! A header must be followed by a Credits or an Animation Block!";
            exit(1);
        }
    }

    if (bytes_read != FILE_SIZE) {
        cerr << "Invalid file format, EOF was expected but data was found!";
        exit(1);
    }

    for (unsigned int i = bytes_read; i < FILE_SIZE; i++) {
        cout << input_buffer[bytes_read];
    }

    if (animation_blocks_read == 0) {
        cerr << "Invalid file format! There should be at least one Animation Block!";
        exit(1);
    }

    // PRINT (Debug)
    cout << "--------------------" << endl << "PARSING SUCCESSFUL:" << endl << "--------------------" << endl << endl;
    header.print();
    credits.print();
    for (unsigned int i = 0; i < animations.size(); i++) {
        animations[i].print();
    }

    /*
     * Create GIF from animations
     */
    InitializeMagick(nullptr);

    vector<Image> frames;

    for(unsigned int i = 0; i < animations.size(); i++) {
        const unsigned int width_ = animations[i].width;
        const unsigned int height_ = animations[i].height;
        std::string map_ = "RGB";
        const StorageType type_ = CharPixel;
        const unsigned int delay_ = animations[i].duration / 10;
        vector<unsigned char> pixels_;
        for(unsigned int j = 0; j < width_ * height_; j++){
            pixels_.push_back(animations[i].pixels[j].R);
            pixels_.push_back(animations[i].pixels[j].G);
            pixels_.push_back(animations[i].pixels[j].B);
        }
        const unsigned char *pixelsArray_ = &pixels_[0];
        Image frame(width_, height_, map_, type_, pixelsArray_);
        frame.animationDelay(delay_);
        frames.push_back(frame);
    }
    string gifFileName = file_name + ".gif";
    writeImages(frames.begin(), frames.end(), gifFileName);
    cout << "CAFF parsed to " << gifFileName << endl;
    
    return 0;
}
