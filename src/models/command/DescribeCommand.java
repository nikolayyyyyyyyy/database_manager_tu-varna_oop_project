package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

public class DescribeCommand implements Command {
    private final Database database;

    public DescribeCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 1){
            throw new DomainException("For description command are required 1 arg.");
        }

        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        Table tableImpl = this.database.getLoadedTables().get(tableName);
        MessageLogger.log(tableImpl.printColumnTypes());
    }
}
