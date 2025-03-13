package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class SelectCommand implements Command {
    private final DatabaseManager databaseManager;

    public SelectCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];
        Table table = databaseManager.getTable(tableName);

        System.out.println(table.selectAllRowsContain(columnIndex,searchedValue));
    }
}
