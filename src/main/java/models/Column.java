package models;
import javax.xml.bind.annotation.XmlElement;

public class Column {
    private String name;
    private ColumnType type;

    public Column() {
    }

    public Column(String name, ColumnType type){
        this.name = name;
        this.type = type;
    }

    @XmlElement(name = "columName")
    public String getName() {
        return name;
    }

    @XmlElement(name = "type")
    public ColumnType getType() {
        return type;
    }
}
