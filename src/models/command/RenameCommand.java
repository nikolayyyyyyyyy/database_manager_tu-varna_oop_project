package models.command;

import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;

public class RenameCommand implements Command {
    private final Database database;

    public RenameCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 2){
            throw new DomainException("For rename command are required 2 args.");
        }

        String tableName = command[0];
        String newTableName = command[1];


        Table tableImpl = this.database.getTable(tableName);
        tableImpl.rename(this.database.getBase(),newTableName);
    }
}
