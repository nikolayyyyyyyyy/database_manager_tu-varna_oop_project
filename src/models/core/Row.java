package models.core;
import interfaces.RowManager;
import java.util.ArrayList;
import java.util.List;

public class Row implements RowManager {
    private final List<String> records;

    public Row(){
        this.records = new ArrayList<>();
    }

    @Override
    public void addValue(String value) {
        this.records.add(value);
    }

    @Override
    public String getValueAt(int index) {
        return this.records.get(index);
    }

    @Override
    public void updateValueAt(int index,String value) {
        this.records.set(index,value);
    }

    @Override
    public String print() {
        return String.join(" ",this.records);
    }
}
