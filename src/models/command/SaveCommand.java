package models.command;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;
import models.exception.DomainException;

import java.io.IOException;

/**
 * Клас, който имплементира командата за записване на таблица в базата данни.
 * Тази команда позволява записване на дадена таблица в базата данни.
 */
public class SaveCommand implements Command {
    private final Database database;

    public SaveCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за записване на таблица.
     * Записва дадената таблица в базата данни.
     *
     * @param command Параметрите на командата, като първият параметър е името на таблицата,
     *                която трябва да бъде записана.
     *
     * @throws DomainException Ако не е подаден точно 1 параметър за командата (името на таблицата).
     * @throws IOException Ако възникне грешка при записването на таблицата.
     */
    @Override
    public void execute(String... command) throws IOException {
        if(command.length != 1){
            throw new DomainException("For save command are required 1 arg.");
        }

        String tableName = command[0];
        this.database.saveTable(tableName);
        MessageLogger.log("Saved table -> ".concat(tableName));
    }
}
