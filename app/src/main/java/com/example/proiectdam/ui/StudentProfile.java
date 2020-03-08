package com.example.proiectdam.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.proiectdam.R;
import com.example.proiectdam.data.Exam;
import com.example.proiectdam.data.SharedPrefs;
import com.example.proiectdam.data.UpdateUserAsync;
import com.example.proiectdam.data.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {

    private User student;
    private SharedPrefs prefs;
    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbReference = fbDatabase.getReference();
    private ArrayList<Exam> exams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        student = getIntent().getParcelableExtra("student");
        prefs = SharedPrefs.getInstance(this);
        prefs.saveString("email",student.getEmail());
        prefs.saveInt("id",student.getUserId());
        BottomNavigationView bottomNav = findViewById(R.id.studentBottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Exam exam = dataSnapshot.getValue(Exam.class);

                DataSnapshot examSnapshot = dataSnapshot.child("examene");
                Iterable<DataSnapshot> examChildren = examSnapshot.getChildren();
                for(DataSnapshot e : examChildren)
                {
                    Exam ex = e.getValue(Exam.class);
                    exams.add(ex);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.studentFragmentContainer,
                    new LabFragment(student)).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                showChangePassDialog();
                break;
            case R.id.showExams:
                showExams();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showExams() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfile.this);
        LayoutInflater inflater = StudentProfile.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.show_exams, null);
        ListView lvExams = view.findViewById(R.id.lvExams);

        ExamsAdapter examsAdapter = new ExamsAdapter(exams,this);
        lvExams.setAdapter(examsAdapter);
        builder.setView(view);



        builder.setTitle("Vizualizare examene");

        builder.setNegativeButton("Inchide", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
            }
        });
        builder.create().show();

    }

    private void showChangePassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfile.this);
        LayoutInflater inflater = StudentProfile.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.change_pass, null);
        final EditText etNewPass = view.findViewById(R.id.etNewPass);
        builder.setView(view);
        builder.setTitle("Schimbare parola");

        builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                if(etNewPass.getText().toString().isEmpty()) {
                    Toast.makeText(StudentProfile.this, "Introduceti noua parola", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    student.setParola(etNewPass.getText().toString());
                    new UpdateUserAsync(StudentProfile.this)
                    {
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(StudentProfile.this, "Parola a fost schimbata", Toast.LENGTH_SHORT).show();
                        }
                    }.execute(student);
                }
            }
        });
        builder.setNegativeButton("Anuleaza", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
            }
        });
        builder.create().show();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = new LabFragment(student);

                    switch (menuItem.getItemId()) {
                        case R.id.nav_study:
                            selectedFragment = new LabFragment(student);
                            break;
                        case R.id.nav_projects:
                            selectedFragment = new ProjectsFragment();
                            break;
                        case R.id.nav_exams:
                            selectedFragment = new ExamsFragment(student);
                            break;
                        case R.id.nav_teachers:
                            if(isNetworkAvailable())
                                selectedFragment = new RemoteViewTeachersFragment();
                            else
                                Toast.makeText(StudentProfile.this, "You need internet connection", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.studentFragmentContainer,
                            selectedFragment).commit();
                    return true;
                }
            };

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
