package models.core;
import models.exception.DomainException;
import interfaces.Column;
import interfaces.FileManage;
import interfaces.Row;
import interfaces.Table;
import models.common.TextFileManager;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class DatabaseImpl implements interfaces.Database {
    private final Map<String, Table> tables;
    private final Map<String,String> help;
    private final FileManage fileManage;
    private final Path base;

    public DatabaseImpl() {
        this.tables = new LinkedHashMap<>();
        this.fileManage = new TextFileManager();
        this.base = Path.of("catalog");

        this.help = Map.of("open <file>", "opens the given file",
                "close <file>", "closes the given file",
                "save <file>", "saves the given file",
                "saveas <file>", "saves file in new file",
                "help","prints this information",
                "exit","exists the program");
    }

    @Override
    public String printHelp(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("The following command are supported:").append("\n");

        for (String command:
             this.help.keySet()) {

            stringBuilder
                    .append(command)
                    .append("      ")
                    .append(this.help.get(command))
                    .append("\n");
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public Table getTable(String name) {
        if(!this.tables.containsKey(name)) {

            throw new DomainException(String.format("Table with %s is not loaded ", name));
        }

        return this.tables.get(name);
    }

    @Override
    public void openTable(String fileName) throws IOException {
        Table tableImpl = fileManage
                .readFile(this.base, fileName);

        if (this.tables.containsKey(tableImpl.getName())) {

            throw new DomainException(String.format("Table %s is already opened. ", fileName.replace(".txt","")));
        }

        this.tables.put(tableImpl.getName().replace(".txt",""), tableImpl);
    }

    @Override
    public String printTables() {
        StringBuilder sb = new StringBuilder();
        sb.append("Opened tables:").append("\n");

        for (String table :
                this.tables.keySet()) {

            sb.append(table).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public void closeTable(String fileName) {
        if(!this.tables.containsKey(fileName)){

            throw new DomainException(String.format("Table %s is not loaded.",fileName.replace(".txt","")));
        } else {

            this.tables.remove(fileName);
        }
    }

    @Override
    public void saveTable(String tableName) throws IOException {
        this.fileManage.writeFile(this.base,
                this.tables.get(tableName));

        this.closeTable(tableName);
    }

    @Override
    public void saveTableAs(String oldFileName, String newFileName) throws IOException {
        if(this.base.resolve(newFileName).toFile().exists()){

            throw new DomainException("Name is already existing.");
        }
        Table tableImpl = this.tables.get(oldFileName);
        tableImpl.rename(newFileName);

        this.fileManage.writeFile(this.base, tableImpl);
        this.closeTable(oldFileName);
    }

    @Override
    public String innerJoinTables(String firstTable, int firstColIndex,
                                  String secondTable, int secondColIndex) {
        Table first = this.tables.get(firstTable);
        Table second = this.tables.get(secondTable);

        List<Column> columns = new ArrayList<>();
        columns.addAll(first.getColumns());
        columns.addAll(second.getColumns());

        Table joinedTable = new TableImpl(firstTable + "_" + secondTable);
        for (Column column :
                columns) {
            joinedTable.addColumn(column);
        }

        for (Row firstTableRow :
                first.getRows()) {

            for (Row secondTableRow :
                    second.getRows()) {

                if(firstTableRow
                        .getAttributes().get(first.getColumns().get(firstColIndex))
                        .equals(secondTableRow.getAttributes().get(second.getColumns().get(secondColIndex)))){

                    String row = firstTableRow + " " + secondTableRow;
                    joinedTable.addRow(row.split(" "));
                }
            }
        }

        this.tables.put(joinedTable.getName(),joinedTable);

        return "Joined table: " + joinedTable.getName();
    }
}
