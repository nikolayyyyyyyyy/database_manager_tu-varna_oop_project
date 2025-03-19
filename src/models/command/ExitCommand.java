package models.command;
import interfaces.Command;
import models.common.MessageLogger;

/**
 * Клас, който имплементира командата за излизане от програмата.
 * Тази команда затваря приложението и изходи от него.
 */
public class ExitCommand implements Command {
    @Override
    public void execute(String... command) {
        MessageLogger.log("Exiting the program...");
        System.exit(0);
    }
}
