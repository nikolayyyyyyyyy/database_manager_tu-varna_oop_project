package models.command;

import interfaces.Command;
import interfaces.Database;
import models.core.TableImpl;

public class DescribeCommand implements Command {
    private final Database database;

    public DescribeCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        TableImpl tableImpl = this.database.getTable(tableName);

        System.out.println(tableImpl.printColumnTypes());
    }
}
