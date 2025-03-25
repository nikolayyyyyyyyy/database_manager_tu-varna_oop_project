package models.common;
import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Engine;
import models.command.*;
import models.core.DatabaseImpl;
import java.io.IOException;
import java.util.*;

/**
 * Имплементация на интерфейса {@link Engine}, който предоставя основната логика за управление на базата данни чрез изпълнение на команди.
 * Този класа предоставя механизъм за стартиране на команден ред за работа с база от данни.
 */
public class DatabaseEngine implements Engine {

    private final Database database;
    private final Map<String, Command> commands;

    /**
     * Конструктор за създаване на нов {@code DatabaseEngine}.
     * Приема обект на {@code Database} за работа с базата данни и инициализира наличните команди.
     *
     * @param database Обект, който представлява базата данни, върху която ще се изпълняват командите.
     */
    public DatabaseEngine(Database database) {

        this.database = database;
        this.commands = new LinkedHashMap<>();
        initCommands(this.commands);
    }

    /**
     * Стартира командния ред и започва да обработва въведените команди от потребителя.
     * Когато потребителят въведе команда, тя се изпълнява и обработват евентуални грешки.
     */
    @Override
    public void run()  {
        Scanner scanner = new Scanner(System.in);
        Command command;

        while(true){

            System.out.print("> ");
            String input = scanner.nextLine();
            String[] arguments = input.split(" ");

            if(this.commands.containsKey(arguments[0])) {
                try {
                    command = this.commands.get(arguments[0]);

                    command.execute(Arrays
                            .stream(arguments)
                            .skip(1)
                            .toArray(String[]::new));
                }catch (DomainException exception){

                    MessageLogger.log(exception.getMessage());
                } catch ( IOException exception){

                    MessageLogger.log("IO exception.");
                    new ExitCommand().execute();
                }
            } else {

                MessageLogger.log("Invalid command!");
            }
        }
    }

    /**
     * Инициализира командите, които могат да се изпълняват в системата.
     * Събира командите в {@code commands} карта, където ключът е командата, а стойността е обектът, който я изпълнява.
     *
     * @param commands Списък с всички възможни команди.
     */
    private void initCommands(Map<String, Command> commands) {
        commands.put("addcolumn", new AddColumnCommand(this.database));
        commands.put("close", new CloseCommand(this.database));
        commands.put("count", new CountCommand(this.database));
        commands.put("delete", new DeleteCommand(this.database));
        commands.put("describe", new DescribeCommand(this.database));
        commands.put("help", new HelpCommand());
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