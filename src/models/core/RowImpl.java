package models.core;
import java.util.ArrayList;
import java.util.List;

public class RowImpl implements interfaces.Row {
    private final List<String> records;

    public RowImpl(){
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
