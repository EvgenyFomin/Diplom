package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Node {
    boolean visible;
    int version;
    String user, timestamp;
    double lat, lon;
    long id, uid, changeset;
    ArrayList<Tag> tagList = new ArrayList<Tag>();

    @XmlElement(name = "tag")
    public ArrayList<Tag> getTagList() {
        return tagList;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @XmlAttribute
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    @XmlAttribute
    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getChangeset() {
        return changeset;
    }

    @XmlAttribute
    public void setChangeset(long changeset) {
        this.changeset = changeset;
    }

    public boolean isVisible() {
        return visible;
    }

    @XmlAttribute
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getVersion() {
        return version;
    }

    @XmlAttribute
    public void setVersion(int version) {
        this.version = version;
    }

    public String getUser() {
        return user;
    }

    @XmlAttribute
    public void setUser(String user) {
        this.user = user;
    }

    public double getLat() {
        return lat;
    }

    @XmlAttribute
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    @XmlAttribute
    public void setLon(double lon) {
        this.lon = lon;
    }
}
