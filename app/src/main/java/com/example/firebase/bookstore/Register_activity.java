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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_activity extends AppCompatActivity {

    EditText nameField;
    EditText emailField;
    EditText passField;
    Button btn_register;
    TextView alreadyMember;
    FirebaseAuth userAuth;
    DatabaseReference myRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        userAuth = FirebaseAuth.getInstance();
        userRef =myRoot.child("Users"); //creating a new node to store user information

        progress = new ProgressDialog(this);

        nameField = (EditText)findViewById(R.id.editText_name);
        emailField = (EditText)findViewById(R.id.editText_email);
        passField = (EditText)findViewById(R.id.editText_password);
        alreadyMember=(TextView)findViewById(R.id.txt_alreadyMember);
        btn_register = (Button)findViewById(R.id.btn_register);

        alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Login = new Intent(Register_activity.this, login_activity.class);
                startActivity(Login);

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startRegister();
            }

        });

    }

    private void startRegister() {

        final String name = nameField.getText().toString().trim();
        final String email = emailField.getText().toString().trim();
        final String pass = passField.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){

            progress.setMessage("Registering User");
            progress.show();//showing message

            userAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        // checkUserExist();

                        String user_id = userAuth.getCurrentUser().getUid();

                        DatabaseReference currentuser_id = userRef.child(user_id); //creating a child for user in root reference

                        currentuser_id.child("name").setValue(name);
                        currentuser_id.child("email").setValue(email);

                        progress.dismiss();//when it is done

                        Intent main = new Intent(Register_activity.this, MainActivity.class);
                        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(main);
                    } else {
                        progress.dismiss();
                        Toast.makeText(Register_activity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                    }

                }
            });




        }

}
}
