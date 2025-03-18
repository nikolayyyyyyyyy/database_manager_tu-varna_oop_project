package models.command;
import interfaces.Command;
import models.common.MessageLogger;

public class ExitCommand implements Command {
    @Override
    public void execute(String... command) {
        MessageLogger.log("Exiting the program...");
        System.exit(0);
    }
}
