package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class UpdateCommand implements Command {
    private final DatabaseManager databaseManager;

    public UpdateCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];
        int targetColumnIndex = Integer.parseInt(command[3]);
        String targetValue = command[4];

        Table table = this.databaseManager.getTable(tableName);

        System.out.println(table.updateRowValueAtIndexWhereContainsAt(columnIndex,
                targetColumnIndex,
                searchedValue,
                targetValue));
    }
}
