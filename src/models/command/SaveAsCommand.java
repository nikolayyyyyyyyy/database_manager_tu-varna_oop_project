package models.command;
import interfaces.Command;
import interfaces.DatabaseManager;
import java.io.IOException;

public class SaveAsCommand implements Command {
    private final String tableName;
    private final String newFileName;
    private final DatabaseManager databaseManager;

    public SaveAsCommand(String tableName, String newFileName, DatabaseManager databaseManager) {
        this.tableName = tableName;
        this.newFileName = newFileName;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() throws IOException {

        this.databaseManager.saveTableAs(this.tableName,this.newFileName);
    }
}
