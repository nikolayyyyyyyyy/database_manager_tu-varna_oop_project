package models.core;
import interfaces.Column;
import interfaces.Row;
import java.util.LinkedHashMap;
import java.util.Map;

public class RowImpl implements Row {
    private final Map<Column,String> attributes;

    public RowImpl(){
        this.attributes = new LinkedHashMap<>();
    }

    @Override
    public void addAttribute(Column column,String attribute) {
        this.attributes.put(column,attribute);
    }

    @Override
    public String getAttributeFromColumn(Column column) {
        return this.attributes.get(column);
    }

    @Override
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String values :
                this.attributes.values()) {
            stringBuilder.append(values).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public String describe() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Column column :
                this.attributes.keySet()){

            stringBuilder
                    .append(column.getName())
                    .append(": ")
                    .append(this.attributes.get(column))
                    .append(", ");
        }
        return stringBuilder.toString().trim().replaceAll(",$", "");
    }
}
