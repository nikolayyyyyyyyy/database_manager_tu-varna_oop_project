package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.exception.DomainException;

import java.util.Arrays;

public class InsertCommand implements Command {
    private final Database database;

    public InsertCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s does not exist",tableName));
        }

        String[] values = Arrays.stream(command).skip(1).toArray(String[]::new);

        Table tableImpl = this.database.getLoadedTables().get(tableName);
        tableImpl.addRow(values);
    }
}
