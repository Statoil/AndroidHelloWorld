package com.example.canteenapp.model;

import java.io.Serializable;

/**
 * Created by asbjorn on 9/16/13.
 */
public class CanteenItem implements Serializable {
    private String id;
    private String name;
    private String link;

    public CanteenItem() {
    }

    public CanteenItem(String id, String name, String link) {
        this.setId(id);
        this.setName(name);
        this.setLink(link);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
