package com.example.proiectdam.data;

import android.content.Context;

import androidx.room.Room;

public class Database {
    private static Database instance;
    private RoomDB database;

    private Database(Context context)
    {
        database = Room.databaseBuilder(context,RoomDB.class,"study_db").build();
    }

    public static Database getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new Database(context);
        }
        return instance;
    }

    public RoomDB getDatabase() { return database; }
}
