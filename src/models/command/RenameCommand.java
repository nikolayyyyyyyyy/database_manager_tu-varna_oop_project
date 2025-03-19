package models.command;
import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;

/**
 * Клас, който имплементира командата за преименуване на таблица в базата данни.
 * Тази команда позволява преименуването на съществуваща таблица в ново име.
 */
public class RenameCommand implements Command {
    private final Database database;

    public RenameCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за преименуване на таблица.
     * Преименува таблицата с даденото ново име.
     *
     * @param command Параметрите на командата, като първият параметър е името на таблицата,
     *                а вторият е новото име на таблицата.
     *
     * @throws DomainException Ако не са подадени точно 2 параметъра за командата (старото име и новото име).
     */
    @Override
    public void execute(String... command) {
        if(command.length != 2){
            throw new DomainException("For rename command are required 2 args.");
        }

        String tableName = command[0];
        String newTableName = command[1];

        Table tableImpl = this.database.getTable(tableName);
        tableImpl.rename(this.database.getBaseDirectory(),newTableName);
    }
}