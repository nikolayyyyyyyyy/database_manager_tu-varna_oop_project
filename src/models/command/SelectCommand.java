package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;

public class SelectCommand implements Command {
    private final Database database;

    public SelectCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[2];
        int columnIndex = Integer.parseInt(command[0]);
        String searchedValue = command[1];
        Table tableImpl = database.getTable(tableName);

        MessageLogger.log(tableImpl.selectAllRowsContain(columnIndex,searchedValue));
    }
}
