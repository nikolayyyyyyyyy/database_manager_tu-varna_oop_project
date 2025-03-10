package models;

import javax.xml.bind.*;
import java.io.File;

public class XmlFileManager {

    public static Table readFile(File file) throws JAXBException {

        JAXBContext context = JAXBContext
                .newInstance(Table.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Table)unmarshaller.unmarshal(file);
    }

    public static void writeFile(Table table, File file) throws JAXBException {

        JAXBContext context = JAXBContext
                .newInstance(Table.class);

        Marshaller marshaller = context
                .createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(table, file);
    }
}
