package hu.bme.crysys.homework.pangolin.webshop.util.caff;

import hu.bme.crysys.homework.pangolin.webshop.model.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public final class CaffUtils {

    private CaffUtils() { }

    public static String getFilePreview(final File file) {
        final Path path = Path.of(file.getLocation(), file.getFileName());
        try {
            final ProcessBuilder pb = new ProcessBuilder("/parser/parser", path.toString());
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

    private static String readAndDeleteFilePreview(final File file) throws IOException {
        final Path path = Path.of(file.getLocation(), file.getFileName() + ".gif");
        final String previewAsString = Base64.encodeBase64String(Files.readAllBytes(path));
        Files.delete(path);

        return previewAsString;
    }

}
