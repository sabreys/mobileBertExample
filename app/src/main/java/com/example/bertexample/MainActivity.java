package com.example.bertexample;

import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText textinput;
    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textinput = findViewById(R.id.metininput);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(v -> {
            textView.setText(sentimentPredict(textinput.getText().toString()));
        });

    }

    String sentimentPredict(String data) {

        BertNLClassifier classifier = null;
        try {

            AssetManager am = getAssets();
            InputStream is = am.open("model.tflite");

            try {
                File file = new File(getCacheDir(), "cacheFileAppeal.srl");
                try (OutputStream output = new FileOutputStream(file)) {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = is.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }

                    output.flush();

                    classifier = BertNLClassifier.createFromFile(file);

                    List<Category> results = classifier.classify(data);


                    return results.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {


                is.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }


}