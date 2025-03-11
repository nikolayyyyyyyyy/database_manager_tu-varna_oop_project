package models;

public class FileManager {

    public static Table readFile(String fileName){

        return new Table(fileName);
    }
}
