package interfaces;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Интерфейс, който дефинира основните операции за работа с база данни, като отваряне, затваряне, запазване и присъединяване на таблици.
 * Той предоставя методи за работа с таблици в рамките на базата данни.
 */
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
