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
    Button button01,button02,button03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] scalePart = new int[]{15,23,30,35};
        final int[] scaleColor = new int[]{R.color.color06CAAC,R.color.colorFF8F26,R.color.color06CAAC};

        final int[] scalePart2 = new int[]{8,10,11,1,2,3};
        final int[] scaleColor2 = new int[]{R.color.colorFF8F26,R.color.color06CAAC,R.color.color06CAAC,R.color.colorFF8F26,R.color.color06CAAC};

        final int[] scalePart3 = new int[]{27,28,30,1,2};
        final int[] scaleColor3 = new int[]{R.color.color06CAAC,R.color.color06CAAC,R.color.color06CAAC,R.color.colorFF8F26};

        scaleProgressView = findViewById(R.id.scaleProgressView);
        scaleProgressView.setScalePart(scalePart)
                .setScaleColor(scaleColor)
                .setClipPos(26)
                .setUnit("℃")
                .isSpace(true);

        button01 = findViewById(R.id.button01);
        button02 = findViewById(R.id.button02);
        button03 = findViewById(R.id.button03);

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
                        .isSpace(true)
                        .setTimeMode(-1)
                        .invalidate();
            }
        });
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleProgressView.setScalePart(scalePart2)
                        .setScaleColor(scaleColor2)
                        .setClipPos(10)
                        .setClipText("10/04日")
                        .setUnit("月")
                        .setScaleInsideSize(30)
                        .setScaleDeviationPos(4)
                        .setTextColor(R.color.colorPrimary)
                        .setClipColor(R.color.colorPrimary)
                        .isSpace(true)
                        .setTimeMode(1)
                        .invalidate();
            }
        });
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleProgressView.setScalePart(scalePart3)
                        .setScaleColor(scaleColor3)
                        .setClipPos(30)
                        .setClipText("XX月30号")
                        .setUnit("号")
                        .setTextColor(R.color.colorPrimary)
                        .setClipColor(R.color.colorPrimary)
                        .isSpace(true)
                        .setTimeMode(0)
                        .invalidate();
            }
        });
    }
}
