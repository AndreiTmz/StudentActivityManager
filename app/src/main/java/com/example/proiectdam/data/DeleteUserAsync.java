package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;

public class DeleteUserAsync extends AsyncTask<User,Void,Void> {

    private Context context;

    public DeleteUserAsync(Context context)
    {
        this.context = context;
    }
    @Override
    protected Void doInBackground(User... users) {
        Database db = Database.getInstance(context);
        if(users.length > 0)
        {
            db.getDatabase().userDAO().deleteUser(users[0]);
        }
        return null;
    }
}
