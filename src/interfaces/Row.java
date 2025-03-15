package interfaces;

public interface Row {

    void addAttribute(Column column,String attribute);
    String getAttributeFromColumn(Column column);
    String print();
    String describe();
}
