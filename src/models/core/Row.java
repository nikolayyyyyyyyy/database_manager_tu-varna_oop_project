package models.core;
import java.util.ArrayList;
import java.util.List;

public class Row {
    private final List<String> records;

    public Row(){
        this.records = new ArrayList<>();
    }

    public List<String> getRecords(){
        return this.records;
    }
}
