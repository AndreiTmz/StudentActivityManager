package com.example.proiectdam.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "grades",
foreignKeys = @ForeignKey(entity = User.class,
parentColumns = "userId",
childColumns = "uid",
onDelete = CASCADE))
public class Grade {
    @PrimaryKey(autoGenerate = true)
    private int id_nota;
    private int nota;
    private String materie;
    private String tip; // partial / proiect / examen final
    private int uid;

    public Grade( int nota, String materie, String tip, int uid) {
        this.nota = nota;
        this.materie = materie;
        this.tip = tip;
        this.uid = uid;
    }

    public int getId_nota() {
        return id_nota;
    }

    public void setId_nota(int id_nota) {
        this.id_nota = id_nota;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
