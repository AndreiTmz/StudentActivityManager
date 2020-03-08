package com.example.proiectdam.data;

public class StudyObject {

    private String name;
    private int credits;
    private String type;
    private String abbreviation;

    public StudyObject() {

    }

    public StudyObject(String name, int credits, String type,String abbreviation) {
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
