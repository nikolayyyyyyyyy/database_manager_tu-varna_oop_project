package models.command;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

/**
 * Тази команда показва списък на всички таблици, които са в момента отворени в базата данни.
 */
public class ShowTableCommand implements Command {
    private final Database database;

    public ShowTableCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {

        MessageLogger.log(this.database.printLoadedTables());
    }
}
