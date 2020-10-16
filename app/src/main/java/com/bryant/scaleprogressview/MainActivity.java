package com.bryant.scaleprogressview;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bryant.scaleprogresslibrary.ScaleProgressView;

public class MainActivity extends AppCompatActivity {

    ScaleProgressView scaleProgressView;
    Button button01,button02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] scalePart = new int[]{15,23,30,35};
        final int[] scaleColor = new int[]{R.color.color06CAAC,R.color.colorFF8F26,R.color.color06CAAC};

        final int[] scalePart2 = new int[]{5,6,7,8,9,10};
        final int[] scaleColor2 = new int[]{R.color.color06CAAC,R.color.colorFF8F26,R.color.color06CAAC,R.color.colorFF8F26,R.color.color06CAAC};

        scaleProgressView = findViewById(R.id.scaleProgressView);
        scaleProgressView.setScalePart(scalePart)
                .setScaleColor(scaleColor)
                .setClipPos(26)
                .setUnit("℃")
                .isSpace(true);

        button01 = findViewById(R.id.button01);
        button02 = findViewById(R.id.button02);

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleProgressView.setScalePart(scalePart)
                        .setScaleColor(scaleColor)
                        .setClipPos(26)
                        .setClipText("26℃")
                        .setUnit("℃")
                        .setScaleInsideSize(0)
                        .setScaleDeviationPos(0)
                        .setTextColor(R.color.colorPrimary)
                        .setClipColor(R.color.colorPrimary)
                        .isSpace(true).invalidate();
            }
        });
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleProgressView.setScalePart(scalePart2)
                        .setScaleColor(scaleColor2)
                        .setClipPos(8)
                        .setClipText("8/13日")
                        .setUnit("月")
                        .setScaleInsideSize(30)
                        .setScaleDeviationPos(13)
                        .setTextColor(R.color.colorFF8F26)
                        .setClipColor(R.color.colorFF8F26)
                        .isSpace(true)
                        .invalidate();
            }
        });
    }
}
