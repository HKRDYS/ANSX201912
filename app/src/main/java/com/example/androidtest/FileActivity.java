package com.example.sx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 军事页面
 */
public class FileActivity extends AppCompatActivity {
    //初始化控件
    private Button btnBack;
    private Button btnMain;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        //定义控件
        btnBack = findViewById(R.id.btn_back);
        btnMain = findViewById(R.id.btn_main);
        //返回按钮监听器
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FileActivity.this,MainActivity_.class);
                startActivity(intent);
            }
        });
        //主页按钮监听器
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FileActivity.this,MainActivity_.class);
                startActivity(intent);
            }
        });
    }

}
