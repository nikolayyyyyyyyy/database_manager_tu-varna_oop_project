package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class SelectCommand implements Command {
    private final int columnIndex;
    private final String searchedValue;
    private final String tableName;
    private final DatabaseManager databaseManager;

    public SelectCommand(int columnIndex, String searchedValue, String tableName, DatabaseManager databaseManager) {
        this.columnIndex = columnIndex;
        this.searchedValue = searchedValue;
        this.tableName = tableName;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        Table table = databaseManager.getTable(tableName);

        System.out.println(table.selectAllRowsContain(columnIndex,searchedValue));
    }
}
