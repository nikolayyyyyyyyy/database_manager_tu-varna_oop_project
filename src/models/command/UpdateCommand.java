package models.command;
import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;

public class UpdateCommand implements Command {
    private final Database database;

    public UpdateCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 5){
            throw new DomainException("For update command are required 5 args.");
        }

        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];
        int targetColumnIndex = Integer.parseInt(command[3]);
        String targetValue = command[4];

        Table tableImpl = this.database.getLoadedTables().get(tableName);

        MessageLogger.log(tableImpl.updateRowValueAtIndexWhereContainsAt(columnIndex,
                targetColumnIndex,
                searchedValue,
                targetValue));
    }
}
