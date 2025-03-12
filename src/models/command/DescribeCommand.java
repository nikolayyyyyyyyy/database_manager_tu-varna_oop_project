package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class DescribeCommand implements Command {
    private final String tableName;
    private final DatabaseManager databaseManager;

    public DescribeCommand(String tableName, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        Table table = this.databaseManager.getTable(tableName);

        System.out.println(table.printColumnTypes());
    }
}
