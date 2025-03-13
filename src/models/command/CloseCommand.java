package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

public class CloseCommand implements Command {
    private final DatabaseManager databaseManager;

    public CloseCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        this.databaseManager.closeTable(tableName);

    }
}
