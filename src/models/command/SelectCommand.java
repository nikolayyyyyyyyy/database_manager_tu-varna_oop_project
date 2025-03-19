package models.command;

import interfaces.Command;
import interfaces.Database;
import interfaces.Table;
import models.common.MessageLogger;
import models.exception.DomainException;

/**
 * Клас, който имплементира командата за извличане на редове от таблица в базата данни.
 * Тази команда позволява да се избират редове от таблица, които съдържат определена стойност в дадена колона.
 */
public class SelectCommand implements Command {
    private final Database database;

    public SelectCommand(Database database) {
        this.database = database;
    }

    /**
     * Изпълнява командата за извличане на редове от таблица.
     * Тази команда избира всички редове, които съдържат определена стойност в дадена колона.
     *
     * @param command Параметрите на командата:
     *                - Първият параметър е индексът на колоната, в която ще се търси стойността.
     *                - Вторият параметър е стойността, която ще се търси в колоната.
     *                - Третият параметър е името на таблицата, от която ще се извлекат редовете.
     *
     * @throws DomainException Ако не са подадени точно 3 параметъра за командата.
     */
    @Override
    public void execute(String... command) {
        if(command.length != 3){

            throw new DomainException("For select command are required 3 args");
        }

        String tableName = command[2];
        int columnIndex = Integer.parseInt(command[0]);
        String searchedValue = command[1];

        Table tableImpl = database.getTable(tableName);
        tableImpl.selectAllRowsContain(columnIndex,searchedValue);
    }
}
