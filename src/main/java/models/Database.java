package models;
import java.util.LinkedHashMap;
import java.util.Map;


public class Database  {
    private Map<String, Table> openTables;
    private String base;

    public Database(){
        this.openTables = new LinkedHashMap<>();
        this.base = "catalog";
    }

    public Map<String, Table> getOpenTables() {
        return openTables;
    }

    public void setOpenTables(Map<String, Table> openTables) {
        this.openTables = openTables;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
