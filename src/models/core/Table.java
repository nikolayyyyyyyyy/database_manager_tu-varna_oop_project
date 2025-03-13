package models.core;
import exception.DomainException;
import interfaces.ColumnManager;
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
        if(this.columns.isEmpty()){

            return String.format("Table %s has no columns.",this.name);
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (ColumnManager column :
                this.columns) {
            stringBuilder.append(column.print()).append(" ");
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public void printRows() {
        if(this.rows.isEmpty()){

            System.out.printf("Table %s has no records.", this.name);
        }

        for (RowManager row :
                this.rows) {
            System.out.println(row.print());
        }
    }

    @Override
    public String selectAllRowsContain(int columnIndex,String value) {
        if(columnIndex > this.columns.size()){

            throw new DomainException("Index out of range!Try again ;)");
        }

        List<RowManager> selectedRows = this.rows.stream()
                .filter(r -> r.getValueAt(columnIndex).equals(value))
                .collect(Collectors.toList());

        if(selectedRows.isEmpty()){

            return "No rows match the value at given index.";
        }
        StringBuilder sb = new StringBuilder();

        for (RowManager row :
                selectedRows) {
            sb.append(row.print()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public void addColumn(ColumnType type,String name) {
        Column column = new Column(name,type);
        if (this.columns.contains(column)){

            throw new DomainException("Column already exist");
        }
        this.columns.add(column);

        if(!rows.isEmpty()) {
            for (RowManager row :
                    this.rows) {
                row.addValue("Null");
            }
        }
    }

    @Override
    public String updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue, String newValue) {
        if(index > this.columns.size() || targetIndex > this.columns.size()){

            throw new DomainException("Index out of range exception");
        }

        List<RowManager> rows = this.rows
                .stream().filter(r -> r.getValueAt(index).equals(oldValue))
                .collect(Collectors.toList());

        if(rows.isEmpty()){
            return "0 rows affected.";
        }

        for (RowManager row :
                rows) {
            row.updateValueAt(targetIndex,newValue);
        }
        return String.format("%d rows affected.",rows.size());
    }

    @Override
    public String deleteTableWhereRowContainsAt(int index, String value) {
        if(this.rows.stream().anyMatch(r -> r.getValueAt(index).equals(value))){

            List<RowManager> roesToDelete = this.rows.stream()
                    .filter(r -> r.getValueAt(index).equals(value))
                    .collect(Collectors.toList());

            this.rows.removeAll(roesToDelete);
            return String.format("%d rows deleted.",roesToDelete.size());
        }

        return "0 rows affected.";
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