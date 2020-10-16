# ScaleProgressView
分段多刻度进度条

![这是一张图片](https://github.com/YangsBryant/ScaleProgressView/blob/master/c1bb5-4ea6k.gif)

## 引入module
```java
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://www.jitpack.io' }
    }
}
```

```java
implementation 'com.github.YangsBryant:OverlappingCards:1.0.2'
```

## 主要代码
```java

public class MainActivity extends AppCompatActivity {

    ScaleProgressView scaleProgressView;
    Button button01,button02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] scalePart = new int[]{15,23,30,35};
        final int[] scaleColor = new int[]{R.color.color06CAAC,R.color.colorFF8F26,R.color.color06CAAC};

        final int[] scalePart2 = new int[]{50,80,100};
        final int[] scaleColor2 = new int[]{R.color.colorPrimary,R.color.colorAccent};

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
                        .setUnit("℃")
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
                        .setClipPos(70)
                        .setUnit("%")
                        .setTextColor(R.color.colorFF8F26)
                        .setClipColor(R.color.colorFF8F26)
                        .isSpace(true)
                        .invalidate();
            }
        });
    }
}
```

```java
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.bryant.scaleprogresslibrary.ScaleProgressView
        android:id="@+id/scaleProgressView"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        android:layout_margin="20dp"
        />

    <Button
        android:id="@+id/button01"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@+id/scaleProgressView"
        app:layout_constraintLeft_toLeftOf="parent"
        android:layout_marginLeft="50dp"
        android:text="数据集一"
     />

    <Button
        android:id="@+id/button02"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@+id/scaleProgressView"
        app:layout_constraintLeft_toRightOf="@+id/button01"
        android:layout_marginLeft="50dp"
        android:text="数据集二"
        />
</androidx.constraintlayout.widget.ConstraintLayout>
```

## ScaleProgressView属性大全
方法名 | 属性
--------- | -------------
setScalePart(int[] scalePart) | 设置刻度值
setScaleColor(int[] scaleColor) | 设置每段刻度条颜色
setUnit(String unit) | 设置刻度单位
setTextColor(int textColor) | 设置刻度文本颜色
setendsPadding(int endsPadding) | 设置两端文字的缩进边距
setScaleHeight(int scaleHeight) | 设置刻度尺高度
isSpace(boolean bl) | 是否显示间隔
setClipColor(int clipColor) | 设置卡子的颜色
setClipPos(int clipPos) | 设置卡子位置
isClip(boolean bl) | 是否显示卡子
setClipPaddingTop(int clipPaddingTop) | 顶部的距离

## 联系QQ：961606042
