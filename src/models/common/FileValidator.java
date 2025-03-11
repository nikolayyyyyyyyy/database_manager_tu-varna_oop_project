package models.common;
import java.nio.file.Path;

public class FileValidator {
    private static final Path BASE
            = Path.of("catalog");

    public static boolean isFileExist(String fileName){
        return BASE.resolve(fileName).toFile().exists();
    }
}
