package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.enums.ColumnOperation;
import models.exception.DomainException;

public class AggregateCommand implements Command {
    private final Database database;

    public AggregateCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 5){
            throw new DomainException("For aggregating command are required 5 arguments.");
        }

        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException("Table %s does not exist.");
        }

        int searchedColumnIndex = Integer.parseInt(command[1]);
        String value = command[2];

        int targetColumnIndex = Integer.parseInt(command[3]);
        ColumnOperation operation = ColumnOperation.valueOf(command[4]);

        Table table = this.database.getLoadedTables().get(tableName);
        MessageLogger.log(table.aggregate(searchedColumnIndex,value,targetColumnIndex,operation));
    }
}
