package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String email;
    private String userName;
    private int points;
    private int userId;
    private boolean isRangListAllowed;
    private int currentPathId;
    private int currentQuestionId;
    private String language="srp";

    public User(){}

    public User(String email, String userName, int points, int userId, boolean isRangListAllowed, int currentPathId, int currentQuestionId) {
        this.email = email;
        this.userName = userName;
        this.points = points;
        this.userId = userId;
        this.isRangListAllowed = isRangListAllowed;
        this.currentPathId = currentPathId;
        this.currentQuestionId = currentQuestionId;
    }

    public User(String userName, int points){
        //ostali podaci nisu vazni za rangiranje
        this.email=null;
        this.userName=userName;
        this.points = points;
        this.userId = -1;
        this.isRangListAllowed = false;
        this.currentPathId = -1;
        this.currentQuestionId = -1;
    }

    protected User(Parcel in) {
        email = in.readString();
        userName = in.readString();
        points = in.readInt();
        userId = in.readInt();
        isRangListAllowed = in.readByte() != 0;
        currentPathId = in.readInt();
        currentQuestionId = in.readInt();
        language=in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String email, String userName, int points, int newUserId, boolean isRangListAllowed) {
        this.email = email;
        this.userName = userName;
        this.points = points;
        this.userId = newUserId;
        this.isRangListAllowed = isRangListAllowed;
        this.currentPathId = 1;
        this.currentQuestionId = -1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isRangListAllowed() {
        return isRangListAllowed;
    }

    public void setRangListAllowed(boolean rangListAllowed) {
        isRangListAllowed = rangListAllowed;
    }

    public int getCurrentPathId() {
        return currentPathId;
    }

    public void setCurrentPathId(int currentPathId) {
        this.currentPathId = currentPathId;
    }

    public int getCurrentQuestionId() {
        return currentQuestionId;
    }

    public void setCurrentQuestionId(int currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeInt(points);
        dest.writeInt(userId);
        dest.writeByte((byte) (isRangListAllowed ? 1 : 0));
        dest.writeInt(currentPathId);
        dest.writeInt(currentQuestionId);
        dest.writeString(language);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", points=" + points +
                ", userId=" + userId +
                ", isRangListAllowed=" + isRangListAllowed +
                ", currentPathId=" + currentPathId +
                ", currentQuestionId=" + currentQuestionId +
                ", language='" + language + '\'' +
                '}';
    }
}
