package models.command;
import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;


/**
 * Клас, който имплементира командата за актуализиране на стойности в редове на таблица.
 * Тази команда актуализира стойността на дадена колона в таблица, ако се намери съвпадение
 * за определена стойност в друга колона.
 */
public class UpdateCommand implements Command {
    private final Database database;

    public UpdateCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за актуализиране на стойността в дадена колона на таблицата.
     * Тази команда намира редовете, които съдържат определена стойност в една колона и
     * актуализира стойността в друга колона.
     *
     * @param command Параметрите на командата:
     *                - Име на таблицата (command[0])
     *                - Индекс на колоната за търсене на стойност (command[1])
     *                - Стойност за търсене в колоната (command[2])
     *                - Индекс на колоната, в която ще се актуализира стойността (command[3])
     *                - Новата стойност за актуализиране (command[4])
     */
    @Override
    public void execute(String... command) {
        if(command.length != 5){
            throw new DomainException("For update command are required 5 args.");
        }

        String tableName = command[0];
        int columnIndex = Integer.parseInt(command[1]);
        String searchedValue = command[2];
        int targetColumnIndex = Integer.parseInt(command[3]);
        String targetValue = command[4];

        Table tableImpl = this.database.getTable(tableName);

        MessageLogger.log(tableImpl.updateRowValueAtIndexWhereContainsAt(columnIndex,
                targetColumnIndex,
                searchedValue,
                targetValue));
    }
}
