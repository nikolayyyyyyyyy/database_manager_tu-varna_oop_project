package models.common;
import interfaces.Command;
import interfaces.DatabaseManager;
import interfaces.Engine;
import models.command.*;
import models.core.Database;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class DatabaseEngine implements Engine {

    private final DatabaseManager databaseManager;
    private final Scanner scanner;
    private final Map<String, Command> commands;

    public DatabaseEngine() {

        databaseManager = new Database();
        scanner = new Scanner(System.in);
        this.commands = new LinkedHashMap<>();
        initCommands(this.commands);
    }

    @Override
    public void run()  {
        Command command;

        while(true){
            System.out.print("> ");

            String input = this.scanner.nextLine();
            String[] arguments = input.split(" ");

            command = commands.get(arguments[0]);
            command.execute(Arrays
                    .stream(arguments)
                    .skip(1)
                    .toArray(String[]::new));
        }
    }

    @Override
    public void initCommands(Map<String, Command> commands) {
        commands.put("addcolumn", new AddColumnCommand(this.databaseManager));
        commands.put("close", new CloseCommand(this.databaseManager));
        commands.put("count", new CountCommand(this.databaseManager));
        commands.put("delete", new DeleteCommand(this.databaseManager));
        commands.put("describe", new DescribeCommand(this.databaseManager));
        commands.put("help", new HelpCommand(this.databaseManager));
        commands.put("insert", new InsertCommand(this.databaseManager));
        commands.put("open", new OpenCommand(this.databaseManager));
        commands.put("print", new PrintCommand(this.databaseManager));
        commands.put("rename", new RenameCommand(this.databaseManager));
        commands.put("saveas", new SaveAsCommand(this.databaseManager));
        commands.put("select", new SelectCommand(this.databaseManager));
        commands.put("showtables", new ShowTableCommand(this.databaseManager));
        commands.put("update", new UpdateCommand(this.databaseManager));
        commands.put("save", new SaveCommand(this.databaseManager));
    }
}