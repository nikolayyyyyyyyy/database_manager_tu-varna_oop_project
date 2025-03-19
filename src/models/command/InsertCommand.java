package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import java.util.Arrays;

/**
 * Клас, който имплементира командата за добавяне на ред в таблица.
 * Тази команда добавя нов ред със зададени стойности в конкретна таблица.
 */
public class InsertCommand implements Command {
    private final Database database;

    public InsertCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за добавяне на нов ред в таблица.
     * Стойностите за новия ред се подават като параметри.
     *
     * @param command Параметри за командата, където първият параметър е името на таблицата,
     *                а останалите са стойностите за новия ред.
     */
    @Override
    public void execute(String... command) {
        String tableName = command[0];
        String[] values = Arrays.stream(command).skip(1).toArray(String[]::new);

        Table tableImpl = this.database.getTable(tableName);
        tableImpl.addRow(values);
    }
}
