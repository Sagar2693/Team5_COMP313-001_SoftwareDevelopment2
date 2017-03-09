package com.example.sagar_patel.sagarkumarpatel_comp304lab2;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Menu_list extends AppCompatActivity {
    String[] Dishes;
    ListView list;
    Button selected_item_but;
    String abc ="";
    TextView Directions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);


        Intent t = getIntent();
        String cuisine = t.getStringExtra("selected_cuisine");
        String res_name = t.getStringExtra("selected_res");
        Resources res = getResources();

            switch (res_name)
            {
                case "Scifi":
                    Dishes = res.getStringArray(R.array.Indian_Res1);

             //       Directions =(TextView)findViewById(R.id.directions);

                    final String latitude_ind1="43.785800";
                    final String longitude_ind1="-79.279320";
                    /*  Directions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent t1 = new Intent(Menu_list.this,GoogleMapsActivity.class);
                            t1.putExtra("Latitude",latitude_ind1);
                            t1.putExtra("Longitude", longitude_ind1);

                            startActivity(t1);
                        }
                    });*/

                    break;
                case "Horror":
                    Dishes = res.getStringArray(R.array.Indian_Res2);
                 //   Directions =(TextView)findViewById(R.id.directions);

                    final String latitude_nova="43.785800";
                    final String longitude_nova="-79.279320";

                   /* Directions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent t1 = new Intent(Menu_list.this,GoogleMapsActivity.class);
                            t1.putExtra("Latitude",latitude_nova);
                            t1.putExtra("Longitude", longitude_nova);

                            startActivity(t1);
                        }
                    });*/

                    break;
                case "Romance":
                    Dishes = res.getStringArray(R.array.Indian_Res3);
                  //  Directions =(TextView)findViewById(R.id.directions);

                    final String latitude_cesar="43.745399";
                    final String longitude_cesar="-79.295127";

                  /*  Directions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent t1 = new Intent(Menu_list.this,GoogleMapsActivity.class);
                            t1.putExtra("Latitude",latitude_cesar);
                            t1.putExtra("Longitude", longitude_cesar);

                            startActivity(t1);
                        }
                    });*/

                    break;
                case "Biography":
                    Dishes = res.getStringArray(R.array.Indian_Res4);
                    break;
                case "Drama":
                    Dishes = res.getStringArray(R.array.Indian_Res5);
                    break;
            }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, Dishes);
        list = (ListView) findViewById(R.id.item_list);
        list.setAdapter(adapter);

        selected_item_but = (Button)findViewById(R.id.selected_item_button);
        selected_item_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selected = list.getCheckedItemPositions();


                for(int i= (selected.size()-1) ; i>=0 ;i--) {

                    if (selected.valueAt(i)) {
                        String temp = list.getItemAtPosition(i).toString();

                        abc = abc + temp +"\n";
                        //.append("\n" + String.valueOf(list.getItemAtPosition(i)));
                    }
                }
                Intent t = new Intent(Menu_list.this,Payment.class);
                t.putExtra("items_selected",abc);
                startActivity(t);
            }
        });



    }
}
