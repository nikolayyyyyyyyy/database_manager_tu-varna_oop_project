package interfaces;
import models.core.Table;
import java.io.IOException;
import java.nio.file.Path;

public interface FileManage {

    public Table readFile(Path baseDirectory, String fileName) throws IOException;
    public void writeFile(Path baseDirectory,Table table) throws IOException;
}
