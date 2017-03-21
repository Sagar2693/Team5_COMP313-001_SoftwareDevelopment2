package com.example.firebase.bookstore;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Book_list extends AppCompatActivity
{
    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Intent t = getIntent();
        String category = t.getStringExtra("category_selected");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        RecyclerView recyclerView;
   ;


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
                            Log.v("author"," is .................." + bookId);
                            Intent i = new Intent(Book_list.this,book_description.class);
                            i.putExtra("Selected_book",bookId);
                            startActivity(i);

                        }
                    });


                }
            };

            recyclerView.setAdapter(firebaseRecyclerAdapter);




        }



    }


