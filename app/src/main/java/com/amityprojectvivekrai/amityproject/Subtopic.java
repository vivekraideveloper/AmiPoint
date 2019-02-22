package com.amityprojectvivekrai.amityproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Subtopic implements Parcelable {
    public final String name;

    public Subtopic(String name) {
        this.name = name;
    }

    protected Subtopic(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Subtopic> CREATOR = new Creator<Subtopic>() {
        @Override
        public Subtopic createFromParcel(Parcel in) {
            return new Subtopic(in);
        }

        @Override
        public Subtopic[] newArray(int size) {
            return new Subtopic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
