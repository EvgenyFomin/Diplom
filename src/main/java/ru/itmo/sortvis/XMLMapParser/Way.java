package ru.itmo.sortvis.XMLMapParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Way {
    boolean visible;
    int version;
    String user, timestamp;
    long id, uid, changeset;
    ArrayList<Tag> tagList = new ArrayList<Tag>();
    ArrayList<NodesInTheWay> ndList = new ArrayList<>();

    @XmlElement(name = "tag")
    public ArrayList<Tag> getTagList() {
        return tagList;
    }

    @XmlElement(name = "nd")
    public ArrayList<NodesInTheWay> getNdList() {
        return ndList;
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
}
