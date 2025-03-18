package models.command;

import interfaces.FileManage;
import models.common.TextFileManager;
import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;

public class RenameCommand implements Command {
    private final Database database;
    private final FileManage fileManage;

    public RenameCommand(Database database) {
        this.fileManage = new TextFileManager();
        this.database = database;
    }

    @Override
    public void execute(String... command) {
        if(command.length != 2){
            throw new DomainException("For rename command are required 2 args.");
        }

        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        String newTableName = command[1];
        if(this.fileManage.isFileExitsInBase(newTableName)){

            throw new DomainException(String.format("Table with %s name already exist.",newTableName));
        }

        Table tableImpl = this.database.getLoadedTables().get(tableName);
        tableImpl.rename(newTableName);
    }
}