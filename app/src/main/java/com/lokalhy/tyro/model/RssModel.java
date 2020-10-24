package com.lokalhy.tyro.model;

import com.lokalhy.tyro.model.Channel;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name="rss",strict = false)
public class RssModel {

    @Element
    public Channel channel;
}
