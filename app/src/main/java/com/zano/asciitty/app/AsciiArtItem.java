package com.zano.asciitty.app;

/**
 * Created by mamanzan on 6/4/2014.
 */
public class AsciiArtItem {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private long id;
    private String name;
    private String data;
}
