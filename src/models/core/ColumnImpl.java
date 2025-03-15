package models.core;
import interfaces.Column;
import java.util.*;

public class ColumnImpl implements Column {
    private final String name;
    private final ColumnType type;

    public ColumnImpl(String name, ColumnType type){
        this.name = name;
        this.type = type;
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
        ColumnImpl columnImpl = (ColumnImpl) o;
        return Objects.equals(name, columnImpl.name) && type == columnImpl.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
