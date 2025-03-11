package interfaces;

import models.Row;

public interface TableOperation {

    String printColumnTypes();
    String printRows();
    String selectAllRowsContain(int columnIndex,String value);
    void addColumn(String name,String type);
    void updateColumnValue(String column, String oldValue,String newValue);
    void deleteTableWhereColumnContains(String column,String value);
    void addRow(Row record);
    void rename(String name);
    int getCountRowsContain(String value);
}
