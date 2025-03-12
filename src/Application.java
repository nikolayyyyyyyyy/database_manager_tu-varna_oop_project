import interfaces.Engine;
import models.common.BaseFileValidator;
import models.common.EngineImpl;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        BaseFileValidator.createDirectoryIfNotExist("catalog");

        Engine engine = new EngineImpl();
        engine.run();
    }
}
