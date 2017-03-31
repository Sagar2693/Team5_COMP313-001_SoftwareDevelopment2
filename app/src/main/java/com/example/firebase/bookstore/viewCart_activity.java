package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class viewCart_activity extends AppCompatActivity {

    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_activity);

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
                        Intent i = new Intent(viewCart_activity.this,MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_viewCart:
                        mDrawerlayout.closeDrawers();
                        break;

                    case R.id.nav_contact:
                        Intent c = new Intent(viewCart_activity.this,MainActivity.class);
                        startActivity(c);
                        break;


                }
                return false;
            }
        });


        //======================== Code start here =====================














        //======================== Code end here =====================
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }


}



