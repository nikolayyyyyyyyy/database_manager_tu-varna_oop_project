package models;

import java.util.HashSet;
import java.util.Set;

public class Table {
    private String name;
    private Set<Column> columns;
    private Set<Record> records;

    public Table() {
    }

    public Table(String name) {
        this.name = name;
        this.records = new HashSet<>();
        this.columns = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
}
