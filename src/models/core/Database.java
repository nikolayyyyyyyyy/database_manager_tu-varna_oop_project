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
    public boolean openTable(String fileName) {
        try {
            Table table = fileManage
                    .readFile(BaseFileValidator.getBase(), fileName);

            if (this.tables.containsKey(table.getName())) {

                return false;
            }

            this.tables.put(table.getName(), table);
        }catch (IOException e) {

            System.out.println("Check ErrorLogger");
            System.exit(0);
        }

        return true;
    }

    @Override
    public String printTables() {
        StringBuilder sb = new StringBuilder();
        sb.append("Opened tables:").append("\n");

        for (String table :
                this.tables.keySet()) {

            sb.append(table).append("\n");
        }

        return sb.toString();
    }

    @Override
    public void closeTable(String fileName) {
        if(!this.tables.containsKey(fileName)){

            System.out.println("Table ->".concat(fileName).concat(" is not loaded"));
        } else {

            this.tables.remove(fileName);
            System.out.println("Closed table ->".concat(fileName));
        }
    }

    @Override
    public void saveTable(String tableName) {
        try {

            this.fileManage.writeFile(BaseFileValidator.getBase(), this.tables.get(tableName));

            System.out.println("Saved table ->".concat(tableName));
            this.closeTable(tableName);

        } catch (IOException e){
            System.out.println("Error with saving the table ->".concat(tableName));
        }
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
