package models.command;

import interfaces.Command;
import interfaces.Database;
import models.core.TableImpl;

public class UpdateCommand implements Command {
    private final Database database;

    public UpdateCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];
        int targetColumnIndex = Integer.parseInt(command[3]);
        String targetValue = command[4];

        TableImpl tableImpl = this.database.getTable(tableName);

        System.out.println(tableImpl.updateRowValueAtIndexWhereContainsAt(columnIndex,
                targetColumnIndex,
                searchedValue,
                targetValue));
    }
}
