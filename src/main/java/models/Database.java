package models;
import interfaces.DatabaseOperation;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Database implements DatabaseOperation {
    private final Map<String,Table> tables;
    private final Path base;
    private final Map<String,String> help;

    public Database() {
        this.tables = new LinkedHashMap<>();
        this.base = Path.of("catalog");

        this.help = Map.of("open <file>", "opens the file",
                "close", "closes the currently opened file",
                "save", "saves the currently opened file",
                "saveas <file>", "saves the currently opened file in the file",
                "help","prints this information",
                "exit","exists the program");
    }

    public Path getBase() {
        return base;
    }

    public Map<String, Table> getTables() {
        return tables;
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
    public void openTable(String fileName) throws IOException, JAXBException {
        File file = new File(base.resolve(fileName).toString());

        String tableName = fileName.replace(".xml","");

        Table table;

        if(file.length() == 0){

            table = new Table(tableName);
        } else {

            table = XmlFileManager.readFile(file);
        }

        if(this.tables.containsKey(tableName)){

            ErrorLogger.log("Table already loaded.");
            return;
        }
        this.tables.put(tableName,table);
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

            ErrorLogger.log("Table does not exist.");
        } else {

            this.tables.remove(fileName);
        }
    }

    @Override
    public void saveTable(String tableName) throws JAXBException {
        File file = new File(base.resolve(tableName + ".xml")
                .toString());

        XmlFileManager.writeFile(this.tables.get(tableName),file);
        this.closeTable(tableName);
    }

    @Override
    public void saveTableAs(String oldFileName, String newFileName) throws JAXBException {
        File file = new File(base.resolve(newFileName + ".xml")
                .toString());

        XmlFileManager.writeFile(this.tables.get(oldFileName),file);
        this.closeTable(oldFileName);
    }

    @Override
    public String innerJoinTables(String firstTable, String firstValue,
                                  String secondTable, String secondValue) {

        return "";
    }
}
