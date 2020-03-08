package com.example.proiectdam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiectdam.R;
import com.example.proiectdam.data.Database;
import com.example.proiectdam.data.GetUsersAsync;
import com.example.proiectdam.data.InsertUsersAsync;
import com.example.proiectdam.data.User;

import java.util.List;

public class AddUserFragment extends Fragment {

    private EditText etLastName, etFirstName,etEmail;
    private Spinner spRole;
    private Database db;
    private String role;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        Button buttonAddStudent = view.findViewById(R.id.btnAddStudent);
        etLastName = view.findViewById(R.id.etLastName);
        etFirstName = view.findViewById(R.id.etFirstName);
        etEmail = view.findViewById(R.id.etEmail);
        spRole = view.findViewById(R.id.spRole);
        db = Database.getInstance(this.getActivity());

        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUser()) {

                    final User user = new User(etEmail.getText().toString(),"defaultpass",etLastName.getText().toString(),
                            etFirstName.getText().toString(),role);

                    new GetUsersAsync(getContext())
                    {
                        @Override
                        protected void onPostExecute(List<User> users) {
                            super.onPostExecute(users);
                            boolean exists = false;
                            for(User u:users)
                            {
                                if(u.getEmail().equals(etEmail.getText().toString()))
                                {
                                    exists = true;
                                }
                            }
                            if(exists)
                            {
                                Toast.makeText(getContext(), "Utilizatorul exista deja", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                new InsertUsersAsync(getContext()){
                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        super.onPostExecute(aVoid);
                                        Toast.makeText(getContext(), "Utilizator adaugat", Toast.LENGTH_SHORT).show();
                                    }
                                }.execute(user);
                            }
                        }
                    }.execute();
                }
            }
        });
        return view;
    }

    private boolean validateUser(){
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError(getString(R.string.add_stud_error_no_last_name));
            return false;
        } else if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError(getString(R.string.add_stud_error_no_first_name));
            return false;
        }

        else if(etEmail.getText().toString().isEmpty())
        {
            etEmail.setError(getString(R.string.add_stud_error_no_email));
            return false;
        }

        role = String.valueOf(spRole.getSelectedItem()).equals("Student")?"stud":"prof";

        return true;


    }
}
