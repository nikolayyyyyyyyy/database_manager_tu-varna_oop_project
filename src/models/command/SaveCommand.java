package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

import java.io.IOException;

public class SaveCommand implements Command {
    private final DatabaseManager databaseManager;

    public SaveCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        try {
            this.databaseManager.saveTable(tableName);
        } catch (IOException exception){

            System.out.printf("Error saving file %s%n",tableName);
        }
    }
}
