package interfaces;
import java.io.IOException;

public interface Database {
    void openTable(String fileName) throws IOException;
    String printTables();
    void closeTable(String fileName);
    void saveTable(String tableName) throws IOException;
    void saveTableAs(String oldFileName,String newFileName) throws IOException;
    String innerJoinTables(String firstTable,int firstColIndex,String secondTable,int secondColIndex);
    String printHelp();
    Table getTable(String name);
}
