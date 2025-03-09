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
}
