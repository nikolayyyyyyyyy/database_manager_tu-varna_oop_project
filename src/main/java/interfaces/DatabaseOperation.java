package interfaces;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface DatabaseOperation {
    void importTable(String path) throws IOException, JAXBException;
    String printTables();
    void closeTable(String fileName);
    void exportTable(String name);
    void exportTableAs(String pathName,String fileName);
    String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
}
