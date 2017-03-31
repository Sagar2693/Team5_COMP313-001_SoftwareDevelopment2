package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;

    String[] category = {"Action","Comedy","Drama","Fiction","Horror","Mystery","Romance"};
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);

        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView =(NavigationView)findViewById(R.id.navgation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id  = item.getItemId();
                switch (id) {
                    case R.id.nav_signOut:

                       // userAuth.signOut();

                        break;
                    case R.id.nav_home:

                        mDrawerlayout.closeDrawers();
                        break;
                    case R.id.nav_viewCart:
                        Intent v = new Intent(MainActivity.this,viewCart_activity.class);
                        startActivity(v);
                        break;

                    case R.id.nav_contact:
                        Intent c = new Intent(MainActivity.this,contact_us_activity.class);
                        startActivity(c);
                        break;


                }
                return false;
            }
        });



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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }

}
