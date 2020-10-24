package com.lokalhy.tyro.model;


import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(strict = false)
@Default(DefaultType.PROPERTY)
public class ImageObject implements Serializable {


    public String url;

    public ImageObject(String url) {
        this.url = url;
    }

    public ImageObject() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
