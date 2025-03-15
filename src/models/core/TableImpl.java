package models.core;
import exception.DomainException;
import interfaces.Column;
import interfaces.Row;
import interfaces.Table;
import models.common.BaseFileValidator;

import java.util.*;
import java.util.stream.Collectors;

public class TableImpl implements Table {
    private String name;
    private final List<Column> columns;
    private final List<Row> rows;

    public TableImpl(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    @Override
    public double aggregate(int columnIndex, String value,int targetColumnIndex ,ColumnOperation columnOperation) {
        if(columnIndex >= this.columns.size()
        || targetColumnIndex >= this.columns.size()){

            throw new DomainException("No column at given index");
        }
        Column searchedColumn = this.columns.get(columnIndex);
        Column targetColumn = this.columns.get(targetColumnIndex);

        if(targetColumn.getColumnType() == ColumnType.STRING){

            throw new DomainException("Cannot aggregate on STRING");
        }

        List<Row> matchedRows = this.rows
                .stream()
                .filter(r -> r.getAttributeFromColumn(searchedColumn).equals(value))
                .collect(Collectors.toList());

        if(matchedRows.isEmpty()){

            throw new DomainException("Impossible to aggregate.");
        }

        double result;
        if(columnOperation == ColumnOperation.MAXIMUM){
            result = matchedRows
                    .stream()
                    .mapToDouble(r -> Double.parseDouble(r.getAttributeFromColumn(targetColumn)))
                    .max()
                    .orElseThrow();
        } else if(columnOperation == ColumnOperation.MINIMUM){

            result = matchedRows
                    .stream()
                    .mapToDouble(r -> Double.parseDouble(r.getAttributeFromColumn(targetColumn)))
                    .min()
                    .orElseThrow();
        } else if(columnOperation == ColumnOperation.PRODUCT){

            result = matchedRows.stream()
                    .mapToDouble(row -> Double.parseDouble(row.getAttributeFromColumn(targetColumn)))
                    .reduce(1, (a, b) -> a * b);
        } else if(columnOperation == ColumnOperation.SUM){

            result = matchedRows
                    .stream()
                    .mapToDouble(r -> Double.parseDouble(r.getAttributeFromColumn(targetColumn)))
                    .sum();
        } else {

            throw new DomainException("Invalid type to aggregate.");
        }
        return result;
    }

    @Override
    public List<Row> getRows() {
        return this.rows;
    }

    @Override
    public List<Column> getColumns() {
        return this.columns;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String printColumnTypes() {
        if(this.columns.isEmpty()){

            return String.format("Table %s has no column.",this.name);
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (Column column :
                this.columns) {
            stringBuilder
                    .append(column.getName())
                    .append(": ")
                    .append(column.getColumnType())
                    .append(" ");
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public void printRows() {
        if(this.rows.isEmpty()){

            System.out.printf("Table %s has no records.", this.name);
        }


    }

    @Override
    public String selectAllRowsContain(int columnIndex,String value) {
        if(columnIndex > this.columns.size()){

            throw new DomainException("Index out of range!Try again ;)");
        }
        Column column = new ArrayList<>(this.columns)
                .get(columnIndex);

        List<Row> selectedRows = this.rows.stream()
                .filter(r -> r.getAttributeFromColumn(column).equals(value))
                .collect(Collectors.toList());

        if(selectedRows.isEmpty()){

            return "No rows match the value at given index.";
        }
        StringBuilder sb = new StringBuilder();

        for (Row row :
                selectedRows) {
            sb.append(row.describe()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public void addColumn(Column column) {
        if (this.columns.contains(column)){

            throw new DomainException("Column already exist");
        }
        this.columns.add(column);

        if(!rows.isEmpty()) {
            for (Row row :
                    this.rows) {
                row.addAttribute(column,"Null");
            }
        }
    }

    @Override
    public String updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue, String newValue) {
        if(index > this.columns.size() || targetIndex > this.columns.size()){

            throw new DomainException("Index out of range exception");
        }

        Column column = this.columns.get(index);
        Column column1 = this.columns.get(targetIndex);

        List<Row> rows = this.rows
                .stream().filter(r -> r.getAttributeFromColumn(column).equals(oldValue))
                .collect(Collectors.toList());

        if(rows.isEmpty()){
            return "0 rows affected.";
        }

        for (Row row :
                rows) {
            row.addAttribute(column1,newValue);
        }
        return String.format("%d rows affected.",rows.size());
    }

    @Override
    public String deleteTableWhereRowContainsAt(int index, String value) {
        if(index >= this.columns.size()){

            throw new DomainException("There is no column at given index.");
        }
        Column column = this.columns.get(index);

        if(this.rows.stream().anyMatch(r -> r.getAttributeFromColumn(column).equals(value))){

            List<Row> roesToDelete = this.rows.stream()
                    .filter(r -> r.getAttributeFromColumn(column).equals(value))
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

            RowImpl row = new RowImpl();
            for(int i = 0; i < values.length; i++) {

                row.addAttribute(this.columns.get(i),values[i]);
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
        if(index >= this.columns.size()){

            throw new DomainException("There is no column at given index.");
        }

        Column column = this.columns.get(index);
        return (int)this.rows
                .stream()
                .filter(r -> r.getAttributeFromColumn(column).equals(value))
                .count();
    }

}