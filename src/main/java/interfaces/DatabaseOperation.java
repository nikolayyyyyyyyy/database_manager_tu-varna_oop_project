package interfaces;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface DatabaseOperation {
    void openTable(String fileName) throws IOException, JAXBException;
    String printTables();
    void closeTable(String fileName);
    void saveTable(String tableName) throws JAXBException;
    void saveTableAs(String oldFileName,String newFileName) throws JAXBException;
    String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
    String printHelp();
}
