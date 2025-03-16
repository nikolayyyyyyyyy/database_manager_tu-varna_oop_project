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
    public Map<Column, String> getAttributes() {
        return this.attributes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Column column :
                this.attributes.keySet()) {

            stringBuilder
                    .append(column.getName())
                    .append(": ")
                    .append(this.attributes.get(column))
                    .append(", ");
        }
        return stringBuilder.toString().trim().replaceAll(",$", "");
    }
}
