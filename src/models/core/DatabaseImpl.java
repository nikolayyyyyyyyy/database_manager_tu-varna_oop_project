package models.core;
import interfaces.Column;
import interfaces.Database;
import models.command.ExitCommand;
import models.common.MessageLogger;
import models.enums.ColumnType;
import models.exception.DomainException;
import interfaces.Row;
import interfaces.Table;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Имплементация на интерфейса {@link Database}, която предоставя функционалности за управление на таблици в база от данни.
 * Този клас позволява зареждане, записване, променяне и обединяване на таблици в базата.
 */
public class DatabaseImpl implements Database {
    private final Map<String, Table> tables;
    private final Path baseDirectory;

    /**
     * Конструктор за създаване на нова база от данни с начален каталог за таблици.
     */
    public DatabaseImpl() {
        this.tables = new LinkedHashMap<>();
        this.baseDirectory = Path.of("Catalog");
        validateDirectory();
    }

    /**
     * Връща основната директория на базата от данни, където се съхраняват таблиците.
     *
     * @return Път до основната директория.
     */
    @Override
    public Path getBaseDirectory() {
        return this.baseDirectory;
    }

    /**
     * Връща таблица по нейното име.
     *
     * @param tableName Името на таблицата.
     * @return Обект от тип {@code Table}.
     * @throws DomainException Ако таблицата не е заредена.
     */
    @Override
    public Table getTable(String tableName) {
        if(!this.tables.containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        return this.tables.get(tableName);
    }

    /**
     * Зарежда таблица от файл, ако таблицата не съществува, тя се създава.
     * Ако файлът съдържа редове и колони, те се добавят към таблицата.
     *
     * @param fileName Името на файла за таблицата.
     * @throws IOException Ако възникне проблем при работа с файловете.
     */
    @Override
    public void openTable(String fileName) throws IOException {
        if (!baseDirectory.resolve(fileName).toFile().exists()) {

            Files.createFile(baseDirectory.resolve(fileName));
        }

        Table table = new TableImpl(fileName);
        if (!Files.readAllLines(baseDirectory.resolve(fileName)).isEmpty()) {


            String[] columnPairs = Files
                    .readAllLines(baseDirectory.resolve(fileName))
                    .get(0)
                    .split(", ");

            for (int i = 0; i < columnPairs.length; i += 2) {
                String name = columnPairs[i].split(": ")[1].trim();
                String type = columnPairs[i + 1].split(": ")[1].trim();
                Column column = new ColumnImpl(name, ColumnType.valueOf(type));

                table.addColumn(column);
            }

            List<String> rows = Files
                    .readAllLines(baseDirectory.resolve(fileName))
                    .stream()
                    .skip(1)
                    .collect(Collectors.toList());

            for (String row :
                    rows) {
                String[] records = Arrays.stream(row.split(", "))
                        .map(r -> r.split(": "))
                        .map(sr -> sr[1])
                        .toArray(String[]::new);

                table.addRow(records);
            }
        }

        if(this.tables.containsKey(table.getName())){

            throw new DomainException(String.format("Table %s is already loaded.",table.getName()));
        }
        this.tables.put(table.getName(),table);
    }

    /**
     * Записва таблица с ново име в основната директория.
     *
     * @param table Името на таблицата за запазване.
     * @param newTableName Новото име за таблицата.
     */
    @Override
    public void saveTableAs(String table, String newTableName) {
        if(!this.tables.containsKey(table)){

            throw new DomainException(String.format("Table %s is not loaded.",table));
        }

        Table renamedTable = this.tables.get(table);
        renamedTable.rename(baseDirectory,newTableName);
        saveTable(renamedTable.getName());
    }

    /**
     * Затваря заредена таблица и я премахва от базата от данни.
     *
     * @param tableName Името на таблицата за затваряне.
     */
    @Override
    public void closeTable(String tableName) {
        if(!this.tables.containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        this.tables.remove(tableName);
    }

    /**
     * Записва съдържанието на таблица в основната директория.
     *
     * @param tableName Името на таблицата за запис.
     */
    @Override
    public void saveTable(String tableName){
        try {
            if(!this.tables.containsKey(tableName)){

                throw new DomainException(String.format("Table %s is not loaded.",tableName));
            }

            Files.writeString(baseDirectory.resolve(tableName), this.tables.get(tableName).toString(),
                    StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException exception){

            MessageLogger.log(String.format("Error saving table %s",tableName));
            System.exit(0);
        }catch (DomainException domainException){

            MessageLogger.log(domainException.getMessage());
        }
    }

    /**
     * Връща текст с всички заредени таблици.
     *
     * @return Текст, съдържащ имената на заредените таблици.
     */
    @Override
    public String printLoadedTables() {
        StringBuilder sb = new StringBuilder();
        if(this.tables.isEmpty()){

            return "No tables loaded.";
        }
        sb.append("Opened tables:").append("\n");

        for (String table :
                this.tables.keySet()) {

            sb.append(table).append("\n");
        }

        return sb.toString().trim();
    }

    /**
     * Извършва обединение на две таблици по съвпадение на стойности в посочени колони.
     *
     * @param firstTable Името на първата таблица.
     * @param firstColIndex Индекс на колоната в първата таблица за обединение.
     * @param secondTable Името на втората таблица.
     * @param secondColIndex Индекс на колоната във втората таблица за обединение.
     * @return Стринг с името на новосъздадената обединена таблица.
     */
    @Override
    public String innerJoinTables(String firstTable, int firstColIndex,
                                  String secondTable, int secondColIndex) {
        if(!this.tables.containsKey(firstTable)){

            throw new DomainException(String.format("Table %s is not loaded.",firstTable));
        }

        if(!this.tables.containsKey(secondTable)){

            throw new DomainException(String.format("Table %s is not loaded.",secondTable));
        }

        Table first = this.tables.get(firstTable);
        if(firstColIndex >= first.getColumns().size() || firstColIndex < 0){

            throw new DomainException("First column index is out of bounds");
        }

        Table second = this.tables.get(secondTable);
        if(secondColIndex >= second.getColumns().size() || secondColIndex < 0){
            throw new DomainException("Second column index is out of bounds");
        }

        Table joinedTable = new TableImpl(firstTable + "_" + secondTable);
        joinedTable.getColumns().addAll(first.getColumns());
        joinedTable.getColumns().addAll(second.getColumns());

        for (Row firstTableRow :
                first.getRows()) {

            for (Row secondTableRow :
                    second.getRows()) {

                if(firstTableRow
                        .getAttributes().get(first.getColumns().get(firstColIndex))
                        .equals(secondTableRow.getAttributes().get(second.getColumns().get(secondColIndex)))){

                    String row = firstTableRow + ", " + secondTableRow;
                    joinedTable.addRow(Arrays.stream(row.split(", "))
                            .map(r -> r.split(": "))
                            .map(sr -> sr[1])
                            .toArray(String[]::new));
                }
            }
        }

        this.tables.put(joinedTable.getName(),joinedTable);

        return "Joined table: " + joinedTable.getName();
    }
    /**
     * Проверява дали основната директория за таблиците съществува и я създава, ако е необходимо.
     */
    private void validateDirectory() {
        try {
            if (!this.baseDirectory.toFile().exists()) {
                Files.createDirectory(this.baseDirectory);
            }
        }catch (IOException e){

            MessageLogger.log("IO exception.");
            new ExitCommand().execute();
        }
    }
}
