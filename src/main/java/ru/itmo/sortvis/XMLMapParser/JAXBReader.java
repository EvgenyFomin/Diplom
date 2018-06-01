package ru.itmo.sortvis.XMLMapParser;

import org.apache.commons.lang3.tuple.Pair;
import ru.itmo.sortvis.AdjListGraph;
import ru.itmo.sortvis.GraphModel;
import ru.itmo.sortvis.Launcher;

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
    private HashMap<Pair<Long, Long>, Integer> weight;
    Map<Long, Node> idToNode = new HashMap<>();

    private ArrayList<Node> nodeList;
    private ArrayList<Way> wayList;
    private ArrayList<Bounds> bounds;
    private double minlat, minlon, maxlat, maxlon;
    private boolean filterOut = true;

    public GraphModel parse(String path) throws JAXBException {
        File file = new File(path);
        JAXBContext context = JAXBContext.newInstance(XMLParser.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLParser xmlParser = (XMLParser) unmarshaller.unmarshal(file);

//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(xmlParser, System.out);
//        marshaller.marshal(xmlParser, new File("src/main/resources/newMap.xml"));

        bounds = xmlParser.getBounds();
        minlat = bounds.get(0).getMinlat();
        maxlat = bounds.get(0).getMaxlat();
        minlon = bounds.get(0).getMinlon();
        maxlon = bounds.get(0).getMaxlon();

        System.out.println(minlat + " " + maxlat + " " + minlon + " " + maxlon);

        nodeList = xmlParser.getNodeList();
        wayList = xmlParser.getWayList();

        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getLat() < minlat ||
                    nodeList.get(i).getLat() > maxlat ||
                    nodeList.get(i).getLon() < minlon ||
                    nodeList.get(i).getLon() > maxlon) {
                nodeList.remove(i);
                i--;
            }
        }

        for (Node currentNode : nodeList) {
            long currentNodeId = currentNode.getId();
            idToNode.put(currentNodeId, currentNode);
        }

        countOfNodes = nodeList.size();
        adjList = new HashMap<>();
        for (Way currentWay : wayList) {

            if (filterOut) {
                for (Tag currentTag : currentWay.getTagList()) {
                    if (currentTag.getV().equals("footway") || currentTag.getV().equals("sidewalk") ||
                            currentTag.getV().equals("park")) {
                        if (Launcher.enableDebugOutput)
                            System.out.println("Warning. Way " + currentWay.getId() + " is under filter");
                        break;
                    }
                }
            }

            ArrayList<NodesInTheWay> currentNodeList;
            currentNodeList = currentWay.getNdList();
            int sizeOfNodesList = currentNodeList.size();
            for (int i = 0; i < sizeOfNodesList - 1; i++) {

                if (idToNode.containsKey(currentNodeList.get(i).getRef()) &&
                        idToNode.containsKey(currentNodeList.get(i + 1).getRef())) {
                    if (adjList.containsKey(currentNodeList.get(i).getRef())) {
                        adjList.get(currentNodeList.get(i).getRef()).add(currentNodeList.get(i + 1).getRef());
                    } else {
                        adjList.put(currentNodeList.get(i).getRef(), new HashSet<>());
                        adjList.get(currentNodeList.get(i).getRef()).add(currentNodeList.get(i + 1).getRef());
                    }

                    if (adjList.containsKey(currentNodeList.get(i + 1).getRef())) {
                        adjList.get(currentNodeList.get(i + 1).getRef()).add(currentNodeList.get(i).getRef());
                    } else {
                        adjList.put(currentNodeList.get(i + 1).getRef(), new HashSet<>());
                        adjList.get(currentNodeList.get(i + 1).getRef()).add(currentNodeList.get(i).getRef());
                    }
                }
            }
        }

        countOfEdges = 0;

        weight = new HashMap<>();

        for (Node currentNode : nodeList) {
            long currentNodeId = currentNode.getId();

            if (adjList.get(currentNodeId) == null) {
                // Не используется в маршрутах way (памятник, например)
                continue;
            }

            countOfEdges += adjList.get(currentNodeId).size();

            int x0 = (int) (idToNode.get(currentNodeId).getLon() * 1000000);
            int y0 = (int) (idToNode.get(currentNodeId).getLat() * 1000000);
            for (long id : adjList.get(currentNodeId)) {
                int x1 = (int) (idToNode.get(id).getLon() * 1000000);
                int y1 = (int) (idToNode.get(id).getLat() * 1000000);
                weight.put(Pair.of(currentNodeId, id), (int) (Math.sqrt(Math.pow((x1 - x0)/100000.0, 2) + Math.pow((y1 - y0)/100000.0, 2)) * 100000));
            }
        }

        if (Launcher.enableDebugOutput)
            System.out.println(countOfNodes + " " + countOfEdges);

//        System.exit(0);
        return new AdjListGraph(/*countOfNodes, countOfEdges,*/ false, idToNode, adjList, weight);
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
