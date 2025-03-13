package models.command;

import interfaces.Command;
import interfaces.Database;

public class CloseCommand implements Command {
    private final Database database;

    public CloseCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        this.database.closeTable(tableName);

    }
}
