package models.command;
import interfaces.Column;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.core.ColumnImpl;
import models.enums.ColumnType;
import models.exception.DomainException;

public class AddColumnCommand implements Command {
    private final Database database;

    public AddColumnCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 3){
            throw new DomainException("For adding needs 2 parameters");
        }

        String tableName = command[0];
        Column column = new ColumnImpl(command[1],ColumnType.valueOf(command[2]));

        Table tableImpl = this.database.getTable(tableName);
        tableImpl.addColumn(column);

        MessageLogger.log("Added column -> ".concat(column.getName()));
    }
}
