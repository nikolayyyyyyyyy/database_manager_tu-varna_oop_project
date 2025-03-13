import interfaces.Engine;
import models.common.BaseFileValidator;
import models.common.DatabaseEngine;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        BaseFileValidator.createDirectoryIfNotExist("catalog");

        Engine databaseEngine = new DatabaseEngine();
        databaseEngine.run();
    }
}
