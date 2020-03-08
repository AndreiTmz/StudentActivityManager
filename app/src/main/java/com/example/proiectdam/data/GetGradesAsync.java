package com.example.proiectdam.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class GetGradesAsync extends AsyncTask<String,Void, List<Grade>> {

    private Context context;

    public GetGradesAsync(Context context)
    {
        this.context = context;
    }

    @Override
    protected List<Grade> doInBackground(String... strings) {
        Database db = Database.getInstance(context);
        List<Grade> grades = null;
        if(strings.length == 2)
        {
            int id = Integer.parseInt(strings[0]);
            String type = strings[1];
            grades = db.getDatabase().gradeDAO().getStudentGradeByType(id,type);
        }
        else if(strings.length == 1)
        {
            int id = Integer.parseInt(strings[0]);
            grades = db.getDatabase().gradeDAO().getStudentGrade(id);
        }
        else if(strings.length == 0)
        {
            grades = db.getDatabase().gradeDAO().getAllGrades();
        }

        return  grades;
    }
}
