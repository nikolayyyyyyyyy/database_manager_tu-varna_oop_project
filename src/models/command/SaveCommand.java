package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

public class SaveCommand implements Command {
    private final String tableName;
    private final DatabaseManager databaseManager;

    public SaveCommand(String tableName, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        this.databaseManager.saveTable(tableName);
    }
}
