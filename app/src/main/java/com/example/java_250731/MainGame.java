package com.example.java_250731;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainGame extends AppCompatActivity
{
    private boolean IsRunnning = true;

    private ImageView imageView;
    private final int[] imageIds = { R.drawable.guu, R.drawable.choki, R.drawable.paa };
    private int currentIndex = 0;
    public void showNextImage(ImageView imageView) {

        //(currentIndex + 1) % imageIds.length;

        imageView.setImageResource(imageIds[(currentIndex + 1) % imageIds.length]);

        imageView.setVisibility(ImageView.VISIBLE);

        currentIndex++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        //下にじゃんけんゲームの処理を書く

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
                    Thread.sleep(500); // 0.5秒ごとに進めたい
                } catch (InterruptedException e)
                {
                    break;
                }
            }
        }).start();

    public void StopImage()
    {
        IsRunnning = false;
    }
}
