package com.example.sx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    //初始化控件
    private Button mTabFile;
    private Button mTabFood;
    private Button mTabMy;
    private Button mTabSky;
    private Button mTabTrian;
    private Button mTabMain;

//    private TextView textView;

//    private FileActivity mFile;
//    private FileActivity mFood;
//    private FileActivity mMy;
//    private FileActivity mSky;
//    private FileActivity mTrian;
//    private FileActivity mMain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);
        //定义控件
        mTabFile = findViewById(R.id.file);
        mTabFood = findViewById(R.id.food);
        mTabMy = findViewById(R.id.am);
        mTabSky = findViewById(R.id.sky);
        mTabTrian = findViewById(R.id.train);
        mTabMain = findViewById(R.id.imp);

        /**
         * 点击军事按钮监视器
         */
        mTabFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                第一个参数:上下文对象this
                第二个参数:目标文件
                 */
                Intent intent = new Intent(MainActivity.this,FileActivity.class);
                startActivity(intent);

            }
        });

        /**
         * 点击食物按钮监视器
         */
        mTabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                第一个参数:上下文对象this
                第二个参数:目标文件
                 */
                Intent intent = new Intent(MainActivity.this,FoodActivity.class);
                startActivity(intent);

            }
        });

        /**
         * 点击我的按钮监视器
         */
        mTabMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                第一个参数:上下文对象this
                第二个参数:目标文件
                 */
                Intent intent = new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent);

            }
        });


        /**
         * 点击天文按钮监视器
         */
        mTabSky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                创建意图intent
                第一个参数:上下文对象this
                第二个参数:目标文件
                 */
                Intent intent = new Intent(MainActivity.this,SkyActivity.class);
                startActivity(intent);

            }
        });


        /**
         * 点击旅游按钮监视器
         */
        mTabTrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                创建意图intent
                第一个参数:上下文对象this
                第二个参数:目标文件
                 */
                Intent intent = new Intent(MainActivity.this,TrianActivity.class);
                startActivity(intent);

            }
        });


        /**
         * 点击天文按钮监视器
         */
        mTabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                创建意图intent
                第一个参数:上下文对象this
                第二个参数:目标文件
                 */
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }


}

