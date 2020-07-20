package com.example.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Declaring Component
    EditText etName,etAge,etPhone;
    RadioGroup rgGender;
    RadioButton rbMale,rbFemale;
    Button btnRegister;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding OF Components

        etName=findViewById(R.id.etName);
        etAge=findViewById(R.id.etAge);
        etPhone=findViewById(R.id.etPhone);
        rgGender=findViewById(R.id.rgGender);
        rbFemale=findViewById(R.id.rbFemale);
        rbMale=findViewById(R.id.rbMale);
        btnRegister=findViewById(R.id.btnRegister);

        sp=getSharedPreferences("f1",  MODE_PRIVATE);
        String name=sp.getString("name","");
         String age=sp.getString("age","");
        String phone=sp.getString("phone","");




if(name.length()==0 && age.length()==0 && phone.length()==0){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name=etName.getText().toString();
                    String age=etAge.getText().toString();
                    String phone=etPhone.getText().toString();


                    if(name.length()==0)
                    {
                        etName.setError("Name Cannot Be Empty");
                        etName.requestFocus();
                        return;
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name",name);
                    editor.commit();




                    if(etAge.length()==0)
                    {
                        etAge.setError("Age Cannot be Be Empty");
                        etAge.requestFocus();
                        return;
                    }
                    editor.putString("age",age);
                    editor.commit();


                    if(phone.length()==0)
                    {

                        etPhone.setError("Phone Number Cannot be Empty");
                        etPhone.requestFocus();
                        return;
                    }
                    editor.putString("phone",phone);
                    editor.commit();

                    Intent i= new Intent(MainActivity.this,BmiData.class);
                    startActivity(i);
                    finish();



                }
            });


    }
else
{

    Intent i= new Intent(MainActivity.this,BmiData.class);
    startActivity(i);
    finish();


}
}
}
