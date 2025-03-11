package interfaces;

import models.core.ColumnType;

public interface TableOperation {

    String printColumnTypes();
    String printRows();
    String selectAllRowsContain(int columnIndex,String value);
    void addColumn(String name, ColumnType type);
    void updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue,String newValue);
    void deleteTableWhereRowContainsAt(int index,String value);
    void addRow(String[] values);
    void rename(String name);
    int getCountRowsContainAt(int index,String value);
}
