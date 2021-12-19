package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

    private int id;
    private String text;
    private String answer;
    private Integer points;
    private String firstHelp;
    private String secondHelp;
    private String qrCode;
    private String locationHint;
    private String locationName;
    private String locationLon;
    private String locationLat;
    private String city;
    private boolean usedFirstHelp;
    private boolean usedScndHelp;

    public Question() {
    }

    public Question(int id, String text, String answer,Integer points, String firstHelp, String secondHelp,
                    String qrCode, String locationHint, String locationName, String locationLon,  String locationLat, String city, boolean usedFirstHelp, boolean usedScndHelp ) {
        this.id=id;
        this.text = text;
        this.answer = answer;
        this.points=points;
        this.firstHelp = firstHelp;
        this.secondHelp = secondHelp;
        this.qrCode = qrCode;
        this.locationHint = locationHint;
        this.locationName = locationName;
        this.locationLon=locationLon;
        this.locationLat = locationLat;
        this.city = city;
        this.usedFirstHelp=usedFirstHelp;
        this.usedScndHelp=usedScndHelp;
    }

    public Question(int id, String text, String answer, Integer points, String firstHelp, String secondHelp, String qrCode, String locationHint, String locationName, String locationLon, String locationLat, String city) {
        this.id = id;
        this.text = text;
        this.answer = answer;
        this.points = points;
        this.firstHelp = firstHelp;
        this.secondHelp = secondHelp;
        this.qrCode = qrCode;
        this.locationHint = locationHint;
        this.locationName = locationName;
        this.locationLon = locationLon;
        this.locationLat = locationLat;
        this.city = city;
    }

    protected Question(Parcel in) {
        id=in.readInt();
        text = in.readString();
        answer = in.readString();
        points=in.readInt();
        firstHelp = in.readString();
        secondHelp = in.readString();
        qrCode = in.readString();
        locationHint = in.readString();
        locationName = in.readString();
        locationLon = in.readString();
        locationLat = in.readString();
        city = in.readString();
        usedFirstHelp=in.readInt() == 1;
        usedScndHelp=in.readInt() == 1;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeString(answer);
        dest.writeInt(points);
        dest.writeString(firstHelp);
        dest.writeString(secondHelp);
        dest.writeString(qrCode);
        dest.writeString(locationHint);
        dest.writeString(locationName);
        dest.writeString(locationLon);
        dest.writeString(locationLat);
        dest.writeString(city);
        dest.writeInt(usedFirstHelp ? 1 : 0);
        dest.writeInt(usedScndHelp ? 1: 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFirstHelp() {
        return firstHelp;
    }

    public void setFirstHelp(String firstHelp) {
        this.firstHelp = firstHelp;
    }

    public String getSecondHelp() {
        return secondHelp;
    }

    public void setSecondHelp(String secondHelp) {
        this.secondHelp = secondHelp;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getLocationHint() {
        return locationHint;
    }

    public void setLocationHint(String locationHint) {
        this.locationHint = locationHint;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(String locationLon) {
        this.locationLon = locationLon;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUsedFirstHelp() {
        return usedFirstHelp;
    }

    public void setUsedFirstHelp(boolean usedFirstHelp) {
        this.usedFirstHelp = usedFirstHelp;
    }

    public boolean isUsedScndHelp() {
        return usedScndHelp;
    }

    public void setUsedScndHelp(boolean usedScndHelp) {
        this.usedScndHelp = usedScndHelp;
    }

    public String getHelp(int hintNum){
        switch (hintNum){
            case 1:
                return firstHelp;
            case 2:
                return secondHelp;
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answer='" + answer + '\'' +
                ", points=" + points +
                ", firstHelp='" + firstHelp + '\'' +
                ", secondHelp='" + secondHelp + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", locationHint='" + locationHint + '\'' +
                ", locationName='" + locationName + '\'' +
                ", locationLon='" + locationLon + '\'' +
                ", locationLat='" + locationLat + '\'' +
                ", city='" + city + '\'' +
                ", usedFirstHelp=" + usedFirstHelp +
                ", usedScndHelp=" + usedScndHelp +
                '}';
    }
}
