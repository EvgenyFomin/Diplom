package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlAttribute;

public class Tag {
    String k, v;

    public String getK() {
        return k;
    }

    @XmlAttribute
    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    @XmlAttribute
    public void setV(String v) {
        this.v = v;
    }
}
