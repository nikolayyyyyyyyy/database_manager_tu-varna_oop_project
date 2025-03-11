package models.common;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseFileValidator {
    private static final Path BASE
            = Path.of("catalog");

    public static boolean isFileExist(String fileName){
        return BASE.resolve(fileName).toFile().exists();
    }

    public static void createDirectoryIfNotExist(String directory) throws IOException {

        if(Files.notExists(BASE)){
            Files.createDirectory(BASE);
        }
    }

    public static Path getBase(){
        return BASE;
    }
}
