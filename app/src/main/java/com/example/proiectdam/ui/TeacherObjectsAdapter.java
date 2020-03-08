package com.example.proiectdam.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proiectdam.data.StudyObject;
import com.example.proiectdam.R;

import java.util.List;

public class TeacherObjectsAdapter extends BaseAdapter {

    Context context;
    List<StudyObject> studyObjects;
    LayoutInflater layoutInflater;

    public TeacherObjectsAdapter(Context context, List<StudyObject> studyObjects) {
        this.context = context;
        this.studyObjects = studyObjects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.studyObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return this.studyObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = layoutInflater.inflate(R.layout.item_teacher_objects,parent,false);
        TextView tvObjName = rowItem.findViewById(R.id.tvObjName);
        TextView tvObjAbbreviation = rowItem.findViewById(R.id.tvObjAbbreviation);
        TextView tvObjCredits = rowItem.findViewById(R.id.tvObjCredits);

        StudyObject stObj = studyObjects.get(position);

        tvObjName.setText(stObj.getName());
        String abbr = "(" + stObj.getAbbreviation() + ")";
        tvObjAbbreviation.setText(abbr);
        String credits = "Credite:" +stObj.getCredits();
        tvObjCredits.setText(credits);
        return rowItem;


    }
}
