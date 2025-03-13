package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class RenameCommand implements Command {
    private final DatabaseManager databaseManager;

    public RenameCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String newTableName = command[1];

        Table table = this.databaseManager.getTable(tableName);
        table.rename(newTableName);
    }
}
