package models.command;
import models.exception.DomainException;
import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

/**
 * Клас, който имплементира командата за съединяване (join) на две таблици.
 * Тази команда изпълнява вътрешно съединяване (INNER JOIN) на две таблици по зададени колони.
 */
public class JoinCommand implements Command {
    private final Database database;

    public JoinCommand(Database database){

        this.database = database;
    }

    /**
     * Изпълнява командата за съединяване на две таблици.
     * Съединяването се извършва по зададени индекси на колони от двете таблици.
     *
     * @param command Параметри за командата, като първият параметър е името на първата таблица,
     *                вторият параметър е индексът на колоната от първата таблица,
     *                третият параметър е името на втората таблица,
     *                а четвъртият параметър е индексът на колоната от втората таблица.
     *
     * @throws DomainException Ако не са подадени точно 4 параметъра.
     */
    @Override
    public void execute(String... command) {
        if(command.length != 4){
            throw new DomainException("For joining command are required 4 args.");
        }

        String firstTable = command[0];
        int firstColumnIndex = Integer.parseInt(command[1]);
        String secondTable = command[2];
        int secondColumnIndex = Integer.parseInt(command[3]);

        MessageLogger.log(this.database.innerJoinTables(firstTable,firstColumnIndex,secondTable,secondColumnIndex));
    }
}
