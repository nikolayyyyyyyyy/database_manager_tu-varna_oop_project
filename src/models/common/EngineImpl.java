package models.common;
import interfaces.DatabaseManager;
import interfaces.Engine;
import models.core.ColumnType;
import models.core.Database;
import models.core.Table;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class EngineImpl implements Engine {
    private final DatabaseManager databaseManager;
    private final Scanner scanner;

    public EngineImpl() {
        databaseManager = new Database();
        scanner = new Scanner(System.in);
    }

    @Override
    public void run()  {

        while(true){
            System.out.print("> ");
            String commandInput = this.scanner.nextLine();
            String[] arguments = commandInput.split(" ");

            try {
                switch (arguments[0]) {
                    case "open":

                        openTableCommand(arguments[1]);
                        break;
                    case "close":

                        closeTable(arguments[1]);
                        break;
                    case "save":

                        saveTable(arguments[1]);
                        break;
                    case "saveas":

                        saveTableAs(arguments[1], arguments[2]);
                        break;
                    case "addcolumn":

                        addColumn(arguments[1], arguments[2], ColumnType.valueOf(arguments[3]));
                        break;
                    case "aggregate":

                        break;
                    case "count":

                        count(arguments[1], Integer.parseInt(arguments[2]), arguments[3]);
                        break;
                    case "delete":

                        delete(arguments[1], Integer.parseInt(arguments[2]), arguments[3]);
                        break;
                    case "describe":

                        describe(arguments[1]);
                        break;
                    case "help":

                        help();
                        break;
                    case "insert":

                        insert(arguments[1], Arrays.stream(arguments).skip(2).toArray(String[]::new));
                        break;
                    case "print":

                        print(arguments[1]);
                        break;
                    case "showtables":

                        showTables();
                        break;
                    case "rename":

                        rename(arguments[1], arguments[2]);
                        break;
                    case "select":

                        select(Integer.parseInt(arguments[1]), arguments[2], arguments[3]);
                        break;
                    case "update":

                        update(arguments[1],
                                Integer.parseInt(arguments[2]),
                                arguments[3],
                                Integer.parseInt(arguments[5]),
                                arguments[6]);
                        break;
                    case "innerjoin":

                        innerJoin(arguments[2], Integer.parseInt(arguments[1]), arguments[4], Integer.parseInt(arguments[1]));
                        break;
                    default:

                        System.out.println("Invalid command");
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                }
            }catch (IOException e){

            }
        }
    }

    private void innerJoin(String tableName, int firstTableIndex,String secondTableName, int secondTableIndex) {
    }

    private void update(String tableName, int columnIndex, String searchedValue, int targetColumnIndex, String targetValue) {
        Table table = this.databaseManager.getTable(tableName);

        System.out.println(table.updateRowValueAtIndexWhereContainsAt(columnIndex,
                targetColumnIndex,
                searchedValue,
                targetValue));

    }

    private void select(int columnIndex, String searchedValue, String tableName) {
        Table table = databaseManager.getTable(tableName);

        System.out.println(table.selectAllRowsContain(columnIndex,searchedValue));
    }

    private void rename(String tableName, String newTableName) {
        Table table = this.databaseManager.getTable(tableName);
        table.rename(newTableName);
    }

    private void showTables() {
       System.out.println(this.databaseManager.printTables());
    }

    private void print(String tableName) {
        this.databaseManager
                .getTable(tableName)
                .printRows();
    }

    private void insert(String tableName, String[] values) {
        Table table = this.databaseManager.getTable(tableName);

        table.addRow(values);
    }

    private void help() {
        System.out.println(this.databaseManager.printHelp());

    }

    private void describe(String tableName) {
        Table table = this.databaseManager.getTable(tableName);

        System.out.println(table.printColumnTypes());
    }

    private void delete(String tableName, int columnIndex, String searchedValue) {

        Table table = this.databaseManager.getTable(tableName);
        System.out.println(table.deleteTableWhereRowContainsAt(columnIndex,searchedValue));
    }

    private void count(String tableName, int columnIndex, String searchedValue) {
        Table table = this.databaseManager.getTable(tableName);

        System.out.println(table.getCountRowsContainAt(columnIndex,searchedValue));
    }

    private void addColumn(String tableName,String columnName,ColumnType columnType) {
        Table table = this.databaseManager.getTable(tableName);

        table.addColumn(columnType,columnName);
        System.out.println("Added column -> ".concat(columnName));
    }

    private void saveTableAs(String tableName, String newTableName) throws IOException {

        this.databaseManager.saveTableAs(tableName,newTableName);
    }

    private void saveTable(String tableName) throws IOException {
        this.databaseManager.saveTable(tableName);
    }

    private void closeTable(String tableName) {
        this.databaseManager.closeTable(tableName);
    }

    private void openTableCommand(String tableName) throws IOException {
         this.databaseManager.openTable(tableName);

         System.out.println("Opened table -> ".concat(tableName));
    }
}