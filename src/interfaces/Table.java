package interfaces;
import models.enums.ColumnOperation;

import java.nio.file.Path;
import java.util.List;

/**
 * Интерфейс, който описва операциите, които могат да се извършват върху таблица в база данни.
 * Таблицата съдържа колони и редове и предлага възможности за манипулиране на данни в тях.
 */
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
    void rename(Path baseDirectory,String name);
    int getCountRowsContainAt(int index,String value);
}
