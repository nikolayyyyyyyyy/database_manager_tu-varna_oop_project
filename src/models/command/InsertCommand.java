package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class InsertCommand implements Command {
    private final String tableName;
    private final String[] values;
    private final DatabaseManager databaseManager;

    public InsertCommand(String tableName, String[] values, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.values = values;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        Table table = this.databaseManager.getTable(tableName);

        table.addRow(this.values);
    }
}
