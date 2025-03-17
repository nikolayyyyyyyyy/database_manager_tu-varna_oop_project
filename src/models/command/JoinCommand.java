package models.command;
import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

public class JoinCommand implements Command {
    private final Database database;

    public JoinCommand(Database database){

        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String firstTable = command[0];
        int firstColumnIndex = Integer.parseInt(command[1]);
        String secondTable = command[2];
        int secondColumnIndex = Integer.parseInt(command[3]);

        try {

            MessageLogger.log(this.database.innerJoinTables(firstTable,firstColumnIndex,secondTable,secondColumnIndex));
        }catch (DomainException exception){

            MessageLogger.log(exception.getMessage());
        }
    }
}
