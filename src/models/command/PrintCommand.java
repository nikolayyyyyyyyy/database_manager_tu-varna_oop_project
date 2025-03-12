package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

public class PrintCommand implements Command {
    private final String tableName;
    private final DatabaseManager databaseManager;

    public PrintCommand(String tableName, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        System.out.println(this.databaseManager
                .getTable(tableName)
                .printRows());
    }
}
