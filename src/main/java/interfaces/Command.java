package interfaces;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface Command {

    public void execute() throws IOException, JAXBException;
}
