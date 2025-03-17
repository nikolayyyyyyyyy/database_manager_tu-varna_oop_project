package models.command;

import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;
import models.exception.DomainException;

public class CloseCommand implements Command {
    private final Database database;

    public CloseCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 1){
            throw new DomainException("For close commands are required 1 arg.");
        }

        String tableName = command[0];
        this.database.closeTable(tableName);

        MessageLogger.log("Closed table -> ".concat(tableName));
    }
}
