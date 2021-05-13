package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {
    EditText et_name, et_email, et_dno,et_mobile, et_department,et_collegename, et_pass;
    RadioGroup radiogroup;
    Button bt_register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_dno = findViewById(R.id.et_dno);
        et_department = findViewById(R.id.et_department);
        et_mobile = findViewById(R.id.et_mobile);
        bt_register = findViewById(R.id.bt_register);
        et_pass=findViewById(R.id.et_pass);
        // progressbar = findViewById(R.id.progressbar);
        et_collegename = findViewById(R.id.et_collegename);
        mAuth=FirebaseAuth.getInstance();
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String dno = et_dno.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String mobile=et_mobile.getText().toString().trim();
                String password=et_pass.getText().toString().trim();
                String department=et_department.getText().toString().trim();

                String collegeName = et_collegename.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Student student = new Student(name, dno,mobile,department,collegeName);
                            FirebaseDatabase.getInstance().getReference("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileUpdateActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(ProfileUpdateActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }   else {
                            Toast.makeText(ProfileUpdateActivity.this, "Registeration failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    et_name.setText("");
    et_dno.setText("");
    et_mobile.setText("");
    et_department.setText("");
    et_collegename.setText("");
    et_pass.setText("");
    et_email.setText("");


    }


}