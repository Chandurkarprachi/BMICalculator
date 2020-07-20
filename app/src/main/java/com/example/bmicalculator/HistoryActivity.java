package com.example.bmicalculator;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ListView lvData;
    ArrayList<String> s=new ArrayList<>();
    ArrayAdapter<String> ad;
    FirebaseDatabase database;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        setContentView(R.layout.activity_history);

        lvData=findViewById(R.id.lvData);
        database=FirebaseDatabase.getInstance("https://bmicalculator-69039.firebaseio.com/");
        myref=database.getReference();


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try
                {

                for (DataSnapshot d: dataSnapshot.getChildren())
                {
                    String data= (String) d.getValue();
                   // Student data=d.getValue(Student.class);
                    s.add(data);
                }
                ad= new ArrayAdapter<String>(HistoryActivity.this,android.R.layout.simple_list_item_1,s);

                lvData.setAdapter(ad);


            }
                catch (Exception e)
                {
                    Toast.makeText(HistoryActivity.this, ""+e, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
