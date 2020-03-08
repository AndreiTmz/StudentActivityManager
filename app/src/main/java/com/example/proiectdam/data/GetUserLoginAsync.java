package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;

public class GetUserLoginAsync extends AsyncTask<User,Void,User> {

    private Context context;

    public GetUserLoginAsync(Context context){
        this.context = context;
    }

    @Override
    protected User doInBackground(User... users) {
        String email = users[0].getEmail();
        String password = users[0].getParola();
        return Database.getInstance(context).getDatabase().userDAO().getUser(email,password);
    }
}
