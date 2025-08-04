package com.example.java_250731;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageHelper;

public class MainGame extends AppCompatActivity
{
    private boolean IsRunnning = true;

    private ImageView imageView;
    private final int[] imageIds = { R.drawable.guu, R.drawable.choki, R.drawable.paa };
    private int currentIndex = 0;
    private Button showImageButton;

    private Button guu;
    private Button choki;
    private Button paa;
    private Button restart;
    private ImageView myImageView;

    private TextView hantei;

    public void showNextImage(ImageView imageView) {

        //(currentIndex + 1) % imageIds.length;
        currentIndex++;

        imageView.setImageResource(imageIds[(currentIndex + 1) % imageIds.length]);

        imageView.setVisibility(ImageView.VISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        myImageView = findViewById(R.id.viewpicture);

        startImage(myImageView);

        guu = findViewById(R.id.guu_button);
        choki = findViewById(R.id.choki_button);
        paa = findViewById(R.id.paa_button);
        restart = findViewById(R.id.restart_button);
        hantei = findViewById(R.id.hantei_text);

        //下にじゃんけんゲームの処理を書く

        guu.setOnClickListener(view ->{
            StopImage();
            int result = (currentIndex + 1) % imageIds.length;
            if (imageIds[result] == R.drawable.guu)
            {
                hantei.setText("drow");
            } else if (imageIds[result] == R.drawable.choki) {
                hantei.setText("win");
            } else {
                hantei.setText("lose");
            }
            hantei.setVisibility(View.VISIBLE);
        });

        choki.setOnClickListener(view ->{
            StopImage();
            int result = (currentIndex + 1) % imageIds.length;
            if (imageIds[result] == R.drawable.guu)
            {
                hantei.setText("lose");
            } else if (imageIds[result] == R.drawable.choki) {
                hantei.setText("drow");
            } else {
                hantei.setText("win");
            }
            hantei.setVisibility(View.VISIBLE);
        });

        paa.setOnClickListener(view ->{
            StopImage();
            int result = (currentIndex + 1) % imageIds.length;
            if (imageIds[result] == R.drawable.guu)
            {
                hantei.setText("win");
            } else if (imageIds[result] == R.drawable.choki) {
                hantei.setText("lose");
            } else {
                hantei.setText("drow");
            }
            hantei.setVisibility(View.VISIBLE);
        });

        restart.setOnClickListener(view ->{
            loopstart();

        });



    }

    public  void startImage(ImageView imageView)
    {

        // Threadを使う
        new Thread(() -> {
            while (IsRunnning)
            {
                imageView.post(() ->
                {
                    showNextImage(imageView);
                    // 問題1で作成したshowNextImage で画像を切り替え
                });
                // メインスレッドでのUI操作は禁止なので例外になる
                // imageView.setImageResource(...) はここではできない
                // → MainActivityから画像表示だけを呼ぶようにする
                try
                {
                    Thread.sleep(200); // 0.5秒ごとに進めたい
                } catch (InterruptedException e)
                {
                    break;
                }
            }
        }).start();
    }

    public  void StopImage()
    {
        IsRunnning = false;
    }
    public  void loopstart()
    {
        IsRunnning = true;
        startImage(myImageView);
        hantei.setVisibility(View.INVISIBLE);
    }
}