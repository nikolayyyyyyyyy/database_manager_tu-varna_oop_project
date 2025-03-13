package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;

public class SelectCommand implements Command {
    private final Database database;

    public SelectCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];
        Table tableImpl = database.getTable(tableName);

        System.out.println(tableImpl.selectAllRowsContain(columnIndex,searchedValue));
    }
}
