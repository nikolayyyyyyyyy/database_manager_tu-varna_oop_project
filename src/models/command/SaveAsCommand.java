package models.command;

import interfaces.Command;
import interfaces.Database;

import java.io.IOException;

public class SaveAsCommand implements Command {
    private final Database database;

    public SaveAsCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String newTableName = command[1];

        try {
            this.database.saveTableAs(tableName, newTableName);
        }catch (IOException e){

            System.out.printf("Error saving file %s",tableName);
        }
    }
}
