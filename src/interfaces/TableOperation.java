package interfaces;

import models.core.ColumnType;

public interface TableOperation {

    String printColumnTypes();
    String printRows();
    String selectAllRowsContain(int columnIndex,String value);
    void addColumn(String name, ColumnType type);
    void updateColumnValue(String column, String oldValue,String newValue);
    void deleteTableWhereColumnContains(String column,String value);
    void addRow(String[] values);
    void rename(String name);
    int getCountRowsContain(String value);
}
