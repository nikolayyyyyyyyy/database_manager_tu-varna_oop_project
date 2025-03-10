import models.Database;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws JAXBException, IOException {
        Database database = new Database();
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] arguments = command.split(" ");

            switch (arguments[0]){
                case "open":

                    database.openTable(arguments[1]);
                    break;
                case "close":

                    database.closeTable(arguments[1]);
                    break;
                case "showtables":

                    database.printTables();
                    break;
            }
        }
    }
}
