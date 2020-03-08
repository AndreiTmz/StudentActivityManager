package com.example.proiectdam.data;

public class Exam {

    private String id;
    private String classRoom;
    private String day;
    private String hour;
    private String studyObject;

    public Exam()
    {

    }

    public Exam(String id, String classRoom, String day, String hour, String studyObject) {
        this.id = id;
        this.classRoom = classRoom;
        this.day = day;
        this.hour = hour;
        this.studyObject = studyObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getStudyObject() {
        return studyObject;
    }

    public void setStudyObject(String studyObject) {
        this.studyObject = studyObject;
    }
}
