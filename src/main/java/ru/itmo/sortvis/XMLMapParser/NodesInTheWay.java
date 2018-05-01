package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlAttribute;

public class NodesInTheWay {
    long ref;

    public long getRef() {
        return ref;
    }

    @XmlAttribute
    public void setRef(long ref) {
        this.ref = ref;
    }
}
