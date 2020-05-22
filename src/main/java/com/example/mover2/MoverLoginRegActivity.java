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

import org.w3c.dom.Text;

public class MoverLoginRegActivity extends AppCompatActivity {
    private Button MoverLoginButton;
    private Button MoverRegisterButton;
    private TextView MoverRegisterLink;
    private TextView MoverStatus;
    private EditText EmailMover;
    private EditText PasswordMover;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    private DatabaseReference moverDatabaseRef;
    private String onlineMoverID;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_login_reg);

        mAuth = FirebaseAuth.getInstance();


        MoverLoginButton = (Button) findViewById(R.id.mover_login_btn);
        MoverRegisterButton = (Button) findViewById(R.id.mover_register_btn);
        MoverRegisterLink = (TextView) findViewById(R.id.register_mover_link);
        MoverStatus = (TextView) findViewById(R.id.mover_status);
        EmailMover = (EditText) findViewById(R.id.mover_email);
        PasswordMover = (EditText) findViewById(R.id.mover_password);
        loadingBar = new ProgressDialog(this);

        MoverRegisterButton.setVisibility(View.INVISIBLE);
        MoverRegisterButton.setEnabled(false);

        MoverRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoverLoginButton.setVisibility(View.INVISIBLE);
                MoverRegisterLink.setVisibility(View.INVISIBLE);
                MoverStatus.setText("Register Mover");


                MoverRegisterButton.setVisibility(View.VISIBLE);
                MoverRegisterButton.setEnabled(true);
            }
        });
        MoverRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailMover.getText().toString();
                String password = PasswordMover.getText().toString();

                RegisterMover(email,password);
            }
        });
        MoverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailMover.getText().toString();
                String password = PasswordMover.getText().toString();

                SigninMover(email,password);

            }
        });
    }

    private void SigninMover(String email, String password) {
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(MoverLoginRegActivity.this,"Please write Email...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(MoverLoginRegActivity.this,"Please write Password...",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Mover Login");
            loadingBar.setMessage("Please wait, while we are checking your credientials...");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {


                        Intent moverIntent = new Intent(MoverLoginRegActivity.this, MoverMapsActivity.class);
                        startActivity(moverIntent);
                        Toast.makeText(MoverLoginRegActivity.this, "Mover Logged in Successfully...",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else {
                        Toast.makeText(MoverLoginRegActivity.this, "Login Unsuccessful, Please try Again", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void RegisterMover(String email, String password) {
         if(TextUtils.isEmpty(email)) {
             Toast.makeText(MoverLoginRegActivity.this,"Please write Email...",Toast.LENGTH_SHORT).show();
         }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(MoverLoginRegActivity.this,"Please write Password...",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Mover Registration");
            loadingBar.setMessage("Please wait, while we register your data...");
            loadingBar.show();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onlineMoverID = mAuth.getCurrentUser().getUid();
                            moverDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Movers").child(onlineMoverID);
                            moverDatabaseRef.setValue(true);

                            Intent moverIntent = new Intent(MoverLoginRegActivity.this, MoverMapsActivity.class);
                            startActivity(moverIntent);



                            Toast.makeText(MoverLoginRegActivity.this, "Mover Register Successfully...",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();





                        }
                        else {
                            Toast.makeText(MoverLoginRegActivity.this, "Registration Unsuccessful, Please try Again", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
        }
    }
}
