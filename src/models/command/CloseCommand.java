package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

public class CloseCommand implements Command {
    private final DatabaseManager databaseManager;
    private final String fileName;

    public CloseCommand(DatabaseManager databaseManager, String fileName) {
        this.databaseManager = databaseManager;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        this.databaseManager.closeTable(fileName);
    }
}
