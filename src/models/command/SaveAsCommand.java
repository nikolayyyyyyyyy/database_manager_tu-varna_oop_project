package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

public class SaveAsCommand implements Command {
    private final Database database;

    public SaveAsCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 2){

            throw new DomainException("For saving as command are required 2 args.");
        }

        String tableName = command[0];
        String newTableName = command[1];

        this.database.saveTableAs(tableName,newTableName);
        MessageLogger.log(String.format("Saved table %s as %s",tableName,newTableName));
    }
}
