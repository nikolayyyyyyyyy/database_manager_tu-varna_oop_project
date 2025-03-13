package models.command;
import interfaces.Command;
import interfaces.Database;
import models.core.ColumnType;
import models.core.TableImpl;

public class AddColumnCommand implements Command {
    private final Database database;

    public AddColumnCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String columnName = command[1];
        ColumnType columnType = ColumnType.valueOf(command[2]);

        TableImpl tableImpl = this.database.getTable(tableName);

        tableImpl.addColumn(columnType,columnName);
        System.out.println("Added column -> ".concat(columnName));
    }
}
