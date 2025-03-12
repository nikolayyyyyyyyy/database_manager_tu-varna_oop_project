package interfaces;

import models.core.Table;

import java.io.IOException;

public interface DatabaseManager {
    boolean openTable(String fileName) throws IOException;
    String printTables();
    void closeTable(String fileName);
    void saveTable(String tableName);
    void saveTableAs(String oldFileName,String newFileName) throws IOException;
    String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
    String printHelp();
    Table getTable(String name);
}
