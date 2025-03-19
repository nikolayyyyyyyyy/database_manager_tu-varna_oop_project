package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import java.util.Arrays;

public class InsertCommand implements Command {
    private final Database database;

    public InsertCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String[] values = Arrays.stream(command).skip(1).toArray(String[]::new);

        Table tableImpl = this.database.getTable(tableName);
        tableImpl.addRow(values);
    }
}
