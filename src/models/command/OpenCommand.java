package models.command;

import interfaces.Command;
import interfaces.DatabaseManager;

import java.io.IOException;

public class OpenCommand implements Command {
    private final DatabaseManager databaseManager;

    public OpenCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        try {
            this.databaseManager.openTable(tableName);

            System.out.println("Opened table -> ".concat(tableName));
        } catch (IOException exception){

            System.out.printf(String.format("Error reading file %s" ,tableName));
        }
    }
}
