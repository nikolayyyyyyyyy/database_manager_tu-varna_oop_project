package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorLogger {
    private static final String ERROR_PATH
            = "error.txt";

    public static void log(String log) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ERROR_PATH,true))){

            writer.write(log);
            writer.newLine();
        }catch (IOException exception){

            System.err.println("Error happened.");
        }
    }
}
