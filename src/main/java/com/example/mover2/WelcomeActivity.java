package com.example.mover2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class WelcomeActivity extends AppCompatActivity {
    private Button WelcomeMoverButton;
    private Button WelcomeNeederButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome3);



        WelcomeMoverButton = (Button) findViewById(R.id.welcome_mover_btn);
        WelcomeNeederButton = (Button) findViewById(R.id.welcome_needer_btn);

        WelcomeMoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterMoverIntent = new Intent(WelcomeActivity.this,MoverLoginRegActivity.class);
                startActivity(LoginRegisterMoverIntent);
            }
        });

        WelcomeNeederButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterNeederIntent = new Intent(WelcomeActivity.this, NeederLoginRegActivity.class);
                startActivity(LoginRegisterNeederIntent);
            }
        });
    }
}
