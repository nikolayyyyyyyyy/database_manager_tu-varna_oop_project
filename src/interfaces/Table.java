package interfaces;
import models.enums.ColumnOperation;
import java.util.List;

public interface Table {

    double aggregate(int columnIndex, String value,int targetColumnIndex, ColumnOperation columnOperation);
    List<Row> getRows();
    List<Column> getColumns();
    String getName();
    String printColumnTypes();
    void printRows();
    void selectAllRowsContain(int columnIndex,String value);
    void addColumn(Column column);
    String updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue,String newValue);
    String deleteTableWhereRowContainsAt(int index,String value);
    void addRow(String[] values);
    void rename(String name);
    int getCountRowsContainAt(int index,String value);
}
