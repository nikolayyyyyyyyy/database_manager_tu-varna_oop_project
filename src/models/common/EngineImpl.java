package models.common;
import interfaces.DatabaseManager;
import interfaces.Engine;
import models.core.Database;
import models.core.Table;
import java.util.Scanner;

public class EngineImpl implements Engine {
    private final DatabaseManager database;

    public EngineImpl() {
        database = new Database();
    }

    @Override
    public void run()  {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("> ");
            String command = scanner.nextLine();

            String[] arguments = command.split(" ");



        }
    }
}
