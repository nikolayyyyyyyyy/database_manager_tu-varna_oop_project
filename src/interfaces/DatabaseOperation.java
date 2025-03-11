package interfaces;

import models.Table;

import java.io.IOException;
import java.nio.file.Path;

public interface DatabaseOperation {
    void openTable(String fileName) throws IOException;
    String printTables();
    void closeTable(String fileName);
    void saveTable(String tableName);
    void saveTableAs(String oldFileName,String newFileName) throws IOException;
    String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
    String printHelp();
    Table getTable(String name);
    Path getBaseDirectory();
}
