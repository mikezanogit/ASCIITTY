package com.zano.asciitty.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mamanzan on 6/4/2014.
 */
public class AsciiArtItem implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.data);
    }
}
