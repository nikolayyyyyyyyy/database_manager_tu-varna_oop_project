package interfaces;
import java.io.IOException;

public interface FileManage {

    Table openTable(String fileName) throws IOException;
    void saveTable(Table tableImpl) throws IOException;
    boolean isFileExitsInBase(String fileName);
}
