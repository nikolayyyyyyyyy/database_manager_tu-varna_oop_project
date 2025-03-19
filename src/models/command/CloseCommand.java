package models.command;

import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;
import models.exception.DomainException;
/**
 * Клас, който имплементира командата за затваряне на таблица в базата данни.
 * Тази команда извършва операция по затваряне на таблица, като прекратява достъпа до нея.
 */
public class CloseCommand implements Command {
    private final Database database;

    public CloseCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за затваряне на таблица в базата данни.
     * Изисква един параметър:
     * <ol>
     *     <li>Името на таблицата, която трябва да се затвори.</li>
     * </ol>
     * Ако параметърът не е подаден правилно, хвърля {@link DomainException}.
     *
     * @param command Параметър с името на таблицата, която трябва да се затвори.
     * @throws DomainException Ако не е подаден правилен брой параметри.
     */
    @Override
    public void execute(String... command) {
        if(command.length != 1){
            throw new DomainException("For close commands are required 1 arg.");
        }

        this.database.closeTable(command[0]);
        MessageLogger.log("Closed table -> ".concat(command[0]));
    }
}
