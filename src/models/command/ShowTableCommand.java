package models.command;

import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

public class ShowTableCommand implements Command {
    private final Database database;

    public ShowTableCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        try {

            MessageLogger.log(this.database.printTables());
        }catch (DomainException e){

            MessageLogger.log(e.getMessage());
        }
    }
}
