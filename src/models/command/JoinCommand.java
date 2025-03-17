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
        if(command.length != 4){
            throw new DomainException("For joining command are required 4 args.");
        }

        String firstTable = command[0];
        int firstColumnIndex = Integer.parseInt(command[1]);
        String secondTable = command[2];
        int secondColumnIndex = Integer.parseInt(command[3]);

        MessageLogger.log(this.database.innerJoinTables(firstTable,firstColumnIndex,secondTable,secondColumnIndex));
    }
}
