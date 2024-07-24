package com.example.medassist;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import io.noties.markwon.Markwon;



public class MainActivity extends AppCompatActivity {
    TextView tv1,nm,ag;
    EditText prompt,since_days;
    Button gt;

    String Name,Age,Height;

    Uri uri;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Markwon markwon = Markwon.create(this);


        tv1=findViewById(R.id.resb);
        tv1.setMovementMethod(new ScrollingMovementMethod());
        prompt=findViewById(R.id.pmbx);
        nm=findViewById(R.id.name_f);
        ag=findViewById(R.id.age_fi);
        since_days=findViewById(R.id.sin_dys);

        Bundle extras=getIntent().getExtras();
       this.Name=extras.getString("Name");
        this.Age=extras.getString("Age");
        this.Height=extras.getString("Height");

        nm.setText("Name : "+Name);
        ag.setText("Age : "+Age);


        gt=findViewById(R.id.gtb);





        gt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("Getting the data...");



                GenerativeModel gm = new GenerativeModel( "gemini-pro", BuildConfig.apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        String sd= prompt.getText().toString();
        String dur_disease=since_days.getText().toString();
        String query="My age is "+Age+" What are these symptoms "+sd+" indicates respective to my age since I was suffering from"+dur_disease+" days. Can you suggest me the home remedies for this cause. Suggest some doctors to consult for this cause. Suggest some general medicines for the above symptoms. Give the answer in simple english headings with numbers and subheadings";
        Content content = new Content.Builder()
                .addText(query)
                .build();
        Executor executor = Runnable::run;
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                //tv1.setTypeface(tv1.getTypeface(), Typeface.ITALIC);


                markwon.setMarkdown(tv1, resultText);

                //tv1.setText(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, executor);
            }
        });

        /*GenerativeModel gm = new GenerativeModel( "gemini-pro", BuildConfig.apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content conteDent = new Content.Builder()
                .addText("What are the remedies and precautions to be taken for this "+sd+" and suggest" +
                        " which doctor should I consult" +
                        "Give me the data in bullet points ")
                .build();
        Executor executor = Runnable::run;
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                tv1.setText(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, executor);
*/


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}