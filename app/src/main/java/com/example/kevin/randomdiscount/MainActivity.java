package com.example.kevin.randomdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button b;
    private TextView p, value_view, origin_cost, record_view;
    private EditText e;
    int discount[] = {89,79,69,59,49,1};
    int count[] = {0,0,0,0,0,0};
    String output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String record = readFromFile(this);
        record_view = (TextView)findViewById(R.id.record);
        if (!record.equals("")) {
            record_view.setText(record);
            String[] data = record.split(",");
            for (int i = 0; i < data.length; i++) {
                try {
                    count[i] = Integer.parseInt(data[i]);
                } catch (NumberFormatException nfe) {
                    //NOTE: write something here if you need to recover from formatting errors
                };
                Log.d("print", count[i]+"\n");
            }
        }

        b = (Button) findViewById(R.id.random);
        e = (EditText)findViewById(R.id.origin_price);
        p = (TextView)findViewById(R.id.percent);
        value_view = (TextView)findViewById(R.id.value);
        origin_cost = (TextView)findViewById(R.id.origin_cost);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!e.getText().toString().equals("")) {
                    int total = 0;
                    for (int i : discount) {
                        total += i;
                    }
                    int index = 0;
                    int sum = 0;
                    Random r = new Random();
                    int ran = r.nextInt(total);
                    while (sum <= ran) {
                        sum += discount[index++];
                    }
                    int idx = (index - 1 < 0) ? 0 : (index - 1);
                    count[idx]++;

                    String s = discount[idx] + "折";
                    String culm = "";
                    for (int i = 0; i < discount.length; i++) {
                        culm += discount[i] + ":" + count[i] + "\n";
                    }
                    //Log.d("print", "in");
                    p.setText(s);
                    origin_cost.setText(e.getText() + "元");
                    origin_cost.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    int value;
                    if (discount[idx] != 1) {
                        value = Integer.parseInt(e.getText().toString()) * discount[idx] / 100;
                    } else {
                        value = 1;
                    }
                    value_view.setText(value + "元");
                    e.setText("");
                    output = "";
                    for (int i = 0; i < count.length; i++) {
                        output += count[i];
                        if(i!=count.length-1) {
                            output += ",";
                        }
                    }
                    record_view.setText(output);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("print", output);
        writeToFile(output, this);
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("record", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("record");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
