package interfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface Database {

    Path getBaseDirectory();
    Table getTable(String tableName);
    void openTable(String tableName) throws IOException;
    void saveTable(String tableName);
    void saveTableAs(String table,String newTableName);
    void closeTable(String tableName);
    String printLoadedTables();
    String innerJoinTables(String firstTable,int firstColIndex,String secondTable,int secondColIndex);
}
