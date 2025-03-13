package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class DescribeCommand implements Command {
    private final DatabaseManager databaseManager;

    public DescribeCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        Table table = this.databaseManager.getTable(tableName);

        System.out.println(table.printColumnTypes());
    }
}
