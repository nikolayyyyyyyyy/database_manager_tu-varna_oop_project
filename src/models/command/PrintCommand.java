package models.command;

import interfaces.Command;
import interfaces.Database;
import models.exception.DomainException;

public class PrintCommand implements Command {
    private final Database database;

    public PrintCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 1){
            throw new DomainException("For print command is required 1 arg.");
        }

        String tableName = command[0];
        this.database
                .getTable(tableName)
                .printRows();
    }
}
