package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.core.ColumnOperation;

public class AggregateCommand implements Command {
    private final Database database;

    public AggregateCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        int searchedColumn = Integer.parseInt(command[1]);
        String value = command[2];
        int targetColumnIndex = Integer.parseInt(command[3]);
        ColumnOperation operation = ColumnOperation.valueOf(command[4]);

        Table table = this.database.getTable(tableName);
        System.out.println(table.aggregate(searchedColumn,value,targetColumnIndex,operation));
    }
}
