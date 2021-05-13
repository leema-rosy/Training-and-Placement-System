package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.security.KeyStore;
import java.util.MissingFormatArgumentException;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username,et_email,et_pass,et_conpass;
    private Button bt_register;
    private TextView tv_login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_username=findViewById(R.id.et_username);
        et_email=findViewById(R.id.et_email);
        et_pass=findViewById(R.id.et_pass);
        et_conpass=findViewById(R.id.et_conpass);
        bt_register=findViewById(R.id.bt_register);
        tv_login=findViewById(R.id.tv_login);
        bt_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_register:
                RegisterUser();
                break;
            case R.id.tv_login:
                startActivity(new Intent(Register.this, MainActivity.class));
        }

    }

    private void RegisterUser() {
        String name=et_username.getText().toString();
        String email=et_email.getText().toString();

        String password=et_pass.getText().toString();
        String conpass=et_conpass.getText().toString();
        if(name.isEmpty())
        {
            et_username.setError("FullName  is required");
            et_username.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            et_email.setError("Email is required");
            et_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_username.setError("Enter a Valid Email ID");
            et_username.requestFocus();
        }
        if (password.length()<8)
        {
            et_pass.setError("password Length should be more than 8");
            et_pass.requestFocus();
        }
        if(conpass.isEmpty())
        {
            et_conpass.setError("Enter confirm password");
            et_conpass.requestFocus();
        }
        if(!password.equals(conpass))
        {
            et_conpass.setError("Password does Not match");
            et_conpass.requestFocus();

        }
     mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()) {
                 User user = new User(name, email);
                 FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                         .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(Register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();

                         } else {
                             Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_LONG).show();
                         }
                     }
                 });

             }   else {
                 Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_LONG).show();
             }
         }
     });
        et_username.setText("");
        et_email.setText("");
        et_pass.setText("");
        et_conpass.setText("");

     }

}
