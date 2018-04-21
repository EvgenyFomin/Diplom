package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlAttribute;

public class NodesInTheWay {
    int ref;

    public int getRef() {
        return ref;
    }

    @XmlAttribute
    public void setRef(int ref) {
        this.ref = ref;
    }
}
