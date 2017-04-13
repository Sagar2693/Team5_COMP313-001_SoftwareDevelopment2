package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpassword_activity extends AppCompatActivity {

    EditText email;
    Button send;
    String userEmail;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword_activity);

        email=(EditText)findViewById(R.id.txt_resetEmail);
        send=(Button)findViewById(R.id.btn_reset);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = email.getText().toString().trim();
                resetPassword();
            }
        });




    }

    private void resetPassword() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = userEmail;

        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent loginActivity = new Intent(resetpassword_activity.this,login_activity.class);
                startActivity(loginActivity);
                Toast.makeText(resetpassword_activity.this, "Check you email for the instruction to reset your password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
