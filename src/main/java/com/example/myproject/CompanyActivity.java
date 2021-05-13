package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyActivity extends AppCompatActivity {
    EditText et_username,et_cname,et_mail,et_location,et_password;
    Button bt_login;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        et_cname=findViewById(R.id.et_cname);
        et_username=findViewById(R.id.et_username);
        et_location=findViewById(R.id.et_location);
        et_mail=findViewById(R.id.et_mail);
        et_password=findViewById(R.id.et_password);
        bt_login=findViewById(R.id.bt_login);
        mAuth = FirebaseAuth.getInstance();
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanyRegister();
            }
        });
    }

    private void CompanyRegister() {
        String cname=et_cname.getText().toString();
        String location=et_location.getText().toString();
        String username=et_username.getText().toString();
        String email=et_username.getText().toString();
        String password=et_password.getText().toString();
        if(email.isEmpty())
        {
            et_username.setError("Email is required");
            et_username.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            et_password.setError("Password is required");
            et_password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_username.setError("Enter a Valid Email ID");
            et_username.requestFocus();
        }
        if (password.length()<8)
        {
            et_password.setError("password Length should be more than 8");
            et_password.requestFocus();
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                Company company = new Company(cname, location,username,email);
                FirebaseDatabase.getInstance().getReference("Company").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CompanyActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(CompanyActivity.this,InformationActivity.class));

                        } else {
                            Toast.makeText(CompanyActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }   else {
                Toast.makeText(CompanyActivity.this, "Registeration failed", Toast.LENGTH_LONG).show();
            }
            }

        });
        et_username.setText("");
        et_password.setText("");

    }

}