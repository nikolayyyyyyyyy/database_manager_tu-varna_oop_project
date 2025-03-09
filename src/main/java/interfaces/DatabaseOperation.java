package interfaces;

import java.io.IOException;

public interface DatabaseOperation {

    public void importTable(String path) throws IOException;
    public String printTables();
    public void openTable(String fileName);
    public void closeTable(String fileName);
    public void saveTable(String name);
    public void saveAs(String pathName,String fileName);
    public String innerJoinTables(String firstTable,String firstValue,String secondTable,String secondValue);
}
