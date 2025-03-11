import interfaces.Engine;
import models.common.EngineIml;

public class Application {

    public static void main(String[] args) {

        Engine engine = new EngineIml();
        engine.run();
    }
}
