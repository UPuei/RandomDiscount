package com.example.kevin.randomdiscount;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button b;
    private TextView p, value_view, origin_cost;
    private EditText e;
    int discount[] = {89,79,69,59,49,1};
    int count[] = {0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button)findViewById(R.id.random);
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
                    Log.d("print", "in");
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
                }
            }
        });

    }
}
