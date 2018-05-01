package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "osm")
public class XMLParser {
    ArrayList<Node> nodeList = new ArrayList<Node>();
    ArrayList<Way> wayList = new ArrayList<Way>();

    @XmlElement(name = "node")
    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    @XmlElement(name = "way")
    public ArrayList<Way> getWayList() {
        return wayList;
    }
}
