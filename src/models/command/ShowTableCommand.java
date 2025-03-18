package models.command;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

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
