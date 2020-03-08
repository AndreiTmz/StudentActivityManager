package com.example.proiectdam.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proiectdam.R;
import com.example.proiectdam.data.User;

import java.util.List;


public class StudentsAdapter extends BaseAdapter {
    private List<User> students;
    private LayoutInflater inflater;

    public StudentsAdapter(Context context,List<User> students)
    {
        this.students = students;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.students.size();
    }

    @Override
    public User getItem(int position) {
        return this.students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem =inflater.inflate(R.layout.item_student,parent,false);
        TextView studentLastName = rowItem.findViewById(R.id.lvStudentLName);
        TextView studentFirstName = rowItem.findViewById(R.id.lvStudentFName);
        TextView studentEmail = rowItem.findViewById(R.id.lvStudentEmail);


        User currentStudent = students.get(position);

        studentLastName.setText(currentStudent.getNume());
        studentFirstName.setText(currentStudent.getPrenume());
        studentEmail.setText(currentStudent.getEmail());
        return rowItem;
    }
}
