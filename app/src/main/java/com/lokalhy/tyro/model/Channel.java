package com.lokalhy.tyro.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;
import java.util.List;

@Root(name = "channel",strict = false)
public class Channel implements Serializable {

    @Element(name = "description")
    private String description;


    @Element(name = "title")
    private String title;

    @Element(name = "pubDate")
    private String pubDate;

    @ElementList(inline = true, name = "item")
     private List<Item> item;

    @Path("image")
    @Element(name = "url", required = false)
    private String url;

    public Channel() {
    }


    public Channel(String description, String title, String pubDate, List<Item> item, String url) {
        this.description = description;
        this.title = title;
        this.pubDate = pubDate;
        this.item = item;
        this.url = url;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }



    public List<Item> getItems() {
        return item;
    }

    public void setItems(List<Item> items) {
        this.item = items;
    }
}
