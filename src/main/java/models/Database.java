package models;
import interfaces.DatabaseOperation;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Database implements DatabaseOperation {
    private final Map<String,Table> tables;
    private final Path base;

    public Database() {
        this.tables = new LinkedHashMap<>();
        this.base = Path.of("catalog");
    }

    @Override
    public void importTable(String path) throws IOException, JAXBException {
        if(Files.notExists(base.resolve(path))){

            Files.createFile(base.resolve(path));
        }
        File file = new File(base.resolve(path).toString());

        String tableName = Arrays.stream(path.split(" "))
                .findFirst()
                .orElse(null);

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
    public void exportTable(String name) {

    }

    @Override
    public void exportTableAs(String pathName, String fileName) {

    }

    @Override
    public String innerJoinTables(String firstTable, String firstValue, String secondTable, String secondValue) {
        return "";
    }
}
