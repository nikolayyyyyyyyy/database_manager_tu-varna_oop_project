package interfaces;
import models.core.TableImpl;
import java.io.IOException;
import java.nio.file.Path;

public interface FileManage {

    public TableImpl readFile(Path baseDirectory, String fileName) throws IOException;
    public void writeFile(Path baseDirectory, TableImpl tableImpl) throws IOException;
}
