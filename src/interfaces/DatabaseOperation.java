package interfaces;

public interface DatabaseOperation {
    void openTable(String fileName);
    String printTables();
    void closeTable(String fileName);
    void saveTable(String tableName);
    void saveTableAs(String oldFileName,String newFileName);
    String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
    String printHelp();
}
