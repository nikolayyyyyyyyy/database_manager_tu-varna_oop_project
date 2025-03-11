package models.core;
import interfaces.DatabaseManager;
import interfaces.FileManage;
import models.common.BaseFileValidator;
import models.common.TextFileManager;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Database implements DatabaseManager {
    private final Map<String,Table> tables;
    private final Map<String,String> help;
    private final FileManage fileManage;

    public Database() {
        this.tables = new LinkedHashMap<>();
        this.fileManage = new TextFileManager();
        this.help = Map.of("open <file>", "opens the file",
                "close", "closes the currently opened file",
                "save", "saves the currently opened file",
                "saveas <file>", "saves the currently opened file in the file",
                "help","prints this information",
                "exit","exists the program");
    }

    @Override
    public String printHelp(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("The following command are supported:").append("\n");

        for (String command:
             this.help.keySet()) {

            stringBuilder
                    .append(command)
                    .append("      ")
                    .append(this.help.get(command))
                    .append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public Table getTable(String name) {
        return this.tables.get(name);
    }

    @Override
    public void openTable(String fileName) {
        try {
            Table table = fileManage.readFile(BaseFileValidator.getBase(), fileName);

            if(this.tables.containsKey(table.getName())){

                //TODO
                return;
            }
            this.tables.put(table.getName(),table);
        }catch (IOException e){

            System.out.println("Check ErrorLogger");
            System.exit(0);
        }
    }

    @Override
    public String printTables() {
        StringBuilder sb = new StringBuilder();

        for (String table :
                this.tables.keySet()) {

            sb.append(table).append("\n");
        }

        return sb.toString();
    }

    @Override
    public void closeTable(String fileName) {
        if(!this.tables.containsKey(fileName)){

            //TODO
        } else {

            this.tables.remove(fileName);
        }
    }

    @Override
    public void saveTable(String tableName) {

        try {
            this.fileManage.writeFile(BaseFileValidator.getBase(), this.tables.get(tableName));
        } catch (IOException e){

            System.out.println("Check ErrorLogger.");
            System.exit(0);
        }
        this.closeTable(tableName);
    }

    @Override
    public void saveTableAs(String oldFileName, String newFileName) {
        try {
            Files.delete(BaseFileValidator.getBase().resolve(oldFileName));
            Table table = this.tables.get(oldFileName);

            table.rename(newFileName);
            this.fileManage.writeFile(BaseFileValidator.getBase(), table);
            this.closeTable(oldFileName);
        }catch (IOException e){

            System.out.println("Check ErrorLogger");
            System.exit(0);
        }
    }

    @Override
    public String innerJoinTables(String firstTable, String firstValue,
                                  String secondTable, String secondValue) {

        return "";
    }
}
