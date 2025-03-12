package models.core;

import java.util.Objects;

public class Column {
    private final String name;
    private final ColumnType type;

    public Column(String name, ColumnType type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
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
