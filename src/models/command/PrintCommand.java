package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.exception.DomainException;

public class PrintCommand implements Command {
    private final Database database;

    public PrintCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 1){
            throw new DomainException("For print command is required 1 arg.");
        }

        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s does not exist.",tableName));
        }

        Table table =  this.database
                .getLoadedTables().get(tableName);

        table.printRows();
    }
}
