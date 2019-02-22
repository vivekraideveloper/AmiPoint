package com.amityprojectvivekrai.amityproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class ChildList implements Parcelable {

    //    private String title;
    private HashMap<String, String> values;


    public ChildList(HashMap<String, String> values ) {
        this.values = values;
    }

    protected ChildList(Parcel in) {
        values = in.readHashMap(HashMap.class.getClassLoader());
    }

    public static final Creator<ChildList> CREATOR = new Creator<ChildList>() {
        @Override
        public ChildList createFromParcel(Parcel in) {
            return new ChildList(in);
        }

        @Override
        public ChildList[] newArray(int size) {
            return new ChildList[size];
        }
    };
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String Title) {
//        this.title = Title;
//    }


    public HashMap<String, String> getValues() {
        return values;
    }

    public void setValues(HashMap<String, String> values) {
        this.values = values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeMap(values);
    }
}