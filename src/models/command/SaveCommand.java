package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

import java.io.IOException;

public class SaveCommand implements Command {
    private final Database database;

    public SaveCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) throws IOException {
        if(command.length != 1){
            throw new DomainException("For save command are required 1 arg.");
        }

        String tableName = command[0];
        this.database.saveTable(tableName);
        MessageLogger.log("Saved table -> ".concat(tableName));
    }
}
