package models.command;

import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;
import models.exception.DomainException;

import java.io.IOException;

public class SaveAsCommand implements Command {
    private final Database database;

    public SaveAsCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 2){

            throw new DomainException("For saving as command are required 2 args.");
        }

        String tableName = command[0];
        String newTableName = command[1];

        try {
            this.database.saveTableAs(tableName, newTableName);
        }catch (IOException e){

            MessageLogger.log( String.format("Cannot save as %s something went wrong",tableName));
            System.exit(0);
        }
    }
}
