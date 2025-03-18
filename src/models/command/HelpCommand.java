package models.command;

import interfaces.Command;
import interfaces.Database;
import models.common.MessageLogger;

import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String,String> help;

    public HelpCommand(){

        this.help = Map.of("open <file>", "opens the given file",
                "close <file>", "closes the given file",
                "save <file>", "saves the given file",
                "saveas <file>", "saves file in new file",
                "help","prints this information",
                "exit","exists the program");    }

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
