import interfaces.Engine;
import models.common.EngineImpl;

public class Application {

    public static void main(String[] args) {

        Engine engine = new EngineImpl();
        engine.run();
    }
}
