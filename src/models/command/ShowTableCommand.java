package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

public class ShowTableCommand implements Command {
    private final DatabaseManager databaseManager;

    public ShowTableCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        this.databaseManager.printTables();
    }
}
