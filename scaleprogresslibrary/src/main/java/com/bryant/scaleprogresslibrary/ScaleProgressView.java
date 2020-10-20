package com.bryant.scaleprogresslibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class ScaleProgressView extends View {

    private Context context;
    private int clipPaddingTop = 35;//顶部的距离
    private int scalePaddingTop = clipPaddingTop+25;//刻度条距离顶部的距离
    private int scaleWidth;//每个刻度的宽度
    private int scaleInsideWidth;//每个刻度里面的宽度
    private int scaleInsideSize = 0;//每个刻度里面的段数
    private int scaleHeight = 40;//刻度条的高度
    private int[] scalePart = new int[]{50,80,100};//每段刻度值的宽度
    private String clipText="26℃";//卡子的文本
    private int clipPos = 70; //卡子在分段的位置
    private int scaleDeviationPos = 0;//卡子在当前分段的偏移位置
    private String unit = "%";//刻度单位
    private int textColor = R.color.colorPrimary;
    private int[] scaleColor = new int[]{R.color.colorPrimary,R.color.colorAccent,R.color.colorPrimary};//每个刻度的颜色
    private int clipColor = R.color.colorPrimary;
    private int scaleMax;//刻度的总宽度和View的宽度一致
    private int endsPadding = dip2px(15);//两端文字的缩进边距
    private int spaceWidth = dip2px(1); //间隔宽度
    private boolean isClip = true;//是否显示卡子
    private boolean isSpace = false; //是否开启间隔
    private int timeMode = -1; //时间模式 0：日期 1：月份 -1:不开启

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
    //设置卡子所在分段里面的段数
    public ScaleProgressView setScaleInsideSize(int size){
        this.scaleInsideSize = size;
        return this;
    }
    //设置卡子所在分段里的偏移位置
    public ScaleProgressView setScaleDeviationPos(int size){
        this.scaleDeviationPos = size;
        return this;
    }
    //设置卡子在分段位置
    public ScaleProgressView setClipPos(int clipPos){
        this.clipPos = clipPos;
        return this;
    }
    //设置卡子的文本
    public ScaleProgressView setClipText(String clipText){
        this.clipText = clipText;
        return this;
    }
    //设置卡子的颜色
    public ScaleProgressView setClipColor(int clipColor){
        this.clipColor = clipColor;
        return this;
    }
    //是否显示卡子
    public ScaleProgressView isClip(boolean bl){
        this.isClip = bl;
        return this;
    }
    //是否开启日期模式
    public ScaleProgressView setTimeMode(int timeMode){
        this.timeMode = timeMode;
        return this;
    }
    //顶部的距离
    public ScaleProgressView setClipPaddingTop(int clipPaddingTop){
        this.clipPaddingTop = clipPaddingTop+35;
        return this;
    }

    @SuppressLint({"DrawAllocation", "CanvasSize"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        scaleMax = canvas.getWidth();
        switch (timeMode){
            case -1:
                scaleWidth = scaleMax / (getMax(scalePart) - getMin(scalePart));
                break;
            case 0:
                scaleWidth = scaleMax / (getCurrentMonthLastDay() - scalePart[0] + scalePart[scalePart.length-1]);
                break;
            case 1:
                scaleWidth = scaleMax / (12 - scalePart[0] + scalePart[scalePart.length-1]);
                break;
        }
        if(scaleInsideSize>0) {
            scaleInsideWidth = scaleWidth / scaleInsideSize;
        }else{
            scaleInsideWidth = 0;
        }
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
            float startXTo = (clipPos - scalePart[0]) * scaleWidth + spaceWidth * mPos - 7.5f + + scaleInsideWidth * scaleDeviationPos;

            Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(context.getResources().getColor(clipColor));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(4);
            mPaint.setTextSize(30);
            canvas.drawText(clipText, startXTo - getTextWidth(mPaint, clipText) / 2,
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
        if(timeMode<0) {
            int startLength = scalePart[0];
            int endLength = scalePart[endIndex];
            return (endLength - startLength) * scaleWidth;
        }else{
            int total;
            if(timeMode == 0){
                total = getCurrentMonthLastDay();
            }else{
                total = 12;
            }
            int startLength = scalePart[0];
            int endLength = scalePart[endIndex];
            int size;
            int index = 0;
            for(int i=0;i<scalePart.length;i++){
                if(scalePart[i] == getMax(scalePart)){
                    index = i;
                }
            }
            if(endIndex > index && index>0) {
                size = (total - startLength) + endLength;
                return size * scaleWidth;
            }
            return (endLength - startLength) * scaleWidth;
        }
    }

    private int getTextWidth(Paint paint, String text) {
        return (int) paint.measureText(text);
    }

    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public int getMax(int[] array) {
        int Max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Max < array[i]) {
                Max = array[i];
            }
        }
        return Max;
    }

    public int getMin(int[] array) {
        int Max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Max > array[i]) {
                Max = array[i];
            }
        }
        return Max;
    }

    public int getCurrentMonthLastDay(){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
