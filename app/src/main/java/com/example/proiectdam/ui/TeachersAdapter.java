package com.example.proiectdam.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proiectdam.R;
import com.example.proiectdam.data.StudyObject;
import com.example.proiectdam.data.User;
import java.util.List;

public class TeachersAdapter extends BaseAdapter {
    private Context context;
    private List<User> teachersList;
    private LayoutInflater inflater;

    public TeachersAdapter(Context context, List<User> teachers) {
        this.context = context;
        this.teachersList = teachers;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.teachersList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.teachersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowItem = inflater.inflate(R.layout.item_teacher,parent,false);
        TextView tvTeacherName = rowItem.findViewById(R.id.tvTeacherName);
        TextView tvTeacherEmail = rowItem.findViewById(R.id.tvTeacherEmail);
        TextView tvTeacherPhone = rowItem.findViewById(R.id.tvTeacherPhone);
        Button btnViewObjects = rowItem.findViewById(R.id.btnTeacherObjects);


        final User currentTeacher = teachersList.get(position);
       final List<StudyObject> studyObjects = currentTeacher.getStudyObjects();

        tvTeacherName.setText(currentTeacher.getNume() + currentTeacher.getPrenume());
        tvTeacherEmail.setText(currentTeacher.getEmail());
        tvTeacherPhone.setText(currentTeacher.getTelefon());



        btnViewObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = "";
                for(int i=0;i<studyObjects.size();i++)
                {
                    obj += studyObjects.get(i).getAbbreviation() + ",";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(rowItem.getContext());
                View objView = inflater.inflate(R.layout.teacher_objects,null);
                ListView lvObjects = objView.findViewById(R.id.lvTeacherObjects);
                TeacherObjectsAdapter adapter = new TeacherObjectsAdapter(objView.getContext(),studyObjects);
                lvObjects.setAdapter(adapter);

                builder.setView(objView);
                builder.setTitle("Materii predate:");

                builder.setNegativeButton("Inchide", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
        return rowItem;
    }

    public void updateTeachersList(List<User> teachers)
    {
        this.teachersList = teachers;
        notifyDataSetChanged();
    }
}
