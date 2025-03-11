package models.common;
import interfaces.DatabaseOperation;
import interfaces.Engine;
import models.core.ColumnType;
import models.core.Database;
import models.core.Table;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class EngineIml implements Engine {
    private final DatabaseOperation database;

    public EngineIml() {
        database = new Database();
    }

    @Override
    public void run()  {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] arguments = command.split(" ");
            String tableName;
            String searchedValue;
            int columnIndex;
            Table table;

            try {

                switch (arguments[0]) {
                    case "import":
                        database.openTable(arguments[1]);
                        break;

                    case "close":
                        database.closeTable(arguments[1]);
                        break;

                    case "showtables":
                        System.out.print(database.printTables());
                        break;

                    case "save":
                        database.saveTable(arguments[1]);
                        break;

                    case "saveas":
                        database.saveTableAs(arguments[1], arguments[2]);
                        break;

                    case "exit":
                        System.out.println("Exiting the program...");
                        System.exit(0);
                        break;

                    case "select":
                        columnIndex = Integer.parseInt(arguments[1]);
                        searchedValue = arguments[2];
                        tableName = arguments[3];

                        table = database.getTable(tableName);
                        System.out.print(table.selectAllRowsContain(columnIndex, searchedValue));
                        break;

                    case "print":
                        tableName = arguments[1];
                        table = database.getTable(tableName);
                        System.out.println(table.printRows());
                        break;

                    case "addcolumn":
                        tableName = arguments[1];
                        String columnToAdd = arguments[2];
                        ColumnType columnType = ColumnType.valueOf(arguments[3]);

                        table = database.getTable(tableName);
                        table.addColumn(columnToAdd, columnType);
                        break;

                    case "insert":
                        tableName = arguments[1];
                        String[] values = Arrays.stream(arguments)
                                .skip(2)
                                .toArray(String[]::new);

                        table = database.getTable(tableName);
                        table.addRow(values);
                        break;

                    case "rename":
                        tableName = arguments[1];
                        String newName = arguments[2];

                        table = database.getTable(tableName);
                        table.rename(newName);
                        break;

                    case "count":
                        tableName = arguments[1];
                        columnIndex = Integer.parseInt(arguments[2]);
                        searchedValue = arguments[3];

                        table = database.getTable(tableName);
                        System.out.print(table.getCountRowsContainAt(columnIndex,searchedValue));
                        break;

                    case "delete":
                        tableName = arguments[1];
                        columnIndex = Integer.parseInt(arguments[2]);
                        searchedValue = arguments[3];

                        table = database.getTable(tableName);
                        table.deleteTableWhereRowContainsAt(columnIndex,searchedValue);
                        break;

                    case "describe":
                        tableName = arguments[1];
                        table = database.getTable(tableName);

                        System.out.println(table.printColumnTypes());
                        break;
                }
            }catch (IOException e){

                ErrorLogger.log("Something occurs.");
            }
        }
    }
}
