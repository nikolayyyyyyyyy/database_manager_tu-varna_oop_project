package models.core;
import interfaces.RowOperation;
import interfaces.TableOperation;
import models.common.ErrorLogger;
import models.common.FileValidator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Table implements TableOperation {
    private String name;
    private final Set<Column> columns;
    private final List<RowOperation> rows;

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

    public List<RowOperation> getRows() {
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

        return stringBuilder.toString();
    }

    @Override
    public String printRows() {
        StringBuilder stringBuilder = new StringBuilder();

        for (RowOperation row :
                this.rows) {

            stringBuilder.append(row.print()).append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String selectAllRowsContain(int columnIndex,String value) {
        StringBuilder sb = new StringBuilder();

        for (RowOperation row :
                this.rows.stream()
                        .filter(r -> r.getValueAt(columnIndex).equals(value))
                        .collect(Collectors.toList())) {

            sb.append(row.print()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public void addColumn(String name, ColumnType type) {
        Column column = new Column(name,type);
        this.columns.add(column);

        for (RowOperation row :
                this.getRows()) {
            row.addValue("Null");
        }
    }

    @Override
    public void updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue, String newValue) {
        List<RowOperation> rows = this.rows
                .stream().filter(r -> r.getValueAt(index).equals(oldValue))
                .collect(Collectors.toList());

        for (RowOperation row :
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
            ErrorLogger.log("Length of add row values is more than column number." + LocalDate.now());
            return;
        }

        Row row = new Row();
        for (String value :
                values) {
            row.addValue(value);
        }
    }

    @Override
    public void rename(String name) {
        if(FileValidator.isFileExist(name)){

            ErrorLogger.log("Name is already taken,rename with another name." + LocalDate.now());
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