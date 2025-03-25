package models.command;
import interfaces.Command;
import models.common.MessageLogger;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Клас, който имплементира командата за извеждане на помощна информация за наличните команди.
 * Тази команда показва списък с всички поддържани команди и тяхното описание.
 */
public class HelpCommand implements Command {
    private final Map<String,String> help;

    public HelpCommand(){
        this.help = new LinkedHashMap<>();
        initSupportedCommands(this.help);
    }

    /**
     * Инициализирам командите който поддържа системата
     * @param help картата с възможните команди
     */
    private void initSupportedCommands(Map<String, String> help) {
        help.put("open <file>:","opens the given file.");
        help.put("close <file>:","closes the given file.");
        help.put("save <file>:","saves the given file.");
        help.put("saveas <file>:","saves file in new file.");
        help.put("help:","prints this information.");
        help.put("exit:","exists the program.");
        help.put("showtables:","Print name of loaded tables.");
        help.put("describe <name>:","Show column name and type.");
        help.put("print <name>:","Print in pages the rows.");
        help.put("select <column-n> <value> <table name>:","Show all row from table that match at given index the value.");
        help.put("update <table name> <search column n> <search value> <target column n> <target value>:", "Update table where in the search column contains the search value in the target index column with the target value.");
        help.put("delete <table name> <search column n> <search value>:","Delete all row in table where column contains the value.");
        help.put("insert <table name> <column n...>:","Insert new row in the table.");
        help.put("innerjoin <table 1> <column n1> <table 2> <column 2>:","Joins the tables where the columns match.");
        help.put("rename <old name> <new name>:","Rename the table if the name is not taken.");
        help.put("count <table name> <search column n> <search value>:","Print the count of rows that match in given column the value.");
        help.put("aggregate <table name> <search column n> <search value> <target column n> <operation>:","Do aggregation into the column of table with target column where in column n contains the searched value.");
    }

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
