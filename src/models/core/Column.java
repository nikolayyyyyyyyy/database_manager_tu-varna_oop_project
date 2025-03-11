package models.core;

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
}
