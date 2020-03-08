package com.example.proiectdam.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proiectdam.R;
import com.example.proiectdam.data.Grade;

import java.util.List;

public class GradesAdapter extends BaseAdapter {

    private List<Grade> grades;
    private LayoutInflater inflater;

    public GradesAdapter(List<Grade> grades, Context context) {
        this.grades = grades;
        this.inflater = LayoutInflater.from(context);
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
        View rowItem = inflater.inflate(R.layout.item_grade,parent,false);
        TextView tvMaterie, tvNota;
        tvMaterie = rowItem.findViewById(R.id.tvMaterie);
        tvNota = rowItem.findViewById(R.id.tvNota);

        Grade currentGrade = grades.get(position);

        tvMaterie.setText(currentGrade.getMaterie());
        tvNota.setText(String.valueOf(currentGrade.getNota()));

        return rowItem;
    }
}
