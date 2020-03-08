package com.example.proiectdam.data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonReader extends AsyncTask<URL, Void, String> {


    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder result = new StringBuilder();

            String line = "";

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            inputStreamReader.close();
            inputStream.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> parseJson(String json) {
        List<User> teachers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray teachersObject = jsonObject.getJSONArray("teachers");
            for (int i = 0; i < teachersObject.length(); i++) {
                JSONObject currentObject = teachersObject.getJSONObject(i);
                User teacher = new User();
                String fullName[] = currentObject.getString("name").split(" ");
                String nume, prenume="";
                nume = fullName[0];
                for (int k = 1; k < fullName.length; k++) {
                    prenume += " " + fullName[k];
                }
//                if (fullName[2] != null) {
//                    prenume += " " + fullName[2];
//                }
                teacher.setNume(nume);
                teacher.setPrenume(prenume);
                teacher.setEmail(currentObject.getString("email"));
                teacher.setTelefon(currentObject.getString("phone"));

                JSONArray studyObject = currentObject.getJSONArray("objects");
                for (int j = 0; j < studyObject.length(); j++) {
                    JSONObject currentStudyObject = studyObject.getJSONObject(j);
                    StudyObject studyObj = new StudyObject();
                    studyObj.setName(currentStudyObject.getString("name"));
                    studyObj.setCredits(Integer.parseInt(currentStudyObject.getString("credits")));
                    studyObj.setAbbreviation(currentStudyObject.getString("abbreviation"));
                    teacher.getStudyObjects().add(studyObj);
                }

                teachers.add(teacher);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teachers;
    }


}
