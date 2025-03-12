package models.core;
import exception.DomainException;
import interfaces.RowManager;
import interfaces.TableManager;
import models.common.BaseFileValidator;
import java.util.*;
import java.util.stream.Collectors;

public class Table implements TableManager {
    private String name;
    private final Set<Column> columns;
    private final List<RowManager> rows;

    public Table(String name) {
        this.name = name;
        this.columns = new LinkedHashSet<>();
        this.rows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public List<RowManager> getRows() {
        return rows;
    }

    @Override
    public String printColumnTypes() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Column columnType :
                this.columns) {
            stringBuilder
                    .append(columnType
                    .getType()).append("\n");
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public String printRows() {
        StringBuilder stringBuilder = new StringBuilder();

        for (RowManager row :
                this.rows) {

            stringBuilder.append(row.print()).append("\n");
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public String selectAllRowsContain(int columnIndex,String value) {
        StringBuilder sb = new StringBuilder();

        for (RowManager row :
                this.rows.stream()
                        .filter(r -> r.getValueAt(columnIndex).equals(value))
                        .collect(Collectors.toList())) {

            sb.append(row.print()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public void addColumn(String name, ColumnType type) {
        Column column = new Column(name,type);
        if (this.columns.contains(column)){

            throw new DomainException("Column already exist");
        }
        this.columns.add(column);

    if(!rows.isEmpty()) {
        for (RowManager row :
                this.getRows()) {
            row.addValue("Null");
            }
        }
    }

    @Override
    public void updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue, String newValue) {
        if(index > this.columns.size() || targetIndex > this.columns.size()){

            throw new DomainException("Index out of range exception");
        }

        List<RowManager> rows = this.rows
                .stream().filter(r -> r.getValueAt(index).equals(oldValue))
                .collect(Collectors.toList());

        for (RowManager row :
                rows) {
            row.updateValueAt(targetIndex,newValue);
        }
    }

    @Override
    public void deleteTableWhereRowContainsAt(int index, String value) {
        this.rows
                .removeIf(r -> r.getValueAt(index).equals(value));
    }

    @Override
    public void addRow(String[] values) {
        if(values.length > this.columns.size()){

            throw new DomainException("Too many values for the column count.");
        } else {

            Row row = new Row();
            for (String value :
                    values) {
                row.addValue(value);
            }
            this.rows.add(row);
        }
    }

    @Override
    public void rename(String name) {
        if(BaseFileValidator.isFileExist(name)){

            throw new DomainException("File with this name already exist.");
        } else {
            this.name = name;
        }
    }

    @Override
    public int getCountRowsContainAt(int index,String value) {
        return (int)this.rows
                .stream()
                .filter(r -> r.getValueAt(index).equals(value))
                .count();
    }

}