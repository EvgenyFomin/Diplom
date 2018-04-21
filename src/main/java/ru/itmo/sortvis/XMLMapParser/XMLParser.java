package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "osm")
public class XMLParser {
    List<Node> nodeList = new LinkedList<Node>();
    List<Way> wayList = new LinkedList<Way>();

    @XmlElement(name = "node")
    public List<Node> getNodeList() {
        return nodeList;
    }

    @XmlElement(name = "way")
    public List<Way> getWayList() {
        return wayList;
    }
}
