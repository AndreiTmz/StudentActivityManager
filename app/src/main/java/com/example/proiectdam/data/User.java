package com.example.proiectdam.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "users")
public class User implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String email;
    private String parola;
    private String nume;
    private String prenume;
    private String telefon;
    @Ignore
    private List<StudyObject> studyObjects = new ArrayList<>();
    private String rol;  //prof/stud

    public User() {
    }

    public User(String email, String parola, String nume, String prenume, String rol) {
        this.email = email;
        this.parola = parola;
        this.nume = nume;
        this.prenume = prenume;
        this.rol = rol;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        parola = in.readString();
        email = in.readString();
        nume = in.readString();
        prenume = in.readString();
        rol = in.readString();
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<StudyObject> getStudyObjects() {
        return studyObjects;
    }

    public void setStudyObjects(List<StudyObject> studyObjects) {
        this.studyObjects = studyObjects;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(parola);
        parcel.writeString(email);
        parcel.writeString(nume);
        parcel.writeString(prenume);
        parcel.writeString(rol);
    }
}
