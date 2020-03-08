package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;

public class InsertGradeAsync extends AsyncTask<Grade, Void, Void> {
    private Context context;

    public InsertGradeAsync(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Grade... grades) {
        Database db = Database.getInstance(context);
        for(int i=0 ;i<grades.length;i++)
        {
            db.getDatabase().gradeDAO().insertGrade(grades[i]);
        }
        return null;
    }
}
