package models.core;

public class Column {
    private String name;
    private ColumnType type;

    public Column(String name, ColumnType type){
        this.name = name;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }
}
