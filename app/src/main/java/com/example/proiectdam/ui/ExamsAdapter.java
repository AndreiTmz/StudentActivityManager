package com.example.proiectdam.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proiectdam.R;
import com.example.proiectdam.data.Exam;

import java.util.List;

public class ExamsAdapter extends BaseAdapter {

    List<Exam> exams;
    LayoutInflater inflater;

    public ExamsAdapter(List<Exam> exams, Context context)
    {
        this.exams = exams;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return exams.size();
    }

    @Override
    public Object getItem(int position) {
        return exams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = inflater.inflate(R.layout.item_exam,parent,false);
        TextView tvExamObj,tvExamDay, tvExamHour, tvExamRoom;
        tvExamObj = rowItem.findViewById(R.id.tvExamObj);
        tvExamDay = rowItem.findViewById(R.id.tvExamDay);
        tvExamHour = rowItem.findViewById(R.id.tvExamHour);
        tvExamRoom = rowItem.findViewById(R.id.tvExamClassRoom);

        Exam currentExam = exams.get(position);

        tvExamObj.setText(currentExam.getStudyObject());
        tvExamDay.setText(currentExam.getDay());
        tvExamHour.setText(currentExam.getHour());
        tvExamRoom.setText(currentExam.getClassRoom());

        return rowItem;
    }
}
