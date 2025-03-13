package models.command;

import interfaces.Command;
import interfaces.Database;

public class HelpCommand implements Command {
    private final Database database;

    public HelpCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {

        System.out.println(this.database.printHelp());
    }
}
