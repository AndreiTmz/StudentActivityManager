package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;

public class UpdateUserAsync extends AsyncTask<User,Void,Void> {

    private Context context;

    public UpdateUserAsync(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(User... users) {
        Database db = Database.getInstance(context);
        db.getDatabase().userDAO().updateUser(users[0]);
        return null;
    }
}
