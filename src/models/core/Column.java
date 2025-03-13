package models.core;
import interfaces.ColumnManager;
import java.util.*;

public class Column implements ColumnManager {
    private final String name;
    private final ColumnType type;

    public Column(String name, ColumnType type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String print() {
        return String.format("Name: %s Type: %s",this.name,this.type);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ColumnType getColumnType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return Objects.equals(name, column.name) && type == column.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
