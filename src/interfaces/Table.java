package interfaces;
import models.core.ColumnType;
import java.util.Set;

public interface Table {

    Set<Row> getRows();
    Set<Column> getColumns();
    String getName();
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
