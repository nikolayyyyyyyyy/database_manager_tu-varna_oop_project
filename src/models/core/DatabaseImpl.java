package models.core;
import models.exception.DomainException;
import interfaces.Row;
import interfaces.Table;
import java.util.*;

public class DatabaseImpl implements interfaces.Database {
    private final Map<String, Table> tables;

    public DatabaseImpl() {
        this.tables = new LinkedHashMap<>();
    }

    @Override
    public Map<String,Table> getLoadedTables(){
        return this.tables;
    }

    @Override
    public String printLoadedTables() {
        StringBuilder sb = new StringBuilder();
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
}
