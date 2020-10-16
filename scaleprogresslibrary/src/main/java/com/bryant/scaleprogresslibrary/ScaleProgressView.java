package com.bryant.scaleprogresslibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ScaleProgressView extends View {

    private Context context;
    private int clipPaddingTop = 35;//顶部的距离
    private int scalePaddingTop = clipPaddingTop+25;//刻度条距离顶部的距离
    private int scaleWidth;//每个刻度的宽度
    private int scaleHeight = 40;//刻度条的高度
    private int[] scalePart = new int[]{50,80,100};//每段刻度值的宽度
    private int clipPos = 70; //卡子的位置
    private String unit = "%";//刻度单位
    private int textColor = R.color.colorPrimary;
    private int[] scaleColor = new int[]{R.color.colorPrimary,R.color.colorAccent,R.color.colorPrimary};//每个刻度的颜色
    private int clipColor = R.color.colorPrimary;
    private int scaleMax;//刻度的总宽度和View的宽度一致
    private int endsPadding = dip2px(15);//两端文字的缩进边距
    private int spaceWidth = dip2px(1); //间隔宽度
    private boolean isClip = true;//是否显示卡子
    private boolean isSpace = false; //是否开启间隔

    public ScaleProgressView(Context context) {
        this(context,null);
        this.context = context;
    }

    public ScaleProgressView(Context context, AttributeSet attrs){
        super(context,attrs,0);
        this.context = context;
    }

    public ScaleProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //设置刻度值
    public ScaleProgressView setScalePart(int[] scalePart){
        this.scalePart = scalePart;
        return this;
    }
    //设置每段刻度条颜色
    public ScaleProgressView setScaleColor(int[] scaleColor){
        this.scaleColor = scaleColor;
        return this;
    }
    //设置刻度单位
    public ScaleProgressView setUnit(String unit){
        this.unit = unit;
        return this;
    }
    //设置刻度文本颜色
    public ScaleProgressView setTextColor(int textColor){
        this.textColor = textColor;
        return this;
    }
    //设置两端文字的缩进边距
    public ScaleProgressView setendsPadding(int endsPadding){
        this.endsPadding = dip2px(endsPadding);
        return this;
    }
    //设置刻度尺高度
    public ScaleProgressView setScaleHeight(int scaleHeight){
        this.scaleHeight = scaleHeight;
        return this;
    }
    //是否显示间隔
    public ScaleProgressView isSpace(boolean bl){
        this.isSpace = bl;
        return this;
    }
    //设置卡子的颜色
    public ScaleProgressView setClipColor(int clipColor){
        this.clipColor = clipColor;
        return this;
    }
    //设置卡子位置
    public ScaleProgressView setClipPos(int clipPos){
        this.clipPos = clipPos;
        return this;
    }
    //是否显示卡子
    public ScaleProgressView isClip(boolean bl){
        this.isClip = bl;
        return this;
    }
    //顶部的距离
    public ScaleProgressView setClipPaddingTop(int clipPaddingTop){
        this.clipPaddingTop = clipPaddingTop+35;
        return this;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        scaleMax = canvas.getWidth();
        scaleWidth = scaleMax / (scalePart[scalePart.length - 1] - scalePart[0]);
        printProgress(canvas);
        printScaleText(canvas);
        printClip(canvas);
    }

    private void printClip(Canvas canvas){
        if(isClip) {
            int mPos = 0;
            for (int i = 0; i < scalePart.length - 1; i++) {
                if (clipPos >= scalePart[i] && clipPos < scalePart[i + 1]) {
                    mPos = i;
                }
            }
            int startXTo = (clipPos - scalePart[0]) * scaleWidth + spaceWidth * mPos;

            Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(4);
            mPaint.setTextSize(30);
            canvas.drawText(clipPos + unit, startXTo - getTextWidth(mPaint, clipPos + unit) / 2,
                    25, mPaint);

            Paint clipPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            clipPaint.setColor(context.getResources().getColor(clipColor));
            clipPaint.setStyle(Paint.Style.FILL);
            clipPaint.setStrokeWidth(4);
            clipPaint.setTextSize(30);

            Path path = new Path();
            path.moveTo(startXTo, clipPaddingTop);
            path.lineTo(startXTo + 15, clipPaddingTop);
            path.lineTo(startXTo + 15, clipPaddingTop + 30);
            path.lineTo(startXTo + 7.5f, clipPaddingTop + 35);
            path.lineTo(startXTo, clipPaddingTop + 30);
            canvas.drawPath(path, clipPaint);
        }
    }

    private void printScaleText(Canvas canvas){
        for (int i=0;i<scalePart.length;i++){
            Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(context.getResources().getColor(textColor));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(4);
            mPaint.setTextSize(30);
            int paddingL = 0;
            if(i == 0){
                paddingL = endsPadding;
            }else if(i == scalePart.length-1){
                paddingL = -endsPadding;
            }
            canvas.drawText(scalePart[i]+unit,paddingL+getLengthEach(i) - getTextWidth(mPaint,scalePart[i]+unit)/2,
                    scaleHeight*2+scalePaddingTop, mPaint);
        }
    }

    private void printProgress(Canvas canvas){
        for (int i=0;i<scalePart.length-1;i++){
            Paint mPaint = new Paint();
            mPaint.setColor(context.getResources().getColor(scaleColor[i]));
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);

            float[] radii = new float[]{0f,0f,0f,0f,0f,0f,0f,0f};
            if(i == 0){
                radii = new float[]{22f,22f,0f,0f,0f,0f,22f,22f};
            }else if(i == scalePart.length-2){
                radii = new float[]{0f,0f,22f,22f,22f,22f,0f,0f};
            }
            int wSpace = 0;
            if(isSpace && i!=0){
                wSpace = spaceWidth;
            }
            Path path = new Path();
            path.addRoundRect(new RectF(wSpace+getLengthEach(i), scalePaddingTop, getLengthEach(i+1), scaleHeight+scalePaddingTop), radii, Path.Direction.CW);
            canvas.drawPath(path,mPaint);
        }
    }

    private int getLengthEach(int endIndex){
        int startLength = scalePart[0];
        int endLength = scalePart[endIndex];
        return (endLength-startLength) * scaleWidth;
    }

    private int getTextWidth(Paint paint, String text) {
        return (int) paint.measureText(text);
    }

    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
