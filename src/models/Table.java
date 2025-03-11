package models;
import interfaces.TableOperation;
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
    public void addColumn(String name, String type) {

    }

    @Override
    public void updateColumnValue(String column, String oldValue, String newValue) {

    }

    @Override
    public void deleteTableWhereColumnContains(String column, String value) {

    }

    @Override
    public void addRow(Row record) {

    }

    @Override
    public void rename(String name) {

    }

    @Override
    public int getCountRowsContain(String value) {
        return 0;
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
}
