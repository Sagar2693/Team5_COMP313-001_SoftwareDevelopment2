package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class payment extends AppCompatActivity {

    TextView ordered_items;
    EditText credit_card_no, ccv,full_name,address;
    Button Pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        final String items = intent.getStringExtra("items_selected");
        ordered_items = (TextView)findViewById(R.id.cus_price);
        ordered_items.setText("Total Amount To pay : $" +items);

        Pay = (Button)findViewById(R.id.cus_Pay);
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credit_card_no=(EditText)findViewById(R.id.cus_Creditcard);
                ccv =(EditText)findViewById(R.id.cus_Ccv);
                full_name=(EditText)findViewById(R.id.cus_Name);
                address=(EditText)findViewById(R.id.cus_Address);
                String card_no = credit_card_no.getText().toString().trim();
                String fname =full_name.getText().toString().trim();
                String cv = ccv.getText().toString().trim();
                String add = address.getText().toString().trim();
                if(fname.isEmpty())
                {
                    full_name.setError("Enter Full name");

                }
                else if(add.isEmpty())
                {
                    address.setError("Enter Full Address");

                }
                else if(card_no.isEmpty()||card_no.length()!= 10)
                {

                    credit_card_no.setError("Enter 10 digit card number");
                }


                else if(cv.isEmpty() || cv.length()!= 3)
                {
                    ccv.setError("Security code should be 3 digit number");

                }

                else {

                    Toast.makeText(payment.this,
                            "Payment Successful\n\nAmount Payed : $" + items, Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }
}
