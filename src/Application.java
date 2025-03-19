import interfaces.Engine;
import models.common.DatabaseEngine;
import models.core.DatabaseImpl;

public class Application {

    public static void main(String[] args){

        Engine databaseEngine = new DatabaseEngine(new DatabaseImpl());
        databaseEngine.run();
    }
}
