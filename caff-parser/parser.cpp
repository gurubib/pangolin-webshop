#include <iostream>
#include <fstream>
#include<vector>
#include <regex>

#define MAX_FILE_SIZE 1000000000

// Block IDs
#define CAFF_HEADER 0x1
#define CAFF_CREDITS 0x2
#define CAFF_ANIMATION 0x3

using namespace std;

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
        cout << "Size: " << header_size << ", Anim: " << num_anim << endl;
    }

    void parse(char *input_buffer, int &bytes_read) {
        char integer_of_8_bytes[8];

        // Header Magic
        char magic_value[4 + 1];
        strlcpy(magic_value, input_buffer + bytes_read, sizeof(magic_value));
        bytes_read += 4;

        if (strcmp(magic_value, "CAFF") != 0) {
            cerr << "Incorrect magic value!";
            exit(1);
        }
        
        // Header Size
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        header_size = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // Num Anim
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        num_anim = parseInt64(integer_of_8_bytes);
        bytes_read += 8;
    }
};

struct Credits {
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int64_t creator_len;
    char creator[1000]; // Should be large enough (for testing, maybe won't be needed as)

    void print() {
        // For testing purposes only!
        cout << "--- CREDITS ---" << endl;
        cout << "Date: " << year << "." << month << "." << day << " " << hour << ":" << minute << endl;
        cout << "Creator: " << creator << endl;
    }

    void parse(char *input_buffer, int &bytes_read) {
        char integer_of_8_bytes[8];

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
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        creator_len = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // Creator
        strlcpy(creator, input_buffer + bytes_read, creator_len + 1);
        bytes_read += creator_len;
    }

};

struct Animation {
    int64_t duration;
    int64_t header_size;
    int64_t content_size;
    int64_t width;
    int64_t height;

    void print() {
        // For testing purposes only!
        cout << "--- ANIMATION ---" << endl;
        cout << "Duration: " << duration << ", Height: " << height << ", Width: " << width << endl;
    }

    void parse(char *input_buffer, int &bytes_read) {
        char integer_of_8_bytes[8];

        // Duration
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        duration = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // Header Magic
        char magic_value[4 + 1];
        strlcpy(magic_value, input_buffer + bytes_read, sizeof(magic_value));
        bytes_read += 4;

        if (strcmp(magic_value, "CIFF") != 0) {
            cerr << "Incorrect magic value!";
            exit(1);
        }

        // Header Size
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        header_size = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // Content Size
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        content_size = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // Width
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        width = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        // Height
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        height = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        if (content_size != width*height*3) {
            cerr << "Incorrect animation content size!";
            exit(1);
        }

        // Caption
        int caption_size = 0;
        for (int i = 0; i < header_size; i++) {
            if (input_buffer[bytes_read + i] == '\n') {
                caption_size = i;
                break;
            }
        }

        char caption[caption_size + 1];
        strncpy(caption, input_buffer + bytes_read, caption_size + 1);
        caption[caption_size] = '\0';
        bytes_read += caption_size + 1; // We copy and replace the \n also!

        //cout << "Caption Size: " << caption_size << ", " << "Caption: " << caption << endl;

        // Tags
        int tag_number = 0;
        for (int i = 0; i < header_size; i++) {
            if (input_buffer[bytes_read + i] == '\0') {
                tag_number++;
            }
        }

        vector<string> tags;
        for (int i = 0; i < tag_number; i++) {
            int tag_size = 0;
            for (int j = 0; j < header_size; j++) {
                if (input_buffer[bytes_read + j] == '\0') {
                    tag_size = j;
                    char tag[tag_size + 1];
                    strncpy(tag, input_buffer + bytes_read, tag_size + 1); // We copy the \0 also!
                    tags.push_back(tag);
                    bytes_read += tag_size + 1;
                    break;
                }
            }
        }

        //cout << "Tag Number: " << tag_number << endl;

        for (int i = 0; i < tags.size(); i++) {
            //cout << "Tag" << i << ": " << tags[i] << endl;
        }

        // Pixels
        vector<Pixel> pixels;
        int i = 0;
        while (i < content_size - 2) {
            Pixel pixel(input_buffer[bytes_read + i], input_buffer[bytes_read + i + 1], input_buffer[bytes_read + i + 2]);
            pixels.push_back(pixel);
            i += 3;
        }
        bytes_read += content_size;

        //cout << "Pixels:" << endl;

        for (int i = 0; i < pixels.size(); i++) {
            //cout << "RGB(" << pixels[i].R << ", " << pixels[i].G << ", " << pixels[i].B << ")" << endl;
        }
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
    int input_file_size = input_file.tellg();
    input_file.seekg(0, ios::beg);

    if (input_file_size >= MAX_FILE_SIZE) {
        cerr << "File is too large! The size should be less than " << MAX_FILE_SIZE << " bytes!";
        exit(1);
    }
    
    char input_buffer[input_file_size];
    input_file.read(input_buffer, input_file_size);

    int bytes_read = 0; // Counts the number of bytes read so far

    // Temp Buffers
    char block_id;
    char integer_of_8_bytes[8];
    int64_t block_length;

    /*
     * Read 1st block (Header)
     */
    
    // Block ID
    block_id = input_buffer[bytes_read];
    bytes_read += 1;

    if (block_id != CAFF_HEADER) {
        cerr << "A CAFF file should always start with a header!";
        exit(1);
    }

    // Block Length
    strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
    block_length = parseInt64(integer_of_8_bytes);
    bytes_read += 8;

    Header header;
    header.parse(input_buffer, bytes_read);
    header.print();

    /*
     * Read rest of the Blocks (Credits or Animations)
     */

    Credits credits;
    Animation animations[header.num_anim];

    int animation_index = 0;

    // There should be exactly 1 Credits Block and num_anim Animation Blocks left
    for (int i = 0; i < header.num_anim + 1; i++) {
        // Block ID
        block_id = input_buffer[bytes_read];
        bytes_read += 1;

        // Block Length
        strncpy(integer_of_8_bytes, input_buffer + bytes_read, sizeof(integer_of_8_bytes));
        block_length = parseInt64(integer_of_8_bytes);
        bytes_read += 8;

        if (block_id == CAFF_CREDITS) {
            credits.parse(input_buffer, bytes_read);
            credits.print();
        }
        else if (block_id == CAFF_ANIMATION) {
            Animation animation;
            animation.parse(input_buffer, bytes_read);
            animation.print();
            animations[animation_index++] = animation;
        }
        else {
            cout << "BLOCK ID: " << hex << (int)(unsigned char)block_id;
            cerr << "Invalid file format! A header must be followed by a Credits or an Animation Block!";
            exit(1);
        }
    }

    return 0;
}