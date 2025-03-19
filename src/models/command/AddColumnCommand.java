package models.command;
import interfaces.Column;
import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.core.ColumnImpl;
import models.enums.ColumnType;
import models.exception.DomainException;

/**
 * Клас, който имплементира командата за добавяне на нова колона към таблица в базата данни.
 * Тази команда ще добави нова колона със специфични име и тип към дадена таблица.
 */
public class AddColumnCommand implements Command {
    private final Database database;

    public AddColumnCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за добавяне на нова колона в дадена таблица.
     * Изисква три параметъра:
     * <ol>
     *     <li>Името на таблицата</li>
     *     <li>Името на колоната, която ще се добави</li>
     *     <li>Типът на колоната (например, STRING, INTEGER и т.н.)</li>
     * </ol>
     * Ако параметрите не са правилни, хвърля {@link DomainException}.
     *
     * @param command Параметри за изпълнение на командата: име на таблицата, име на колоната, тип на колоната.
     * @throws DomainException Ако не са подадени правилен брой параметри или има грешка при добавянето на колоната.
     */
    @Override
    public void execute(String... command) {
        if(command.length != 3){
            throw new DomainException("For adding needs 2 parameters");
        }

        String tableName = command[0];
        Column column = new ColumnImpl(command[1],ColumnType.valueOf(command[2]));

        Table tableImpl = this.database.getTable(tableName);
        tableImpl.addColumn(column);

        MessageLogger.log("Added column -> ".concat(column.getName()));
    }
}
