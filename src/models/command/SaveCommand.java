package models.command;

import interfaces.Command;
import interfaces.Database;

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
        } catch (IOException exception){

            System.out.printf("Error saving file %s%n",tableName);
        }
    }
}
