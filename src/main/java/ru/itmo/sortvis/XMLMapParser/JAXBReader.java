package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JAXBReader {
    public void parse() throws JAXBException {
        File file = new File("src/main/resources/map.osm");
        JAXBContext context = JAXBContext.newInstance(XMLParser.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLParser xmlParser = (XMLParser) unmarshaller.unmarshal(file);

//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(xmlParser, System.out);
//        marshaller.marshal(xmlParser, new File("src/main/resources/newMap.xml"));
    }

    private void printer(XMLParser xmlParser) {
        List<Node> nodeList = xmlParser.getNodeList();
        List<Way> wayList = xmlParser.getWayList();

        System.out.println("Nodes: ");

        for (Node obj : nodeList) {
            System.out.println(obj.getId());
        }

        System.out.println("Ways: ");

        for (Way obj : wayList) {
            System.out.println(obj.getId());
        }
    }
}
