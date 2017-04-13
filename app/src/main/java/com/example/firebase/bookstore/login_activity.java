package com.example.firebase.bookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_activity extends AppCompatActivity {

    EditText login_email;
    EditText login_Pass;
    Button btn_login;
    TextView forgotPass;
    ProgressDialog progress;
    TextView create_account;


    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");

    FirebaseAuth userAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        login_email = (EditText)findViewById(R.id.editText_email_login);
        login_Pass =(EditText)findViewById(R.id.editText_pass_login);
        forgotPass=(TextView)findViewById(R.id.forgotpass_txt);
        btn_login = (Button)findViewById(R.id.btn_login);
        create_account = (TextView)findViewById(R.id.txtView_create);
        progress = new ProgressDialog(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checklogin();

            }
        });

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrLogin = new Intent(login_activity.this,Register_activity.class);
                registrLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registrLogin);

            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetActivity = new Intent(login_activity.this,resetpassword_activity.class);
                startActivity(resetActivity);

            }
        });

    }


  private void checklogin() {

        String email = login_email.getText().toString().trim();
        String pass = login_Pass.getText().toString().trim();

        if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(pass)){

            progress.setMessage("Loging in User");
            progress.show();

            userAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        progress.dismiss();

                        checkIfUserExist();

                    }
                    else
                    {
                        progress.dismiss();
                        Toast.makeText(login_activity.this, "Login Error", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }

    }

    private void checkIfUserExist() {

        final String user_id = userAuth.getCurrentUser().getUid();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){

                    Intent main_activity = new Intent(login_activity.this,MainActivity.class);
                    main_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main_activity);

                }else
                {
                    Toast.makeText(login_activity.this, "You need to setup your account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
