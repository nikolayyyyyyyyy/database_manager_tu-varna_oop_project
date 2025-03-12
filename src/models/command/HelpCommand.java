package models.command;
import interfaces.Command;
import interfaces.DatabaseManager;

public class HelpCommand implements Command {
    private final DatabaseManager databaseManager;

    public HelpCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute() {
        System.out.println(this.databaseManager.printHelp());
    }
}
