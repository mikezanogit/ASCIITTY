package com.zano.asciitty.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mamanzan on 5/21/2014.
 */
public class Item implements Parcelable {
    public int id;
    public String name;
    public String data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.data);
    }
}
