package com.example.medassist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.checkerframework.checker.units.qual.Length;

public class MainActivity2 extends AppCompatActivity {
    NumberPicker numberPicker;
    EditText name,height;
    TextView age;

    String sl_age,sl_name,sl_height; //sl : selected
    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        name=findViewById(R.id.namef);      //f : field
        height=findViewById(R.id.hgtf);     //hgt : height
        age=findViewById(R.id.age_f);
        numberPicker=findViewById(R.id.pick_age);


        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                age.setText(String.format("Age "+newVal));
                sl_age=Integer.toString(newVal);

            }
        });
        btn=findViewById(R.id.bt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sl_name=name.getText().toString();
                sl_height=height.getText().toString();

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("Age",sl_age);
                i.putExtra("Name",sl_name);
                i.putExtra("Height",sl_height);

                startActivity(i);


            }
        });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}