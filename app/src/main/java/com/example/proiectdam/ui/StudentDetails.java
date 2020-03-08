package com.example.proiectdam.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proiectdam.R;
import com.example.proiectdam.data.DeleteUserAsync;
import com.example.proiectdam.data.Exam;
import com.example.proiectdam.data.GetGradesAsync;
import com.example.proiectdam.data.Grade;
import com.example.proiectdam.data.InsertGradeAsync;
import com.example.proiectdam.data.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class StudentDetails extends AppCompatActivity {

    private FloatingActionButton btnDeleteStudent, exams, marks, action;
    private TextView studentLastname, studentFirstname, studentEmail;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private Boolean isOpen = false;
    private User student;
    private ListView lvGrades;

    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbReference = fbDatabase.getReference("examene");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        initViews();
        initAnim();


        Intent intent = getIntent();
        student = intent.getParcelableExtra("student");

        String nume = student.getNume();
        String prenume = student.getPrenume();
        String email = student.getEmail();

        studentLastname.setText(nume);
        studentFirstname.setText(prenume);
        studentEmail.setText(email);
        loadGrades();

        makeCollapsibleFloatingButton();
        addMarkListener();
        addExamEventListener();
        deleteStudentListener();
    }

    public void loadGrades() {
        String[] params = new String[1];
        params[0] = String.valueOf(student.getUserId());
        new GetGradesAsync(this){
            @Override
            protected void onPostExecute(List<Grade> grades) {
                super.onPostExecute(grades);
                GradesAdapterForTeacher gradesAdapterForTeacher = new GradesAdapterForTeacher(grades,getBaseContext());
                lvGrades.setAdapter(gradesAdapterForTeacher);
            }
        }.execute(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(StudentDetails.this);
            LayoutInflater inflater = StudentDetails.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.info_dialog, null);

            builder.setView(view);
            builder.setTitle("Info");

            builder.setNegativeButton("Inchide", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface di, int i) {
                }
            });
            builder.create().show();

        }
        return true;
    }

    private void initViews()
    {
        studentLastname = findViewById(R.id.tvStudentLastName);
        studentFirstname = findViewById(R.id.tvStudentFirstName);
        studentEmail = findViewById(R.id.studentEmail);
        btnDeleteStudent = findViewById(R.id.btnDeleteStudent);
        lvGrades = findViewById(R.id.lvDetailsGrades);
        exams = findViewById(R.id.exams);
        marks = findViewById(R.id.marks);
        action = findViewById(R.id.action);
    }

    private void initAnim()
    {
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
    }

    private void makeCollapsibleFloatingButton()
    {
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    marks.startAnimation(fab_close);
                    exams.startAnimation(fab_close);
                    btnDeleteStudent.startAnimation(fab_close);
                    action.startAnimation(fab_anticlock);
                    marks.setClickable(false);
                    exams.setClickable(false);
                    btnDeleteStudent.setClickable(false);
                    isOpen = false;
                } else {
                    marks.startAnimation(fab_open);
                    exams.startAnimation(fab_open);
                    btnDeleteStudent.startAnimation(fab_open);
                    action.startAnimation(fab_clock);
                    marks.setClickable(true);
                    exams.setClickable(true);
                    btnDeleteStudent.setClickable(true);
                    isOpen = true;
                }

            }
        });
    }

    private void addMarkListener()
    {
        marks.setOnClickListener(new View.OnClickListener() {
            //            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentDetails.this);
                LayoutInflater inflater = StudentDetails.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.add_mark, null);

                final EditText etNumeMaterie = view.findViewById(R.id.etStudyObject);
                final EditText etNota = view.findViewById(R.id.markInput);
                final RadioButton rbProiect,rbSeminar,rbExamen;
                final RadioGroup rgGradeType;
                rgGradeType = view.findViewById(R.id.rgGradeType);
                rbProiect = view.findViewById(R.id.rbProiect);
                rbSeminar = view.findViewById(R.id.rbSeminar);
                rbExamen = view.findViewById(R.id.rbExamen);
                builder.setView(view);
                builder.setTitle("Note").setMessage("Adaugare nota");

                builder.setPositiveButton("Adauga nota", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int i) {
                        if(etNumeMaterie.getText().toString().isEmpty())
                        {
                            Toast.makeText(StudentDetails.this, "Introduceti numele materiei", Toast.LENGTH_SHORT).show();
                        }
                        else if(etNota.getText().toString().isEmpty() ||
                        Integer.parseInt(etNota.getText().toString()) < 1 ||
                                Integer.parseInt(etNota.getText().toString()) >10)
                        {
                            Toast.makeText(StudentDetails.this, "Introduceti nota ( intre 1 si 10)", Toast.LENGTH_SHORT).show();
                        }
                        else if(rgGradeType.getCheckedRadioButtonId() == -1)
                        {
                            Toast.makeText(StudentDetails.this, "Selectati tipul notei", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String numeMaterie = etNumeMaterie.getText().toString();
                            int nota = Integer.parseInt(etNota.getText().toString());
                            String tip = "";
                            if(rbExamen.isChecked()) { tip = "examen"; }
                            else if(rbProiect.isChecked()) { tip = "proiect"; }
                            else if(rbSeminar.isChecked()) { tip = "seminar"; }

                            final Grade grade = new Grade(nota,numeMaterie,tip,student.getUserId());

                            new InsertGradeAsync(StudentDetails.this){
                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                    Toast.makeText(StudentDetails.this, "Nota a fost adaugata", Toast.LENGTH_SHORT).show();
                                    loadGrades();
                                }
                            }.execute(grade);
                        }
                    }
                });
                builder.setNegativeButton("Anuleaza", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int i) {
                    }
                });
                builder.create().show();
            }
        });
    }

    private void addExamEventListener()
    {
        exams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentDetails.this);
                LayoutInflater inflater = StudentDetails.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.add_exam, null);

                final EditText classRoom = view.findViewById(R.id.classroom);
                final EditText etStudyObj = view.findViewById(R.id.etStudyObject);
                final Calendar examDayCalendar = Calendar.getInstance();
                final EditText etDate = view.findViewById(R.id.examDay);
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        examDayCalendar.set(Calendar.YEAR,year);
                        examDayCalendar.set(Calendar.MONTH,month);
                        examDayCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updateDate(examDayCalendar,etDate);
                    }
                };

                etDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(StudentDetails.this,date,examDayCalendar
                                .get(Calendar.YEAR),examDayCalendar
                                .get(Calendar.MONTH),examDayCalendar
                                .get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                final Calendar examHourCalendar = Calendar.getInstance();
                final EditText etHour = view.findViewById(R.id.examHour);
                final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        examHourCalendar.set(Calendar.HOUR,hourOfDay);
                        examHourCalendar.set(Calendar.MINUTE,minute);
                        updateTime(examHourCalendar,etHour);
                    }
                };

                etHour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new TimePickerDialog(StudentDetails.this,time,examHourCalendar.get(Calendar.HOUR),
                                examHourCalendar.get(Calendar.MINUTE),true).show();
                    }
                });


                builder.setView(view);
                builder.setTitle("Examene").setMessage("Adaugare examen");

                builder.setPositiveButton("Adauga examen", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int i) {
                        String sala = classRoom.getText().toString();
                        String data = etDate.getText().toString();
                        String ora = etHour.getText().toString();
                        String materia = etStudyObj.getText().toString();

                        String examId = UUID.randomUUID().toString();
                        Exam exam = new Exam(examId,sala,data,ora,materia);
                        dbReference.child(exam.getId()).setValue(exam);
                        Toast.makeText(StudentDetails.this, "Examen adaugat", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Anuleaza", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int i) {
                    }
                });
                builder.create().show();
            }
        });
    }

    private void deleteStudentListener()
    {
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteUserAsync(getBaseContext()){
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Intent intent1 = new Intent(StudentDetails.this,TeacherProfile.class);
                        startActivity(intent1);
                        Toast.makeText(StudentDetails.this, "Studentul a fost sters", Toast.LENGTH_SHORT).show();
                    }
                }.execute(student);

            }
        });
    }

    private void updateDate(Calendar c,EditText et)
    {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        et.setText(sdf.format(c.getTime()));
    }
    private void updateTime(Calendar c, EditText et)
    {
        String hourFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(hourFormat,Locale.getDefault());
        et.setText(sdf.format(c.getTime()));
    }



}
// http://www.mocky.io/v2/5dd036ad2f000036003f2007