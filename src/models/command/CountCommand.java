package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class CountCommand implements Command {
    private final String tableName;
    private final int columnIndex;
    private final String searchedValue;
    private final DatabaseManager databaseManager;

    public CountCommand(String tableName, int columnIndex, String searchedValue, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.columnIndex = columnIndex;
        this.searchedValue = searchedValue;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        Table table = this.databaseManager.getTable(this.tableName);

        System.out.println(table.getCountRowsContainAt(this.columnIndex,this.searchedValue));
    }
}
