package com.example.proiectdam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiectdam.R;
import com.example.proiectdam.data.GetGradesAsync;
import com.example.proiectdam.data.Grade;
import com.example.proiectdam.data.SharedPrefs;
import com.example.proiectdam.data.User;

import java.util.List;

public class ProjectsFragment extends Fragment {

    private ListView lvGrades;
   private SharedPrefs prefs;
   private TextView tvTitle;
   private int loggedInId;
   private String loggedInEmail;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades,container ,false);

        lvGrades = view.findViewById(R.id.lvGrades);
        tvTitle = view.findViewById(R.id.gradesTitle);
        tvTitle.setText(R.string.projectGrades);
        prefs = SharedPrefs.getInstance(getActivity());
        loggedInId = prefs.getInt("id");
        loggedInEmail = prefs.getString("email");

        final String[] params = new String[2];
        params[0] = String.valueOf(loggedInId);
        params[1] = "proiect";
        new GetGradesAsync(getContext())
        {
            @Override
            protected void onPostExecute(List<Grade> grades) {
                super.onPostExecute(grades);
                if(grades.size() > 0)
                {
                    GradesAdapter gradesAdapter = new GradesAdapter(grades,getActivity());
                    lvGrades.setAdapter(gradesAdapter);
                    Toast.makeText(getContext(), "Note proiecte incarcate", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Nu exista note", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(params);

        return view;
    }
}
