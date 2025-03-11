import models.Database;
import models.Table;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        Database database = new Database();
        Scanner scanner = new Scanner(System.in);

        if(Files.notExists(database.getBase())){

            Files.createDirectory(database.getBase());
        }

        while(true){
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] arguments = command.split(" ");

            String tableName;
            int columnIndex;
            Table table;

            switch (arguments[0]){
                case "import":
                    database.openTable(arguments[1]);
                    break;

                case "close":
                    database.closeTable(arguments[1]);
                    break;

                case "showtables":
                    System.out.print(database.printTables());
                    break;

                case "export":
                    database.saveTable(arguments[1]);
                    break;

                case "exportAs":
                    break;

                case "exit":
                    System.out.println("Exiting the program...");
                    System.exit(0);
                    break;

                case "select":
                    columnIndex = Integer.parseInt(arguments[1]);
                    String searchedValue = arguments[2];
                    tableName = arguments[3];

                    table = database.getTables().get(tableName);
                    System.out.print(table.selectAllRowsContain(columnIndex,searchedValue));
                    break;

                case "print":
                    tableName = arguments[1];
                    table = database.getTables().get(tableName);
                    System.out.println(table.printRows());
                    break;


            }
        }
    }
}
