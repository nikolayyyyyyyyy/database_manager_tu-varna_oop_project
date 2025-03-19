package models.command;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;
import models.exception.DomainException;
import java.io.IOException;

/**
 * Клас, който имплементира командата за отваряне на таблица в базата данни.
 * Тази команда отваря дадената таблица и я зарежда в паметта за последваща работа с нея.
 */
public class OpenCommand implements Command {
    private final Database database;

    public OpenCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за отваряне на таблица в базата данни.
     * Зарежда таблицата в паметта за последваща работа.
     *
     * @param command Параметърът е името на таблицата, която трябва да бъде отворена.
     *
     * @throws DomainException Ако не е подаден точно 1 параметър за името на таблицата.
     * @throws IOException Ако възникне грешка при отварянето на таблицата.
     */
    @Override
    public void execute(String... command) throws IOException {
        if(command.length != 1){

            throw new DomainException("For open command are required 1 arg.");
        }

        String tableName = command[0];
        this.database.openTable(tableName);
        MessageLogger.log("Opened table -> ".concat(tableName));
    }
}
