import interfaces.Engine;
import models.EngineIml;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        Engine engine = new EngineIml();
        engine.run();
    }
}
