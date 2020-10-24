package com.lokalhy.tyro.model;


import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;

@Root(name = "item",strict = false)
public class Item implements Serializable {

    @Path("title")
    @Text(required = false)
    private String title="";

    @Path("pubDate")
    @Text(required = false)
    private String pubDate="";

    @Path("link")
    @Text(required = false)
    private String link="";

    @Path("description")
    @Text(required = false)
    private String description="";


    public Item(String title, String pubDate, String link, String description) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.description = description;
    }


    public Item() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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


    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
