package models.command;
import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.ColumnType;
import models.core.Table;

public class AddColumnCommand implements Command {
    private final DatabaseManager databaseManager;

    public AddColumnCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String columnName = command[1];
        ColumnType columnType = ColumnType.valueOf(command[2]);

        Table table = this.databaseManager.getTable(tableName);

        table.addColumn(columnType,columnName);
        System.out.println("Added column -> ".concat(columnName));
    }
}
