import interfaces.Engine;
import models.common.DatabaseEngine;
import models.core.DatabaseImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

    public static void main(String[] args){

        Engine databaseEngine = new DatabaseEngine(new DatabaseImpl());
        databaseEngine.run();
    }
}
