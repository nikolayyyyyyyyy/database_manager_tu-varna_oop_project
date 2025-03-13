package models.command;

import interfaces.Command;
import interfaces.Database;
import models.core.TableImpl;

public class RenameCommand implements Command {
    private final Database database;

    public RenameCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String newTableName = command[1];

        TableImpl tableImpl = this.database.getTable(tableName);
        tableImpl.rename(newTableName);
    }
}
