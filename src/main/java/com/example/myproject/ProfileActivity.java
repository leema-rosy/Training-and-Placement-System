package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigation;
    private ActionBarDrawerToggle mToggle;
    private FirebaseUser user;
    private String userID;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigation = findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        user= FirebaseAuth.getInstance().getCurrentUser();
        mRef= FirebaseDatabase.getInstance().getReference("Student");
        userID=user.getUid();
        final TextView tv_name=(TextView) findViewById(R.id.tv_name);
        final TextView tv_dno=(TextView) findViewById(R.id.tv_dno);
        final TextView tv_mobile=(TextView) findViewById(R.id.tv_mobile);
        final TextView tv_dep=(TextView) findViewById(R.id.tv_dep);
        final TextView tv_cname=(TextView) findViewById(R.id.tv_cname);

        mRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student userProfile=snapshot.getValue(Student.class);
                if(userProfile!=null)
                {
                    String name=userProfile.name;
                    String dno=userProfile.dno;
                    String department=userProfile.department;
                    String mobile=userProfile.mobile;
                    String collegename=userProfile.collegename;
                    tv_name.setText(name);
                    tv_dno.setText(dno);
                    tv_dep.setText(department);
                    tv_mobile.setText(mobile);
                    tv_cname.setText(collegename);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(this, ProfileUpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.qualification:
                startActivity(new Intent(this, QualificationActivity.class));
                break;
            case R.id.notify:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity( new Intent(ProfileActivity.this, MainActivity.class));
                break;
            /*case R.id.share:
                startActivity(new Intent(this, ShareActivity.class));
                break;
            case R.id.send:
                startActivity(new Intent(this, SendActivity.class));
                break;*/


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
