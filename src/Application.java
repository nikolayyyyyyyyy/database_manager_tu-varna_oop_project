import interfaces.Engine;
import models.common.DatabaseEngine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

    public static void main(String[] args) throws IOException {
        if(Files.notExists(Path.of("catalog"))){
            Files.createDirectory(Path.of("catalog"));
        }

        Engine databaseEngine = new DatabaseEngine();
        databaseEngine.run();
    }
}
