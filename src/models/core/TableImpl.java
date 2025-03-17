package models.core;
import models.exception.DomainException;
import interfaces.Column;
import interfaces.Row;
import interfaces.Table;
import models.common.MessageLogger;
import models.enums.ColumnOperation;
import models.enums.ColumnType;

import java.util.*;
import java.util.stream.Collectors;

public class TableImpl implements Table {
    private String name;
    private final List<Column> columns;
    private final List<Row> rows;
    private final Scanner scanner;

    public TableImpl(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.scanner = new Scanner(System.in);
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
    public double aggregate(int columnIndex, String value, int targetColumnIndex , ColumnOperation columnOperation) {
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
                .filter(r -> r.getAttributes().get(searchedColumn).equals(value)
                && !r.getAttributes().get(searchedColumn).equals("Null"))
                .collect(Collectors.toList());

        if(matchedRows.isEmpty()){

            throw new DomainException("Impossible to aggregate.");
        }

        double result;
        if(columnOperation == ColumnOperation.MAXIMUM){
            result = matchedRows
                    .stream()
                    .mapToDouble(r -> Double.parseDouble(r.getAttributes().get(targetColumn)))
                    .max()
                    .orElseThrow();
        } else if(columnOperation == ColumnOperation.MINIMUM){

            result = matchedRows
                    .stream()
                    .mapToDouble(r -> Double.parseDouble(r.getAttributes().get(targetColumn)))
                    .min()
                    .orElseThrow();
        } else if(columnOperation == ColumnOperation.PRODUCT){

            result = matchedRows.stream()
                    .mapToDouble(row -> Double.parseDouble(row.getAttributes().get(targetColumn)))
                    .reduce(1, (a, b) -> a * b);
        } else if(columnOperation == ColumnOperation.SUM){

            result = matchedRows
                    .stream()
                    .mapToDouble(r -> Double.parseDouble(r.getAttributes().get(targetColumn)))
                    .sum();
        } else {

            throw new DomainException("Invalid type to aggregate.");
        }
        return result;
    }

    @Override
    public String printColumnTypes() {
        if(this.columns.isEmpty()){

            return String.format("Table %s has no column.",this.name);
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (Column column :
                this.columns) {
            stringBuilder.append(column).append("\n");
        }

        return stringBuilder.toString().trim().replaceAll(",$", "");
    }

    @Override
    public void printRows() {
        if(this.rows.isEmpty()){

            MessageLogger.log(String.format("Table %s has no records.", this.name));
        } else {

            initPagPrinting(this.rows);
        }
    }

    @Override
    public void selectAllRowsContain(int columnIndex,String value) {
        if(columnIndex > this.columns.size()){

            throw new DomainException("Index out of range!Try again ;)");
        }
        Column column = new ArrayList<>(this.columns)
                .get(columnIndex);

        List<Row> selectedRows = this.rows.stream()
                .filter(r -> r.getAttributes().get(column).equals(value))
                .collect(Collectors.toList());

        if(selectedRows.isEmpty()){

            MessageLogger.log("No rows match the value at given index.");
        } else {

            initPagPrinting(selectedRows);
        }
    }

    @Override
    public void addColumn(Column column) {
        this.columns.add(column);

        if(!rows.isEmpty()) {
            for (Row row :
                    this.rows) {
                row.getAttributes().put(column,"Null");
            }
        }
    }

    @Override
    public String updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue, String newValue) {
        if(index > this.columns.size() || targetIndex > this.columns.size()){

            throw new DomainException("Index out of range models.exception");
        }

        Column column = this.columns.get(index);
        Column column1 = this.columns.get(targetIndex);

        List<Row> rows = this.rows
                .stream().filter(r -> r.getAttributes().get(column).equals(oldValue))
                .collect(Collectors.toList());

        if(rows.isEmpty()){
            return "0 rows affected.";
        }

        for (Row row :
                rows) {
            row.getAttributes().put(column1,newValue);
        }
        return String.format("%d rows affected.",rows.size());
    }

    @Override
    public String deleteTableWhereRowContainsAt(int index, String value) {
        if(index >= this.columns.size()){

            throw new DomainException("There is no column at given index.");
        }
        Column column = this.columns.get(index);

        if(this.rows.stream().anyMatch(r ->  r.getAttributes().get(column).equals(value))){

            List<Row> roesToDelete = this.rows.stream()
                    .filter(r -> r.getAttributes().get(column).equals(value))
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

                row.getAttributes().put(this.columns.get(i),values[i]);
            }
            this.rows.add(row);
        }
    }

    @Override
    public void rename(String name) {
        this.name = name;
    }

    @Override
    public int getCountRowsContainAt(int index,String value) {
        if(index >= this.columns.size()){

            throw new DomainException("There is no column at given index.");
        }

        Column column = this.columns.get(index);
        return (int)this.rows
                .stream()
                .filter(r -> r.getAttributes().get(column).equals(value))
                .count();
    }

    private void initPagPrinting(List<Row> rows) {
        List<List<Row>> all = new ArrayList<>();

        for (int i = 0; i < rows.size(); i += 5) {

            int end = Math.min(i + 5, rows.size());
            all.add(new ArrayList<>(rows.subList(i, end)));
        }

        for(int i = 0; i < all.size(); i++){
            MessageLogger.log("---------------------------------------");
            StringBuilder stringBuilder = new StringBuilder();

            for (Row row :
                    all.get(i)) {
                stringBuilder.append(row).append("\n");
            }

            MessageLogger.log(stringBuilder.toString().trim());
            String command = scanner.nextLine();

            while (true) {

                if (Objects.equals(command, "next")) {
                    if (i + 1 >= all.size()) {

                        MessageLogger.log("No more records");
                    } else {
                        break;
                    }
                } else if (Objects.equals(command, "previous")) {
                    if (i == 0) {

                        MessageLogger.log("This is page 1.");
                    } else {

                        i-=2;
                        break;
                    }

                } else if (Objects.equals(command, "exit")) {
                    return;
                } else {
                    MessageLogger.log("Invalid command!");
                    return;
                }

                command = scanner.nextLine();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.columns.size(); i++){
            if(i != this.columns.size() - 1){
                stringBuilder.append(this.columns.get(i)).append(", ");
                continue;
            }
            stringBuilder.append(this.columns.get(i)).append("\n");
        }

        this.rows.forEach(r -> stringBuilder.append(r).append("\n"));

        return stringBuilder
                .toString()
                .trim();
    }
}