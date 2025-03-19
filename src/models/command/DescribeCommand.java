package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

/**
 * Клас, който имплементира командата за извеждане на описание на структурата на таблицата.
 * Тази команда извежда информация за типовете на колоните в дадената таблица.
 */
public class DescribeCommand implements Command {
    private final Database database;

    public DescribeCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за извеждане на описание на структурата на таблицата.
     * Изисква един параметър:
     * <ol>
     *     <li>Името на таблицата, за която ще се извеждат типовете на колоните.</li>
     * </ol>
     * Ако параметрите не са подадени правилно, хвърля {@link DomainException}.
     *
     * @param command Параметри за изпълнение на командата: име на таблицата.
     * @throws DomainException Ако не е подаден точно един параметър.
     */
    @Override
    public void execute(String... command) {
        if(command.length != 1){
            throw new DomainException("For description command are required 1 arg.");
        }

        String tableName = command[0];

        Table tableImpl = this.database.getTable(tableName);
        MessageLogger.log(tableImpl.printColumnTypes());
    }
}
