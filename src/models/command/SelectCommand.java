package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

public class SelectCommand implements Command {
    private final Database database;

    public SelectCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 3){

            throw new DomainException("For select command are required 3 args");
        }

        String tableName = command[2];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        int columnIndex = Integer.parseInt(command[0]);
        String searchedValue = command[1];

        Table tableImpl = database.getLoadedTables().get(tableName);
        tableImpl.selectAllRowsContain(columnIndex,searchedValue);
    }
}
