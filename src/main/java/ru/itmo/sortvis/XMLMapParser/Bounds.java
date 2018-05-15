package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlAttribute;

public class Bounds {
    double minlat, minlon, maxlat, maxlon;

    public double getMinlat() {
        return minlat;
    }

    @XmlAttribute
    public void setMinlat(double minlat) {
        this.minlat = minlat;
    }

    public double getMinlon() {
        return minlon;
    }

    @XmlAttribute
    public void setMinlon(double minlon) {
        this.minlon = minlon;
    }

    public double getMaxlat() {
        return maxlat;
    }

    @XmlAttribute
    public void setMaxlat(double maxlat) {
        this.maxlat = maxlat;
    }

    public double getMaxlon() {
        return maxlon;
    }

    @XmlAttribute
    public void setMaxlon(double maxlon) {
        this.maxlon = maxlon;
    }
}
