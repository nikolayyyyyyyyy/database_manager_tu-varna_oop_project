package interfaces;

import models.core.ColumnType;

public interface TableManager {

    String printColumnTypes();
    void printRows();
    String selectAllRowsContain(int columnIndex,String value);
    void addColumn(ColumnType type,String name);
    String updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue,String newValue);
    String deleteTableWhereRowContainsAt(int index,String value);
    void addRow(String[] values);
    void rename(String name);
    int getCountRowsContainAt(int index,String value);
}
