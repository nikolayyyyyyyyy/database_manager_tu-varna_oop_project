package models.command;
import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.ColumnType;
import models.core.Table;

public class AddColumnCommand implements Command {
    private final String tableName;
    private final String columnName;
    private final ColumnType columnType;
    private final DatabaseManager databaseManager;

    public AddColumnCommand(String tableName, String columnName, ColumnType columnType, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        Table table = this.databaseManager.getTable(this.tableName);

        table.addColumn(this.columnName,this.columnType);
        System.out.println("Added column ->".concat(this.columnName));
    }
}
