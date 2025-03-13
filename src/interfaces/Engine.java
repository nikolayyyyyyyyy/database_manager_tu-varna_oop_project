package interfaces;

import java.util.Map;

public interface Engine extends Runnable{

    public void initCommands(Map<String, Command> commands);
}
