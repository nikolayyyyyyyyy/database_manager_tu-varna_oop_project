package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class UpdateCommand implements Command {
    private final String tableName;
    private final int columnIndex;
    private final String searchedValue;
    private final int targetIndex;
    private final String targetValue;
    private final DatabaseManager databaseManager;

    public UpdateCommand(String tableName, int columnIndex, String searchedValue, int targetIndex, String targetValue, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.columnIndex = columnIndex;
        this.searchedValue = searchedValue;
        this.targetIndex = targetIndex;
        this.targetValue = targetValue;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        Table table = this.databaseManager.getTable(tableName);

        table.updateRowValueAtIndexWhereContainsAt(this.columnIndex,
                this.targetIndex,
                this.searchedValue,
                this.targetValue);

    }
}
