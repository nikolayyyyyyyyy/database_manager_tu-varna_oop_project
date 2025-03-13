package interfaces;

public interface Row {

    public void addValue(String value);
    public String getValueAt(int index);
    public void updateValueAt(int index,String value);
    public String print();
}
