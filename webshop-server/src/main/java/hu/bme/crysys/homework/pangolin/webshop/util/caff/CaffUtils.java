package hu.bme.crysys.homework.pangolin.webshop.util.caff;

import hu.bme.crysys.homework.pangolin.webshop.model.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.codec.Hex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public final class CaffUtils {

    private static final String VALID_PARSER_BINARY_SHA3_256_CHECKSUM = "30ad1ef56d8bebd6bfbe05ba39733b326618c9f294f9c74aee6c24a6d3bd187e";
    private static final String PARSER_BINARY_LOCATION = "./parser/parser";

    private CaffUtils() { }

    public static String getFilePreview(final File file) {
        final Path caffFilePath = Path.of(file.getLocation(), file.getFileName());
        try {
            checkParserIntegrity();

            final ProcessBuilder pb = new ProcessBuilder(PARSER_BINARY_LOCATION, caffFilePath.toString());
            pb.redirectErrorStream(true);
            final Process parser = pb.start();
            parser.waitFor();

            return readAndDeleteFilePreview(file);
        } catch (Exception e) {
            log.error("Error during caff/ciff preview parsing!");
            log.error(e.getMessage());
            return null;
        }
    }

    public static void checkParserIntegrity() throws IOException, NoSuchAlgorithmException {
        final byte[] parserBinary = Files.readAllBytes(Path.of(PARSER_BINARY_LOCATION));
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final String actualParserBinarySha3 = new String(Hex.encode(digest.digest(parserBinary)));

        if (!VALID_PARSER_BINARY_SHA3_256_CHECKSUM.equalsIgnoreCase(actualParserBinarySha3)) {
            throw new RuntimeException("The integrity of the parser binary has been violated!");
        }
    }

    private static String readAndDeleteFilePreview(final File file) throws IOException {
        final Path path = Path.of(file.getLocation(), file.getFileName() + ".gif");
        final String previewAsString = Base64.encodeBase64String(Files.readAllBytes(path));
        Files.delete(path);

        return previewAsString;
    }

}
