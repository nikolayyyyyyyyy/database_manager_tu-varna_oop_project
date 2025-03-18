package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.FileManage;
import interfaces.Table;
import models.common.MessageLogger;
import models.common.TextFileManager;
import models.exception.DomainException;

import java.io.IOException;

public class OpenCommand implements Command {
    private final Database database;
    private final FileManage fileManage;

    public OpenCommand(Database database) {
        this.database = database;
        this.fileManage = new TextFileManager();
    }

    @Override
    public void execute(String... command) throws IOException {
        if(command.length != 1){

            throw new DomainException("For open command are required 1 arg.");
        }

        String tableName = command[0];
        Table table = this.fileManage.openTable(tableName);
        if(this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s already loaded in database.",tableName));
        }

        this.database.getLoadedTables().put(tableName,table);
        MessageLogger.log("Opened table -> ".concat(tableName));
    }
}
