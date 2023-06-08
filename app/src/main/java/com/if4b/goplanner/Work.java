package com.if4b.goplanner;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Work implements Parcelable {
    private String id;
    private String content;
    private String user_id;
    private String created_date;
    private String modified_date;
    private String username;

    protected Work(Parcel in) {
        id = in.readString();
        content = in.readString();
        user_id = in.readString();
        created_date = in.readString();
        modified_date = in.readString();
        username = in.readString();
    }

    public static final Creator<Work> CREATOR = new Creator<Work>() {
        @Override
        public Work createFromParcel(Parcel in) {
            return new Work(in);
        }

        @Override
        public Work[] newArray(int size) {
            return new Work[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreate_date() {
        return created_date;
    }

    public void setCreate_date(String create_date) {
        this.created_date = create_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(user_id);
        dest.writeString(created_date);
        dest.writeString(modified_date);
        dest.writeString(username);
    }
}
