package interfaces;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface DatabaseOperation {
    void importTable(String path) throws IOException;
    String printTables();
    void openTable(String tableName) throws IOException, JAXBException;
    void closeTable(String fileName);
    void saveTable(String name);
    void saveAs(String pathName,String fileName);
    String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
}
