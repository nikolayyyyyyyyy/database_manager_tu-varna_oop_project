package models;
import interfaces.DatabaseOperation;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;


public class Database implements DatabaseOperation {
    private final Map<String, Table> openTables;
    private final Path base;

    public Database(){
        this.openTables = new LinkedHashMap<>();
        this.base = Path.of("catalog");
    }

    @Override
    public void importTable(String path) throws IOException {

    }

    @Override
    public String printTables() {
        StringBuilder sb = new StringBuilder();

        for (String tableName :
                this.openTables.keySet()) {

            sb.append(tableName).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void openTable(String fileName) throws IOException, JAXBException {
        Path filePath = this.base.resolve(fileName);

        if (Files.notExists(this.base)) {

            Files.createDirectories(this.base);
        }

        if (Files.notExists(filePath)) {

            Files.createFile(filePath);
            return;
        }

        JAXBContext context = JAXBContext.newInstance(Table.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Table table = (Table) unmarshaller.unmarshal(filePath.toFile());
        this.openTables.put(table.getName(),table);
    }

    @Override
    public void closeTable(String fileName) {
        this.openTables.remove(fileName);
    }

    @Override
    public void saveTable(String name) {

    }

    @Override
    public void saveAs(String pathName, String fileName) {

    }

    @Override
    public String innerJoinTables(String firstTable, String firstValue, String secondTable, String secondValue) {
        return "";
    }
}
