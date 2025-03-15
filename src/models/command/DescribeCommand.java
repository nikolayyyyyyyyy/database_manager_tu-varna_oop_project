package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;

public class DescribeCommand implements Command {
    private final Database database;

    public DescribeCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        Table tableImpl = this.database.getTable(tableName);

        MessageLogger.log(tableImpl.printColumnTypes());
    }
}
