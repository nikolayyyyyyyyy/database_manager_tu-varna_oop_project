package models.core;
import interfaces.TableOperation;
import models.common.ErrorLogger;
import models.common.FileValidator;

import java.time.LocalDate;
import java.util.*;

public class Table implements TableOperation {
    private String name;
    private final Set<Column> columns;
    private final List<Row> rows;

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

    public List<Row> getRows() {
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

        for (Row row :
                this.rows) {

            stringBuilder.append(String.join(" ",row.getRecords()));
        }

        return stringBuilder.toString();
    }

    @Override
    public String selectAllRowsContain(int columnIndex,String value) {

        return "";
    }

    @Override
    public void addColumn(String name, ColumnType type) {
        Column column = new Column(name,type);
        this.columns.add(column);

        for (Row row :
                this.getRows()) {
            row.getRecords().add("Null");
        }
    }

    @Override
    public void updateColumnValue(String column, String oldValue, String newValue) {

    }

    @Override
    public void deleteTableWhereColumnContains(String column, String value) {

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
            row.getRecords().add(value);
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
    public int getCountRowsContain(String value) {
        return 0;
    }

}