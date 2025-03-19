package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

/**
 * Клас, който имплементира командата за изтриване на редове от таблица, съдържащи дадена стойност в определена колона.
 * Тази команда извършва изтриване на редовете, които съдържат конкретна стойност в зададена колона на таблицата.
 */
public class DeleteCommand implements Command {
    private final Database database;

    public DeleteCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за изтриване на редове, които съдържат дадена стойност в определена колона на таблицата.
     * Изисква три параметъра:
     * <ol>
     *     <li>Името на таблицата, върху която се извършва изтриването.</li>
     *     <li>Индексът на колоната, в която се търси стойността.</li>
     *     <li>Стойността, която се търси в колоната.</li>
     * </ol>
     * Ако параметрите не са подадени правилно, хвърля {@link DomainException}.
     *
     * @param command Параметри за изпълнение на командата: име на таблицата, индекс на колоната и стойност за търсене.
     * @throws DomainException Ако не са подадени правилния брой параметри (3).
     */
    @Override
    public void execute(String... command) {
        if(command.length != 3){
            throw new DomainException("For deleting command are required 3 args.");
        }

        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];

        Table tableImpl = this.database.getTable(tableName);
        MessageLogger.log(tableImpl.deleteTableWhereRowContainsAt(columnIndex,searchedValue));
    }
}
