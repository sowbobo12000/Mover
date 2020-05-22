package com.example.mover2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NeederLoginRegActivity extends AppCompatActivity {
    private Button NeederLoginButton;
    private Button NeederRegisterButton;
    private TextView NeederRegisterLink;
    private TextView NeederStatus;
    private EditText EmailNeeder;
    private EditText PasswordNeeder;
    private ProgressDialog loadingBar;

    private DatabaseReference neederDatabaseRef;
    private FirebaseAuth mAuth;
    private String onlineNeederID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needer_login_reg);

        mAuth = FirebaseAuth.getInstance();


        NeederLoginButton = (Button) findViewById(R.id.needer_login_btn);
        NeederRegisterButton = (Button) findViewById(R.id.needer_register_btn);
        NeederRegisterLink = (TextView) findViewById(R.id.register_needer_link);
        NeederStatus = (TextView) findViewById(R.id.needer_status);
        EmailNeeder = (EditText) findViewById(R.id.needer_email);
        PasswordNeeder = (EditText) findViewById(R.id.needer_password);
        loadingBar = new ProgressDialog(this);


        NeederRegisterButton.setVisibility(View.INVISIBLE);
        NeederRegisterButton.setEnabled(false);

        NeederRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeederLoginButton.setVisibility(View.INVISIBLE);
                NeederRegisterLink.setVisibility(View.INVISIBLE);
                NeederStatus.setText("Register Needer");

                NeederRegisterButton.setVisibility(View.VISIBLE);
                NeederRegisterButton.setEnabled(true);
            }
        });
        NeederRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailNeeder.getText().toString();
                String password = PasswordNeeder.getText().toString();

                RegisterNeeder(email,password);
            }
        });
        NeederLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailNeeder.getText().toString();
                String password = PasswordNeeder.getText().toString();

                SigninNeeder(email,password);

            }
        });
    }

    private void SigninNeeder(String email, String password) {
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(NeederLoginRegActivity.this,"Please write Email...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(NeederLoginRegActivity.this,"Please write Password...",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Needer Login");
            loadingBar.setMessage("Please wait, while we are checking your credientials...");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {


                        Intent neederIntent = new Intent(NeederLoginRegActivity.this, NeederMapsActivity.class);
                        startActivity(neederIntent);
                        Toast.makeText(NeederLoginRegActivity.this, "Needer Logged in Successfully...",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else {
                        Toast.makeText(NeederLoginRegActivity.this, "Login Unsuccessful, Please try Again", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void RegisterNeeder(String email, String password) {
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(NeederLoginRegActivity.this,"Please write Email...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(NeederLoginRegActivity.this,"Please write Password...",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Needer Registration");
            loadingBar.setMessage("Please wait, while we register your data...");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        onlineNeederID = mAuth.getCurrentUser().getUid();
                        neederDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(onlineNeederID);
                        neederDatabaseRef.setValue(true);


                        Intent neederIntent = new Intent(NeederLoginRegActivity.this, NeederMapsActivity.class);
                        startActivity(neederIntent);
                        Toast.makeText(NeederLoginRegActivity.this, "Mover Register Successfully...",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();


                    }
                    else {
                        Toast.makeText(NeederLoginRegActivity.this, "Registration Unsuccessful, Please try Again", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }
}
