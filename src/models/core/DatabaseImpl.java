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

public class DatabaseImpl implements Database {
    private final Map<String, Table> tables;
    private final Path baseDirectory;

    public DatabaseImpl() {
        this.tables = new LinkedHashMap<>();
        this.baseDirectory = Path.of("Catalog");
        validateDirectory();
    }

    @Override
    public Path getBaseDirectory() {
        return this.baseDirectory;
    }

    @Override
    public Table getTable(String tableName) {
        if(!this.tables.containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        return this.tables.get(tableName);
    }

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

    @Override
    public void saveTableAs(String table, String newTableName) {
        if(!this.tables.containsKey(table)){

            throw new DomainException(String.format("Table %s is not loaded.",table));
        }

        Table renamedTable = this.tables.get(table);
        renamedTable.rename(baseDirectory,newTableName);
        saveTable(renamedTable.getName());
    }

    @Override
    public void closeTable(String tableName) {
        if(!this.tables.containsKey(tableName)){

            throw new DomainException(String.format("Table %s is not loaded.",tableName));
        }

        this.tables.remove(tableName);
    }

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
