package models.core;
import interfaces.RowOperation;

import java.util.ArrayList;
import java.util.List;

public class Row implements RowOperation {
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
        String record = this.records.get(index);
        record = value;
    }

    @Override
    public String print() {
        return String.join(" ",this.records);
    }
}
