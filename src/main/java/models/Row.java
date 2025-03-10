package models;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Row {
    private final List<String> records;

    public Row(){
        this.records = new ArrayList<>();
    }

    @XmlElement(name = "values")
    public List<String> getRecords(){
        return this.records;
    }
}
