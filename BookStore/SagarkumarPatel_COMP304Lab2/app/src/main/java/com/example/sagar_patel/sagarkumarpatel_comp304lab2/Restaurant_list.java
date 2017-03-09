package com.example.sagar_patel.sagarkumarpatel_comp304lab2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Restaurant_list extends AppCompatActivity {
    String[] Titles;
    String[] Description;
    ListView list;
    int[] images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Resources res =getResources();
        Intent intent = getIntent();
        final String cuisine = intent.getStringExtra("selected_cuisine");



                Titles = res.getStringArray(R.array.Title_Indian);
                Description = res.getStringArray(R.array.Desc_Indian);
                images = new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six};




        list = (ListView)findViewById(R.id.restaurants_list);
        listAdapter adapter = new listAdapter(this,Titles,images,Description);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final String selected_res = String.valueOf(parent.getItemAtPosition(position));
                Intent t = new Intent(Restaurant_list.this,Menu_list.class);
                t.putExtra("selected_res",selected_res);
                t.putExtra("selected_cuisine",cuisine);
                startActivity(t);
            }
        });
        //listAdapter adapter = new listAdapter()
    }
}

class listAdapter extends ArrayAdapter<String>
{
    private Context context;
    private int[] images;
    private String[] titlesArray;
    private String[] descArray;
    listAdapter(Context c,String[] titles,int imgs[],String[] desc) {
        super(c, R.layout.single_row_restaurant, R.id.res_title, titles);
        this.context = c;
        this.images = imgs;
        this.titlesArray = titles;
        this.descArray = desc;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =  inflater.inflate(R.layout.single_row_restaurant,parent,false);

        ImageView myImage=(ImageView) row.findViewById(R.id.res_img);
        TextView textv1 =(TextView)row.findViewById(R.id.res_title);
        TextView textv2 =(TextView)row.findViewById(R.id.res_desc);

        myImage.setImageResource(images[position]);
        textv1.setText(titlesArray[position]);
        textv2.setText(descArray[position]);
        return row;

    }

}
