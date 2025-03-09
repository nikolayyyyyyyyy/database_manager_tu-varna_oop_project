package interfaces;

import models.Record;

public interface TableOperation {

    public String printColumnTypes();
    public String printRows();
    public String selectAllRowsContain(String value);
    public void addColumn(String name,String type);
    public void updateColumnValue(String column, String oldValue,String newValue);
    public void deleteTableWhereColumnContains(String column,String value);
    public void addRow(Record record);
    public void rename(String name);
    public int getCountRowsContain(String value);
}
