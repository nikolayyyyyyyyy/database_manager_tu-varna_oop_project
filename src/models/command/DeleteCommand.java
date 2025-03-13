package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class DeleteCommand implements Command {
    private final DatabaseManager databaseManager;

    public DeleteCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];

        Table table = this.databaseManager.getTable(tableName);
        System.out.println(table.deleteTableWhereRowContainsAt(columnIndex,searchedValue));
    }
}
