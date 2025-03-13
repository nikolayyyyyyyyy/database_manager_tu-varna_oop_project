package models.command;

import interfaces.Command;
import interfaces.Database;

public class ShowTableCommand implements Command {
    private final Database database;

    public ShowTableCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {

        System.out.println(this.database.printTables());
    }
}
