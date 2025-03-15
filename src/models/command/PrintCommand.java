package models.command;

import interfaces.Command;
import interfaces.Database;

public class PrintCommand implements Command {
    private final Database database;

    public PrintCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];

        System.out.println(this.database
                .getTable(tableName)
                .printRows());
    }
}
