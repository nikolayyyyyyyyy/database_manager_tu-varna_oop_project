package models.command;
import exception.DomainException;
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
        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];
        int targetColumnIndex = Integer.parseInt(command[3]);
        String targetValue = command[4];

        try {
            Table tableImpl = this.database.getTable(tableName);

            MessageLogger.log(tableImpl.updateRowValueAtIndexWhereContainsAt(columnIndex,
                    targetColumnIndex,
                    searchedValue,
                    targetValue));

        }catch (DomainException e){

            MessageLogger.log(e.getMessage());
        }
    }
}
