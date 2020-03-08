package com.example.proiectdam.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiectdam.R;
import com.example.proiectdam.data.Database;
import com.example.proiectdam.data.GetUsersAsync;
import com.example.proiectdam.data.User;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsFragment extends Fragment {

    private StudentsAdapter adapter;
    private Database db;
    private ListView lvStudentsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_students,container,false);

        db = Database.getInstance(this.getActivity());
        lvStudentsList = view.findViewById(R.id.lvStudents);
        viewStudents();

        lvStudentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User student = (User)lvStudentsList.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(),StudentDetails.class);
                intent.putExtra("student",student);
                startActivity(intent);
            }
        });

        return view;
    }

    private void viewStudents() {

        new GetUsersAsync(getContext()){
            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                adapter = new StudentsAdapter(getActivity(),users);
                lvStudentsList.setAdapter(adapter);
            }
        }.execute("stud");
    }
}
