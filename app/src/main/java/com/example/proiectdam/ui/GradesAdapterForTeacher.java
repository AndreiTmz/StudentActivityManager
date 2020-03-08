package com.example.proiectdam.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiectdam.MainActivity;
import com.example.proiectdam.R;
import com.example.proiectdam.data.DeleteGradeAsync;
import com.example.proiectdam.data.DeleteUserAsync;
import com.example.proiectdam.data.Grade;
import com.example.proiectdam.data.User;

import java.util.List;

public class GradesAdapterForTeacher extends BaseAdapter {

    private List<Grade> grades;
    private LayoutInflater inflater;
    private Context context;

    public GradesAdapterForTeacher(List<Grade> grades, Context context) {
        this.grades = grades;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = inflater.inflate(R.layout.item_grade_for_teacher,parent,false);
        TextView tvMaterie, tvNota, tvTip;
        Button btnDeleteGrade;

        tvMaterie = rowItem.findViewById(R.id.tvMaterie);
        tvNota = rowItem.findViewById(R.id.tvNota);
        tvTip = rowItem.findViewById(R.id.tvTip);
        btnDeleteGrade = rowItem.findViewById(R.id.btnDeleteGrade);
        final Grade currentGrade = grades.get(position);

        tvMaterie.setText(currentGrade.getMaterie());
        tvNota.setText(String.valueOf(currentGrade.getNota()));
        tvTip.setText(currentGrade.getTip());
        btnDeleteGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteGradeAsync(context)
                {
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(context, "Nota a fost stearsa", Toast.LENGTH_SHORT).show();
                    }
                }.execute(currentGrade);
            }
        });
        return rowItem;
    }
}
