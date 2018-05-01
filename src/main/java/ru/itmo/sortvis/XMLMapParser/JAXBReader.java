package ru.itmo.sortvis.XMLMapParser;

import ru.itmo.sortvis.AdjListGraph;
import ru.itmo.sortvis.GraphModel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;

public class JAXBReader {
    int countOfNodes;
    int countOfEdges;
    boolean isOrientedGraph = false;
    private HashMap<Long, Set<Long>> adjList;
    private HashMap<String, Integer> weight;
    private List<Node> nodeList;
    private List<Way> wayList;

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

        countOfNodes = nodeList.size();
        adjList = new HashMap<>();
        for (Way currentWay : wayList) {
            ArrayList<NodesInTheWay> currentArrayList;
            currentArrayList = currentWay.getNdList();
            int sizeOfNodesList = currentArrayList.size();
            for (int i = 0; i < sizeOfNodesList - 1; i++) {
                if (adjList.containsKey(currentArrayList.get(i).getRef())) {
                    adjList.get(currentArrayList.get(i).getRef()).add(currentArrayList.get(i + 1).getRef());
                } else {
                    adjList.put(currentArrayList.get(i).getRef(), new HashSet<>());
                    adjList.get(currentArrayList.get(i).getRef()).add(currentArrayList.get(i + 1).getRef());
                }

                if (adjList.containsKey(currentArrayList.get(i + 1).getRef())) {
                    adjList.get(currentArrayList.get(i + 1).getRef()).add(currentArrayList.get(i).getRef());
                } else {
                    adjList.put(currentArrayList.get(i + 1).getRef(), new HashSet<>());
                    adjList.get(currentArrayList.get(i + 1).getRef()).add(currentArrayList.get(i).getRef());
                }
            }
        }

        countOfEdges = 0;

        for (Node currentNode : nodeList) {
            try {
                countOfEdges += adjList.get(currentNode.getId()).size();
                for (long id : adjList.get(currentNode.getId())) {
                    weight.put(String.valueOf(currentNode.getId()) + "-" + String.valueOf(id), 1);
                }
            } catch (NullPointerException e) {
//                System.out.println(currentNode.getId()); // вершины, которые не используются в путях
            }
        }

        System.out.println(countOfNodes + " " + countOfEdges);
        return new AdjListGraph(countOfNodes, countOfEdges, false, adjList, weight);
    }

    private void printer() {
        System.out.println("Nodes: ");

        for (Node obj : nodeList) {
            System.out.println(obj.getId());
        }

        System.out.println("Ways: ");

        for (Way obj : wayList) {
            for (NodesInTheWay nodesInTheWay : obj.getNdList()) {
                System.out.println(nodesInTheWay.getRef());
            }
//            System.out.println(obj.getId());
        }
    }
}
