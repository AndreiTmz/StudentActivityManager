package com.example.proiectdam.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Grade.class}, version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    public abstract  UserDAO userDAO();
    public abstract  GradeDAO gradeDAO();
}
