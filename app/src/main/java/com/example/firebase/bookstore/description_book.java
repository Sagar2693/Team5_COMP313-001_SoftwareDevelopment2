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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class description_book extends AppCompatActivity {

    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;

    ImageView imageView ;
    RatingBar ratingBar;
    TextView txt_AuthorName;
    TextView txt_price;
    TextView txt_BookTitle;
    TextView txt_multiLine;
    TextView txt_category;
    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_book);
        Intent t = getIntent();
        String bookID = t.getStringExtra("Selected_book");

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
                        Intent i = new Intent(description_book.this,MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_viewCart:
                        Intent v = new Intent(description_book.this,viewCart_activity.class);
                        startActivity(v);
                        break;

                    case R.id.nav_contact:
                        Intent c = new Intent(description_book.this,contact_us_activity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
            //========================Code start here=====================================

        DatabaseReference selectedBookRef = myRootRef.child("Books").child(bookID);

        imageView =(ImageView) findViewById(R.id.imgView_book);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar_1);
        txt_AuthorName = (TextView)findViewById(R.id.txt_authorName);
        txt_BookTitle = (TextView)findViewById(R.id.txt_bookTitle);
        txt_price = (TextView)findViewById(R.id.txt_price);
        txt_multiLine = (TextView)findViewById(R.id.txt_description);
        txt_category = (TextView)findViewById(R.id.txt_category);


        selectedBookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                book selectedBook = dataSnapshot.getValue(book.class);
                //Log.v("author"," is .................." + selectedBook.getAuthor());

                Picasso.with(getApplicationContext()).load(selectedBook.getImage()).into(imageView);
                ratingBar.setRating(selectedBook.getRating());
                txt_AuthorName.setText("Author: "+selectedBook.getAuthor());
                txt_price.setText("Price: $" +Double.toString(selectedBook.getPrice()));
                txt_BookTitle.setText("Title: "+selectedBook.getTitle());
                txt_multiLine.setText(selectedBook.getDesc());
                txt_category.setText("Category: "+selectedBook.getCategory());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //========================Code end here=====================================

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }


}
