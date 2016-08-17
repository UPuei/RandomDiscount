package com.example.kevin.randomdiscount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button b;
    private TextView p;
    int discount[] = {89,79,69,59,49,1};
    int count[] = {0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button)findViewById(R.id.random);
        p = (TextView)findViewById(R.id.percent);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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
                int idx = (index-1<0) ? 0 : (index-1);
                count[idx]++;

                String s = discount[idx] + "";
                String culm = "";
                for(int i=0;i<discount.length;i++) {
                    culm += discount[i] + ":" + count[i] + "\n";
                }
                Log.d("print",culm);
                p.setText(s);
            }
        });

    }
}
