package com.example.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BmiResult extends AppCompatActivity {

    //declaration

    TextView tvResult,tvOne,tvTwo,tvThree,tvFour;
    Button btnShare,btnBack,btnSave;
    SharedPreferences sp;

    FirebaseDatabase database;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        setContentView(R.layout.activity_bmi_result);
        //Bindings
        //TextViews
        tvResult=findViewById(R.id.tvResult);
        tvOne=findViewById(R.id.tvOne);
        tvTwo=findViewById(R.id.tvTwo);
        tvThree=findViewById(R.id.tvThree);
        tvFour=findViewById(R.id.tvFour);

        //Buttons
        btnBack=findViewById(R.id.btnBack);
        btnSave=findViewById(R.id.btnSave);
        btnShare=findViewById(R.id.btnShare);

        //Database
        database=FirebaseDatabase.getInstance("https://bmicalculator-69039.firebaseio.com/");
        myref=database.getReference();

        sp=getSharedPreferences("f1",MODE_PRIVATE);
      final   String name =sp.getString("name","");
      final   String age= sp.getString( "age","");
     final    String phone= sp.getString("phone","");




        Intent i=getIntent();
        //to send Result
        final String result=i.getStringExtra("Result");

        //to send Bmi(int value)
        Bundle extras = getIntent().getExtras();
        final String Bmi = extras.getString("Bmi");

        tvResult.setText("Your BMI is : "+Bmi +"  And You Are " +result);

        //change text color
       // int length= tvResult.getText().length();
        //SpannableString ss=new SpannableString(result);
      //  BackgroundColorSpan yellow =new BackgroundColorSpan(Color.YELLOW);
      //  ss.setSpan(yellow,1,25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        if (result.equals("Obese")){
            Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
            tvFour.setBackgroundColor(Color.YELLOW);


        }
        if (result.equals("Normal")){
            //Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
            tvTwo.setBackgroundColor(Color.YELLOW);


        }
        if (result.equals("Underweight")){
            Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
            tvOne.setBackgroundColor(Color.YELLOW);


        }
        if (result.equals("Overweight")){
            Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
            tvThree.setBackgroundColor(Color.YELLOW);


        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(BmiResult.this,BmiData.class);
                startActivity(i1);
                finish();
            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              // String msg="Name : "+name;

               //        "\nAge : "+age+"\nPhone"+phone+"\nBMi : "+Bmi;

               // tvName.setText("welcome " +name);
                Intent i2=new Intent(Intent.ACTION_SEND);
                i2.setType("text/plain");
                i2.putExtra(Intent.EXTRA_TEXT,"Information"+"\nName :"+name+"\nAge :"+age+"\nPhone :"+phone+"\n"+tvResult.getText().toString());
                startActivity(i2);



            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar calendar=Calendar.getInstance();
                //String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(calendar.getTime());
                Toast.makeText(BmiResult.this, "Date  : "+formattedDate, Toast.LENGTH_SHORT).show();

                //Storing data which have to store in database In String
                String info="Date : "+formattedDate + "\n Your BMI : "+Bmi;

                //pass string value in myref of database
                myref.push().setValue(info);
                Toast.makeText(BmiResult.this, "Your Data is Saved !", Toast.LENGTH_SHORT).show();

            }
        });
    }//oncreate



}
