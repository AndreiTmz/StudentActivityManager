package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;

public class DeleteGradeAsync extends AsyncTask<Grade,Void,Void> {


    private Context context;

    public DeleteGradeAsync(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Grade... grades) {
        Database db = Database.getInstance(context);
        if(grades.length > 0)
            db.getDatabase().gradeDAO().deleteGrade(grades[0]);
        return null;
    }
}
