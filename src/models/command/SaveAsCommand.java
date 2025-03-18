package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.FileManage;
import interfaces.Table;
import models.common.MessageLogger;
import models.common.TextFileManager;
import models.exception.DomainException;

public class SaveAsCommand implements Command {
    private final Database database;
    private final FileManage fileManage;

    public SaveAsCommand(Database database) {
        this.database = database;
        this.fileManage = new TextFileManager();
    }

    @Override
    public void execute(String... command) {
        if(command.length != 2){

            throw new DomainException("For saving as command are required 2 args.");
        }

        String tableName = command[0];
        if(!this.database.getLoadedTables().containsKey(tableName)){

            throw new DomainException("Table %s is not loaded.");
        }

        Table table = this.database.getLoadedTables().get(tableName);

        String newTableName = command[1];
        if(this.fileManage.isFileExitsInBase(newTableName)){

            throw new DomainException(String.format("Table with %s name already exist, change it first.",newTableName));
        }
        table.rename(newTableName);
        this.database.getLoadedTables().remove(tableName);

        MessageLogger.log(String.format("Saved table %s as %s",tableName,newTableName));
    }
}
