package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;

public class InsertUsersAsync extends AsyncTask<User,Void,Void> {

    private Context context;

    public InsertUsersAsync(Context context)
    {
        this.context = context;
    }


    @Override
    protected Void doInBackground(User... users) {

        Database database = Database.getInstance(context);
        for(int i=0; i < users.length; i++)
        {
            database.getDatabase().userDAO().insertUser(users[i]);
        }

        return null;
    }
}
