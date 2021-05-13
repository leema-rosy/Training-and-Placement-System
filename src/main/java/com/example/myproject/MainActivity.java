package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username,et_password;
 private ImageView iv_login;
    private TextView tv_signup;
    private TextView tv_forgot_pw;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        iv_login=findViewById(R.id.iv_login);
        tv_signup=findViewById(R.id.tv_signup);
        tv_forgot_pw= findViewById(R.id.tv_forgot_pw);
        iv_login.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
        tv_forgot_pw.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


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
            case R.id.iv_login:
              Loginuser();
                break;
            case R.id.tv_signup:
                startActivity(new Intent(getApplicationContext(),Register.class));
                break; 
            case R.id.tv_forgot_pw:
                startActivity(new Intent(getApplicationContext(),Forgot_password.class));
                   break;
                
        }
    }

    private void Loginuser() {
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
                if(task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        Toast.makeText(MainActivity.this, "Pls Wait while Loading", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your mail for Email verification", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                    }

                }

        });
    et_username.setText("");
    et_password.setText("");
    }
}