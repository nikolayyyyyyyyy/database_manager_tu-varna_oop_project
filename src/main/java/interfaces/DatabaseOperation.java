package interfaces;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface DatabaseOperation {
    void importTable(String fileName) throws IOException, JAXBException;
    String printTables();
    void closeTable(String fileName);
    void exportTable(String tableName) throws JAXBException;
    void exportTableAs(String oldFileName,String newFileName) throws JAXBException;
    String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
}
