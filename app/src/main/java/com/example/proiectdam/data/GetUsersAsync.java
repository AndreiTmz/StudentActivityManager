package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class GetUsersAsync extends AsyncTask<String, Void, List<User>>
{
    private Context context;

    public GetUsersAsync(Context context){
        this.context = context;
    }


//    @Override
//    protected List<User> doInBackground(Void... voids) {
//        return Database.getInstance(context).getDatabase().userDAO().getUsersByRole("stud");
//    }

    @Override
    protected List<User> doInBackground(String... strings) {
        if(strings.length > 0)
        {
            if(strings[0].equals("stud"))
            {
                return Database.getInstance(context).getDatabase().userDAO().getUsersByRole("stud");
            }
            else if(strings[0].equals("prof"))
            {
                return Database.getInstance(context).getDatabase().userDAO().getUsersByRole("prof");
            }
        }
        else
        {
            return Database.getInstance(context).getDatabase().userDAO().getAllUsers();
        }
        return null;
    }
}
