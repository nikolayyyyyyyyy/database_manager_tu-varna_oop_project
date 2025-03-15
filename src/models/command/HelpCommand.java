package models.command;

import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

public class HelpCommand implements Command {
    private final Database database;

    public HelpCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        MessageLogger.log(this.database.printHelp());
    }
}
