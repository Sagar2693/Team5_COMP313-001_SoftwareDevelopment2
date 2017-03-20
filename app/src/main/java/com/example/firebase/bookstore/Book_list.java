package com.example.firebase.bookstore;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Book_list extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Intent t = getIntent();
        String category = t.getStringExtra("category_selected");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        RecyclerView recyclerView;
   ;

        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference BookRef = myRootRef.child("Books");



            int numberOfcol =3;
        int nu;

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
                protected void populateViewHolder(bookListViewHolder viewHolder, book model, int position) {

                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setAuthor(model.getAuthor());
                    viewHolder.setImage(getApplicationContext(),model.getImage());
                    viewHolder.setRating(model.getRating());


                }
            };

            recyclerView.setAdapter(firebaseRecyclerAdapter);




        }



    }


