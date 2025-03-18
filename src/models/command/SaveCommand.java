package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.FileManage;
import interfaces.Table;
import models.common.MessageLogger;
import models.common.TextFileManager;
import models.exception.DomainException;

import java.io.IOException;

public class SaveCommand implements Command {
    private final Database database;
    private final FileManage fileManage;

    public SaveCommand(Database database) {
        this.database = database;
        this.fileManage = new TextFileManager();
    }

    @Override
    public void execute(String... command) throws IOException {
        if(command.length != 1){
            throw new DomainException("For save command are required 1 arg.");
        }

        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        Table table = this.database.getLoadedTables().get(tableName);
        this.fileManage.saveTable(table);
        this.database.getLoadedTables().remove(tableName);
        MessageLogger.log("Saved table -> ".concat(tableName));
    }
}
