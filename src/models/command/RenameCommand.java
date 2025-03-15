package models.command;

import exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.core.TableImpl;

public class RenameCommand implements Command {
    private final Database database;

    public RenameCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String newTableName = command[1];

        try {

            Table tableImpl = this.database.getTable(tableName);
            tableImpl.rename(newTableName);
        }catch (DomainException exception){

            MessageLogger.log(exception.getMessage());
        }
    }
}
