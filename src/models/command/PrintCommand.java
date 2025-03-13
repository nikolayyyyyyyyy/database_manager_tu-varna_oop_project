package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

public class PrintCommand implements Command {
    private final DatabaseManager databaseManager;

    public PrintCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];

        this.databaseManager
                .getTable(tableName)
                .printRows();
    }
}
