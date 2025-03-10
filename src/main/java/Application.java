import models.Database;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws JAXBException, IOException {
        Database database = new Database();
        Scanner scanner = new Scanner(System.in);

        if(Files.notExists(database.getBase())){

            Files.createDirectory(database.getBase());
        }

        while(true){
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] arguments = command.split(" ");

            switch (arguments[0]){
                case "import":
                    database.importTable(arguments[1]);
                    break;
                case "close":
                    database.closeTable(arguments[1]);
                    break;
                case "showtables":
                    System.out.print(database.printTables());
                    break;
                case "export":
                    database.exportTable(arguments[1]);
                    break;
                case "exportAs":
                    break;
                case "exit":
                    System.exit(0);
                    break;

            }
        }
    }
}
