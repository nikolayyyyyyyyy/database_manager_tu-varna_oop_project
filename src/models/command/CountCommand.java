package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

public class CountCommand implements Command {
    private final Database database;

    public CountCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 3){
            throw new DomainException("For count command are required 3 args");
        }

        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];

        Table tableImpl = this.database.getTable(tableName);

        MessageLogger.log(tableImpl.getCountRowsContainAt(columnIndex,searchedValue));
    }
}
