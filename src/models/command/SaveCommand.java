package models.command;

import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

import java.io.IOException;

public class SaveCommand implements Command {
    private final Database database;

    public SaveCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        try {
            this.database.saveTable(tableName);
            MessageLogger.log("Saved table -> ".concat(tableName));

        } catch (IOException exception){

            MessageLogger.log(String.format("Error saving file %s",tableName));
            System.exit(0);
        }
    }
}
