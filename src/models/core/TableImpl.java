package models.core;
import models.exception.DomainException;
import interfaces.Column;
import interfaces.Row;
import interfaces.Table;
import models.common.MessageLogger;
import models.enums.ColumnOperation;
import models.enums.ColumnType;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Имплементация на интерфейса {@link Table}, която предоставя функционалности за
 * управление на редове и колони в таблица.
 */
public class TableImpl implements Table {
    private String name;
    private final List<Column> columns;
    private final List<Row> rows;
    private final Scanner scanner;

    /**
     * Конструктор за създаване на нова таблица с дадено име.
     *
     * @param name Името на таблицата.
     */
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

    /**
     * Отпечатва тип и име на всички колони в таблицата.
     *
     * @return Текст с типа и името на колоните или съобщение, ако няма колони.
     */
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

        return stringBuilder.toString().trim();
    }

    /**
     * Отпечатва всички редове в таблицата с възможност за странично преглеждане.
     */
    @Override
    public void printRows() {
        if(this.rows.isEmpty()){

            MessageLogger.log(String.format("Table %s has no records.", this.name));
        } else {

            initPagPrinting(this.rows);
        }
    }

    /**
     * Извършва селекция на всички редове, които съдържат дадена стойност в посочената колона.
     *
     * @param columnIndex Индекс на колоната.
     * @param value Стойността за търсене в колоната.
     */
    @Override
    public void selectAllRowsContain(int columnIndex,String value) {
        if(isIndexOutOfRange(columnIndex)){

            throw new DomainException("Index out of range!Try again ;)");
        }

        Column column = this.columns.get(columnIndex);

        List<Row> selectedRows = this.rows.stream()
                .filter(r -> r.getAttributes().get(column).equals(value))
                .collect(Collectors.toList());

        if(selectedRows.isEmpty()){

            MessageLogger.log("No rows match the value at given index.");
        } else {

            initPagPrinting(selectedRows);
        }
    }

    /**
     * Добавя нова колона в таблицата.
     * Ако таблицата съдържа редове, за всяка от тях се добавя стойност "Null" за новата колона.
     *
     * @param column Новата колона, която да бъде добавена.
     */
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

    /**
     * Обновява стойностите в дадена колона на редовете, които съдържат определена стойност.
     *
     * @param index Индекс на колоната, в която ще се извърши търсенето.
     * @param targetIndex Индекс на колоната, в която ще се извършва актуализацията.
     * @param oldValue Стойността, която ще се търси за замяна.
     * @param newValue Новата стойност, с която ще се замести старата.
     * @return Съобщение за броя на засегнатите редове.
     */
    @Override
    public String updateRowValueAtIndexWhereContainsAt(int index,int targetIndex, String oldValue, String newValue) {
        if(isIndexOutOfRange(index) || isIndexOutOfRange(targetIndex)){

            throw new DomainException("Index out of range.");
        }
        Column searchedColumn = this.columns.get(index);
        Column targetColumn = this.columns.get(targetIndex);

        List<Row> rows = this.rows
                .stream().filter(r -> r.getAttributes().get(searchedColumn).equals(oldValue))
                .collect(Collectors.toList());

        if(rows.isEmpty()){
            return "0 rows affected.";
        }

        for (Row row :
                rows) {
            row.getAttributes().put(targetColumn,newValue);
        }
        return String.format("%d rows affected.",rows.size());
    }

    /**
     * Изтрива редове от таблицата, които съдържат дадена стойност в определена колона.
     *
     * @param index Индекс на колоната.
     * @param value Стойността, за която да се изтрият редовете.
     * @return Съобщение за броя на изтритите редове.
     */
    @Override
    public String deleteTableWhereRowContainsAt(int index, String value) {
        if(isIndexOutOfRange(index)){

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

    /**
     * Добавя нов ред в таблицата с подадените стойности.
     *
     * @param values Масив със стойности за новия ред.
     */
    @Override
    public void addRow(String[] values) {
        if(values.length != this.columns.size()){

            throw new DomainException("Too many values for the column count.");
        } else {

            RowImpl row = new RowImpl();
            for(int i = 0; i < values.length; i++) {

                row.getAttributes().put(this.columns.get(i),values[i]);
            }
            this.rows.add(row);
        }
    }

    /**
     * Променя името на таблицата.
     * Ако таблицата с новото име вече съществува, се хвърля изключение.
     *
     * @param baseDirectory Директорията, в която се намират таблиците.
     * @param name Новото име на таблицата.
     */
    @Override
    public void rename(Path baseDirectory, String name) {
        if(baseDirectory.resolve(name).toFile().exists()){

            throw new DomainException(String.format("Table %s already exist.",name));
        }
        this.name = name;
    }

    /**
     * Връща броя на редовете, които съдържат дадена стойност в посочената колона.
     *
     * @param index Индекс на колоната.
     * @param value Стойността, която се търси.
     * @return Броят на редовете, които съдържат стойността.
     */
    @Override
    public int getCountRowsContainAt(int index,String value) {
        if(isIndexOutOfRange(index)){

            throw new DomainException("There is no column at given index.");
        }

        Column column = this.columns.get(index);
        return (int)this.rows
                .stream()
                .filter(r -> r.getAttributes().get(column).equals(value))
                .count();
    }

    /**
     * Извършва агрегация върху данни в таблицата. Поддържат се операциите: MAXIMUM, MINIMUM, PRODUCT и SUM.
     *
     * @param columnIndex Индекс на колоната, в която ще се извършва търсенето.
     * @param value Стойността, която ще се търси в колоната.
     * @param targetColumnIndex Индекс на колоната, върху която ще се извършва агрегацията.
     * @param columnOperation Тип на агрегацията (MAXIMUM, MINIMUM, PRODUCT, SUM).
     * @return Резултатът от агрегацията.
     */
    @Override
    public double aggregate(int columnIndex, String value, int targetColumnIndex , ColumnOperation columnOperation) {
        if(isIndexOutOfRange(columnIndex) || isIndexOutOfRange(targetColumnIndex)){

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

    private void initPagPrinting(List<Row> rows) {
        List<List<Row>> all = new ArrayList<>();

        for (int i = 0; i < rows.size(); i += 5) {
            all.add(rows.subList(i, Math.min(i + 5, rows.size())));
        }

        String command;
        for(int i = 0; i < all.size(); i++){

            MessageLogger.log("---------------------------------------");
            StringBuilder stringBuilder = new StringBuilder();

            for (Row row :
                    all.get(i)) {
                stringBuilder.append(row).append("\n");
            }

            MessageLogger.log(stringBuilder.toString().trim());
            MessageLogger.log("[Next]||[Previous]||[Exit]");

            while (!Objects.equals(command = scanner.nextLine(), "exit")) {

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

                } else {
                    MessageLogger.log("Invalid command!");
                }
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

    private boolean isIndexOutOfRange(int columnIndex) {
        return columnIndex >= this.columns.size() || columnIndex < 0;
    }
}