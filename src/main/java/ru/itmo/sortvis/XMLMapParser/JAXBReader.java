package ru.itmo.sortvis.XMLMapParser;

import ru.itmo.sortvis.GraphModel;
import ru.itmo.sortvis.MatrixGraph;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JAXBReader {
    int countOfNodes;
    int countOfEdges;
    boolean isOrientedGraph = false;
    int[][] matrix;
    List<Node> nodeList;
    List<Way> wayList;

    public GraphModel parse(String path) throws JAXBException {
        File file = new File(path);
        JAXBContext context = JAXBContext.newInstance(XMLParser.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLParser xmlParser = (XMLParser) unmarshaller.unmarshal(file);

//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(xmlParser, System.out);
//        marshaller.marshal(xmlParser, new File("src/main/resources/newMap.xml"));

        nodeList = xmlParser.getNodeList();
        wayList = xmlParser.getWayList();

        matrix = new int[nodeList.size()][nodeList.size()];

        System.out.println(nodeList.size());

        return null;
    }

    private void printer() {
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
