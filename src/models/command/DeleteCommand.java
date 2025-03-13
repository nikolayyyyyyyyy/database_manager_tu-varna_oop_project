package models.command;

import interfaces.Command;
import interfaces.Database;
import models.core.TableImpl;

public class DeleteCommand implements Command {
    private final Database database;

    public DeleteCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];

        TableImpl tableImpl = this.database.getTable(tableName);
        System.out.println(tableImpl.deleteTableWhereRowContainsAt(columnIndex,searchedValue));
    }
}
