package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] category = {"Action","Comedy","Drama","Fiction","Horror","Mystery","Romance"};
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, category);
        list = (ListView) findViewById(R.id.listViewCategory);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //  int f =  list.getSelectedItemPosition();

                String val =(String) adapterView.getItemAtPosition(position);
                Log.v("MainActivity"," is .................." + val);

                Intent t = new Intent(MainActivity.this,Book_list.class);

                t.putExtra("category_selected",val);
                startActivity(t);
            }
        });




    }
}
