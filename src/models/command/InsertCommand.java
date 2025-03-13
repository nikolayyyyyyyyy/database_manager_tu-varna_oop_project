package models.command;
import interfaces.Command;
import interfaces.DatabaseManager;
import models.core.Table;
import java.util.Arrays;

public class InsertCommand implements Command {
    private final DatabaseManager databaseManager;

    public InsertCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String[] values = Arrays.stream(command).skip(1).toArray(String[]::new);

        Table table = this.databaseManager.getTable(tableName);

        table.addRow(values);
    }
}
