package com.example.proiectdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiectdam.data.GetUserLoginAsync;
import com.example.proiectdam.data.GetUsersAsync;
import com.example.proiectdam.data.Grade;
import com.example.proiectdam.data.InsertGradeAsync;
import com.example.proiectdam.data.InsertUsersAsync;
import com.example.proiectdam.data.SharedPrefs;
import com.example.proiectdam.data.User;
import com.example.proiectdam.ui.StudentProfile;
import com.example.proiectdam.ui.TeacherProfile;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText etEmail,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seed();

        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        Button loginBtn = findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateLogin())
                {
                        User user = new User();
                        user.setEmail(etEmail.getText().toString());
                        user.setParola(etPassword.getText().toString());
                  new GetUserLoginAsync(MainActivity.this)
                  {
                      @Override
                      protected void onPostExecute(User user) {
                          super.onPostExecute(user);
                          if(user == null)
                          {
                              Toast.makeText(MainActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                          }
                          else if(user.getRol().equals("prof"))
                          {
                                Intent intent = new Intent(MainActivity.this, TeacherProfile.class);
                                intent.putExtra("teacher", user);
                                startActivity(intent);
                          }
                          else if(user.getRol().equals("stud"))
                          {
                              Intent intent = new Intent(MainActivity.this, StudentProfile.class);
                              intent.putExtra("student", user);
                              startActivity(intent);
                          }

                      }
                  }.execute(user);
                }
            }
        });
    }

    private boolean validateLogin()
    {
        if(etEmail.getText().toString().isEmpty())
        {
            etEmail.setError(getString(R.string.login_error_no_username));
            return false;
        }
        if(etPassword.getText().toString().isEmpty())
        {
            etPassword.setError(getString(R.string.login_error_no_password));
            return false;
        }
        return true;
    }

    private void seed()
    {
        new GetUsersAsync(this){
            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                boolean emptyDB = true;
                User stud1, prof1;
                for(User u : users)
                {
                    if(u.getEmail().equals("student@yahoo.com") || u.getEmail().equals("profesor@yahoo.com"))
                    {
                         emptyDB = false;
                    }
                }
                    if(emptyDB)
                    {
                        stud1 = new User("student@yahoo.com","parola","Petrescu","Mihai","stud");
                        prof1 = new User("profesor@yahoo.com","parola","Popescu","Ion","prof");
                        User[] new_users = new User[2];
                        new_users[0] = stud1;
                        new_users[1] = prof1;
                        new InsertUsersAsync(getBaseContext()).execute(new_users);
                    }

            }
        }.execute();
    }
}
