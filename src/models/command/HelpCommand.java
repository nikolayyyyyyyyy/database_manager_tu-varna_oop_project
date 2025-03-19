package models.command;

import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

import java.util.Map;


/**
 * Клас, който имплементира командата за извеждане на помощна информация за наличните команди.
 * Тази команда показва списък с всички поддържани команди и тяхното описание.
 */
public class HelpCommand implements Command {
    private final Map<String,String> help;

    public HelpCommand(){

        this.help = Map.of("open <file>", "opens the given file",
                "close <file>", "closes the given file",
                "save <file>", "saves the given file",
                "saveas <file>", "saves file in new file",
                "help","prints this information",
                "exit","exists the program");    }

    /**
     * Изпълнява командата за извеждане на помощна информация за наличните команди.
     * Тази команда генерира списък с всички налични команди и тяхното описание,
     * след което ги извежда в конзолата.
     *
     * @param command Параметри за изпълнение на командата (не се използват в този случай).
     */
    @Override
    public void execute(String... command) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The following command are supported:").append("\n");

        for (String c :
                this.help.keySet()) {
            stringBuilder.append(c).append("    ").append(this.help.get(c)).append("\n");
        }

        MessageLogger.log(stringBuilder.toString().trim());
    }
}
