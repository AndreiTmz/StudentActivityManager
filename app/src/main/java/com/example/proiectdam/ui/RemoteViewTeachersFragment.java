package com.example.proiectdam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiectdam.R;
import com.example.proiectdam.data.JsonReader;
import com.example.proiectdam.data.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RemoteViewTeachersFragment extends Fragment {

    private ListView lvTeachers;
    private TeachersAdapter teachersAdapter;

    public RemoteViewTeachersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_teachers, container, false);
        lvTeachers = view.findViewById(R.id.lvTeachers);
        teachersAdapter = new TeachersAdapter(getActivity(), new ArrayList<User>());
        lvTeachers.setAdapter(teachersAdapter);
        connect();
        return view;
    }

    private void connect() {
        JsonReader jsonReader = new JsonReader() {
            @Override
            protected void onPostExecute(String s) {
                if (s != null) {
                    List<User> teachers = parseJson(s);
                    teachersAdapter.updateTeachersList(teachers);
                }

            }

        };
        try {
            jsonReader.execute(new URL("http://www.mocky.io/v2/5dd036ad2f000036003f2007"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
