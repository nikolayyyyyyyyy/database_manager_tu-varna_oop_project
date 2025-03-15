package models.command;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

import java.io.IOException;

public class OpenCommand implements Command {
    private final Database database;

    public OpenCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        try {

            this.database.openTable(tableName);
            MessageLogger.log("Opened table -> ".concat(tableName));
        } catch (IOException exception){

            MessageLogger.log(String.format("Error reading file %s" ,tableName));
            System.exit(0);
        }
    }
}
