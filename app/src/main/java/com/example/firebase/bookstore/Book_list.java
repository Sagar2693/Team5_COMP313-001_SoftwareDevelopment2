package com.example.firebase.bookstore;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Book_list extends AppCompatActivity{
    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;

    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Intent t = getIntent();
        String category = t.getStringExtra("category_selected");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        RecyclerView recyclerView;

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
                        Intent i = new Intent(Book_list.this,MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_viewCart:
                        Intent v = new Intent(Book_list.this,viewCart_activity.class);
                        startActivity(v);
                        break;

                    case R.id.nav_contact:
                        Intent c = new Intent(Book_list.this,contact_us_activity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });





        DatabaseReference BookRef = myRootRef.child("Books");



            int numberOfcol =3;

            recyclerView = (RecyclerView)findViewById(R.id.rv_view1);
            recyclerView.setHasFixedSize(true);
         GridLayoutManager gridLayoutManager = new GridLayoutManager(this,numberOfcol,GridLayoutManager.VERTICAL,true);
            recyclerView.setLayoutManager(gridLayoutManager);





            FirebaseRecyclerAdapter<book,bookListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<book, bookListViewHolder>(                  book.class,
                    R.layout.single_book_row,
                    bookListViewHolder.class,
                    BookRef.orderByChild("category").equalTo(category)

            ) {

                @Override
                protected void populateViewHolder(bookListViewHolder viewHolder, final book model, int position) {
                   final String bookId = getRef(position).getKey();

                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setAuthor(model.getAuthor());
                    viewHolder.setImage(getApplicationContext(),model.getImage());
                    viewHolder.setRating(model.getRating());

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // Log.v("author"," is .................." + bookId);
                            Intent i = new Intent(Book_list.this,description_book.class);
                            i.putExtra("Selected_book",bookId);
                            startActivity(i);

                        }
                    });


                }
            };

            recyclerView.setAdapter(firebaseRecyclerAdapter);




        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }



    }


