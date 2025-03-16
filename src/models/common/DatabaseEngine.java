package models.common;
import exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Engine;
import models.command.*;
import models.core.DatabaseImpl;
import java.util.*;

public class DatabaseEngine implements Engine {

    private final Database database;
    private final Scanner scanner;
    private final Map<String, Command> commands;

    public DatabaseEngine() {

        database = new DatabaseImpl();
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

            if(this.commands.containsKey(arguments[0])) {
                try {
                    command = this.commands.get(arguments[0]);

                    command.execute(Arrays
                            .stream(arguments)
                            .skip(1)
                            .toArray(String[]::new));
                }catch (DomainException exception){

                    System.out.println(exception.getMessage());
                }
            } else {

                System.out.println("Invalid command!");
                break;
            }
        }
    }

    private void initCommands(Map<String, Command> commands) {
        commands.put("addcolumn", new AddColumnCommand(this.database));
        commands.put("close", new CloseCommand(this.database));
        commands.put("count", new CountCommand(this.database));
        commands.put("delete", new DeleteCommand(this.database));
        commands.put("describe", new DescribeCommand(this.database));
        commands.put("help", new HelpCommand(this.database));
        commands.put("insert", new InsertCommand(this.database));
        commands.put("open", new OpenCommand(this.database));
        commands.put("print", new PrintCommand(this.database));
        commands.put("rename", new RenameCommand(this.database));
        commands.put("saveas", new SaveAsCommand(this.database));
        commands.put("select", new SelectCommand(this.database));
        commands.put("showtables", new ShowTableCommand(this.database));
        commands.put("update", new UpdateCommand(this.database));
        commands.put("save", new SaveCommand(this.database));
        commands.put("exit", new ExitCommand());
        commands.put("aggregate",new AggregateCommand(this.database));
        commands.put("innerjoin",new JoinCommand(this.database));
    }
}