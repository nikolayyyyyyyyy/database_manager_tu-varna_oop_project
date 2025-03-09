package models.commands;
import interfaces.Command;
import models.Database;
import models.Table;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OpenCommand implements Command {
    private final Database database;
    private final String fileName;

    public OpenCommand(Database database, String fileName) {
        this.database = database;
        this.fileName = fileName;
    }

    @Override
    public void execute() throws IOException, JAXBException {
        Path basePath = Path.of(database.getBase());
        Path filePath = basePath.resolve(fileName);

        if (Files.notExists(basePath)) {

            Files.createDirectories(basePath);
        }

        if (Files.notExists(filePath)) {

            Files.createFile(filePath);
            return;
        }

        JAXBContext context = JAXBContext.newInstance(Table.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Table table = (Table) unmarshaller.unmarshal(filePath.toFile());
        database.getOpenTables().put(table.getName(), table);

        System.out.println("Successfully loaded: " + table.getName());
    }
}
