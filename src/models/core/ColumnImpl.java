package models.core;
import interfaces.Column;

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
    public String toString() {
        return String.format("Name: %s, Type: %s",this.name,this.type);
    }
}
