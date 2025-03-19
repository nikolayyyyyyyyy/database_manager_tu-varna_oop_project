package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.exception.DomainException;

/**
 * Клас, който имплементира командата за принтиране на редовете от таблица в базата данни.
 * Тази команда извежда всички редове на дадената таблица на конзолата.
 */
public class PrintCommand implements Command {
    private final Database database;

    public PrintCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за принтиране на редовете на таблица.
     * Извежда всички редове на дадената таблица в конзолата.
     *
     * @param command Параметърът е името на таблицата, чиито редове трябва да бъдат принтирани.
     *
     * @throws DomainException Ако не е подаден точно 1 параметър за името на таблицата.
     */
    @Override
    public void execute(String... command) {
        if(command.length != 1){
            throw new DomainException("For print command is required 1 arg.");
        }

        String tableName = command[0];
        Table table =  this.database
                .getTable(tableName);

        table.printRows();
    }
}
