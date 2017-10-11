package com.a1694158.harshkumar.github;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Harsh on 9/30/2017.
 */

public class Users implements Parcelable {
    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
    private String name;
    private String path;


    public Users(String name, String path) {
        this.name = name;
        this.path = path;
    }

    protected Users(Parcel in) {
        name = in.readString();
        path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
