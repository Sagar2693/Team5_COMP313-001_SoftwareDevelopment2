package com.example.sagar_patel.sagarkumarpatel_comp304lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    Button enter_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        init();
    }

    public void init()
    {
        enter_button = (Button)findViewById(R.id.enter_button);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(MainScreen.this,Restaurant_list.class);
                startActivity(t);
            }
        });

    }
}
