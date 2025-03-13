package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

import java.io.IOException;

public class SaveAsCommand implements Command {
    private final DatabaseManager databaseManager;

    public SaveAsCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String newTableName = command[1];

        try {
            this.databaseManager.saveTableAs(tableName, newTableName);
        }catch (IOException e){

            System.out.printf("Error saving file %s",tableName);
        }
    }
}
