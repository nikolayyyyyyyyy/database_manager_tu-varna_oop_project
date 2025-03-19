package models.command;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.enums.ColumnOperation;
import models.exception.DomainException;


/**
 * Клас, който имплементира командата за агрегиране на стойности в дадена таблица.
 * Тази команда извършва агрегация върху данни в определена колона, използвайки посочена операция.
 */
public class AggregateCommand implements Command {
    private final Database database;

    public AggregateCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за агрегация на стойности в дадена таблица.
     * Изисква пет параметъра:
     * <ol>
     *     <li>Името на таблицата</li>
     *     <li>Индекс на колоната, която ще се търси</li>
     *     <li>Стойност за търсене в колоната</li>
     *     <li>Индекс на целевата колона, върху която ще се извършва агрегацията</li>
     *     <li>Операция за агрегация (например, SUM, AVG, COUNT и т.н.)</li>
     * </ol>
     * Ако параметрите не са правилни, хвърля {@link DomainException}.
     *
     * @param command Параметри за изпълнение на командата: име на таблицата, индекси на колони и операция.
     * @throws DomainException Ако не са подадени правилен брой параметри или има грешка при изпълнението на командата.
     */
    @Override
    public void execute(String... command) {
        if(command.length != 5){
            throw new DomainException("For aggregating command are required 5 arguments.");
        }

        String tableName = command[0];
        int searchedColumnIndex = Integer.parseInt(command[1]);
        String value = command[2];
        int targetColumnIndex = Integer.parseInt(command[3]);
        ColumnOperation operation = ColumnOperation.valueOf(command[4]);

        Table table = this.database.getTable(tableName);
        MessageLogger.log(table.aggregate(searchedColumnIndex,value,targetColumnIndex,operation));
    }
}
