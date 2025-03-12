package models.command;
import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;

public class RenameCommand implements Command {
    private final String tableName;
    private final String newName;
    private final DatabaseManager databaseManager;

    public RenameCommand(String tableName, String newName, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.newName = newName;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        Table table = this.databaseManager.getTable(this.tableName);
        table.rename(this.newName);
    }
}
