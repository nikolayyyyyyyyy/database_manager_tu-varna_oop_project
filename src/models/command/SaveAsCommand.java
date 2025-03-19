package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

/**
 * Клас, който имплементира командата за записване на таблица под ново име в базата данни.
 * Тази команда позволява запазване на съществуваща таблица с ново име.
 */
public class SaveAsCommand implements Command {
    private final Database database;

    public SaveAsCommand(Database database) {
        this.database = database;
    }


    /**
     * Изпълнява командата за записване на таблица под ново име.
     * Записва дадената таблица с ново име в базата данни.
     *
     * @param command Параметрите на командата, като първият параметър е името на таблицата,
     *                а вторият параметър е новото име, под което да бъде запазена таблицата.
     *
     * @throws DomainException Ако не са подадени точно 2 параметъра за командата (старото име и новото име).
     */
    @Override
    public void execute(String... command) {
        if(command.length != 2){

            throw new DomainException("For saving as command are required 2 args.");
        }

        String tableName = command[0];
        String newTableName = command[1];

        this.database.saveTableAs(tableName,newTableName);
        MessageLogger.log(String.format("Saved table %s as %s",tableName,newTableName));
    }
}
