package models.command;
import interfaces.Command;
import interfaces.DatabaseManager;
import java.io.IOException;

public class OpenCommand implements Command {
    private final String fileName;
    private final DatabaseManager databaseManager;

    public OpenCommand(String fileName,
                       DatabaseManager databaseManager) {
        this.fileName = fileName;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() throws IOException {

        boolean isTableLoaded = this.databaseManager.openTable(fileName);

        if(isTableLoaded){
            System.out.println("Opened table ->".concat(fileName));
        } else {
            System.out.println("Table is already loaded.");
        }
    }
}
