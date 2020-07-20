package com.example.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BmiData extends AppCompatActivity {
    //Declarations
    TextView tvHeight, tvFeet,tvInch,tvWeight;
    Spinner spHeight,spInch;
    Button btnCalculate ,btnHistory;
    EditText etWeight;
    LinearLayout snackid;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bmi_data);

        //Binding

        tvHeight=findViewById(R.id.tvHeight);
        tvFeet=findViewById(R.id.tvFeet);
        tvInch=findViewById(R.id.tvInch);
        tvWeight=findViewById(R.id.tvWeight);
        etWeight=findViewById(R.id.etWeight);

        snackid=findViewById(R.id.snackid);


        spHeight=findViewById(R.id.spHeight);
        spInch=findViewById(R.id.spInch);

        btnCalculate=findViewById(R.id.btnCalculate);
        btnHistory=findViewById(R.id.btnHistory);

        //method for text to speech;
        textToSpeech = new TextToSpeech(BmiData.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
            if(i!=TextToSpeech.ERROR)
            {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
            }
        });


        //deciamal format is use of round off decimal numbers
        final DecimalFormat df=new DecimalFormat("##.##");



        final ArrayList<Integer>feet= new ArrayList<>();
        feet.add(2);
        feet.add(3);
        feet.add(4);
        feet.add(5);
        feet.add(6);
        feet.add(7);
        feet.add(8);
        feet.add(9);
        feet.add(10);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,feet);
        spHeight.setAdapter(arrayAdapter);


        final ArrayList<Double>inch=new ArrayList<>();
        inch.add(0.1);
        inch.add(0.2);
        inch.add(0.3);
        inch.add(0.4);
        inch.add(0.5);
        inch.add(0.6);
        inch.add(0.7);
        inch.add(0.8);
        inch.add(0.9);
        inch.add(0.10);
        inch.add(0.11);
        ArrayAdapter arrayAdapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,inch);
        spInch.setAdapter(arrayAdapter1);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etWeight.length()==0)
                {
                   etWeight.setError("Please Enter Weight");
                  // Snackbar snackbar=Snackbar.make(snackid,"Enter weight properly",Snackbar.LENGTH_LONG);
                }

                else {
                    int f=spHeight.getSelectedItemPosition();
                    int finalfeet=feet.get(f);
                    double feetinmeters=finalfeet/3.281;


                    int i= spInch.getSelectedItemPosition();
                    double finalinch=inch.get(i);
                    double inchinmeters=finalinch/39.37;

                    double height= (feetinmeters + inchinmeters);

                    float weight= Float.parseFloat(etWeight.getText().toString());

                    double bmi = weight / (height * height);


                  //  Toast.makeText(BmiData.this,  "Height : "+height +"Weight : "+weight+" BMI is: "+bmi, Toast.LENGTH_LONG).show();
                     String result="";
                    if(bmi<18.5)
                    {
                        result="Underweight";

                    }
                    if(bmi>=18.5 && bmi <=25)
                    {
                        result= "Normal";
                    }
                    if(bmi>25 &&bmi<=30)
                    {
                        result="Overweight";
                    }
                    if(bmi>30)
                    {
                        result="Obese";
                    }


                    //TextTo Speech
                    String ttsp = "Your BMI is "+bmi+"And You Are "+result;
                    textToSpeech.speak(ttsp,TextToSpeech.QUEUE_FLUSH,null);

                    //Intent
                    Intent intent= new Intent(BmiData.this,BmiResult.class);
                    intent.putExtra("Result",result);


                    Bundle extras = new Bundle();
                    extras.putString("Bmi",df.format(bmi)+"" );
                    intent.putExtras(extras);

                    startActivity(intent);

                }

                }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BmiData.this,HistoryActivity.class);
                startActivity(i);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item1)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("About :")
                    .setMessage("This is BMI Calculator App \n " +
                            "Made By Ms.Prachi \n " +
                            "With Guidence Of\n" +
                            " Kamal sir !");
            final AlertDialog a=builder.create();
            a.show();

        }



        if(item.getItemId()==R.id.item2)
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://www.who.int/"));
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Back Again To Exit.", Toast.LENGTH_SHORT).show();
    }
}
