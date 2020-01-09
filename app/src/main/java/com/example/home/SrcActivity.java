package com.example.lenovo.wenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SrcActivity extends AppCompatActivity {
    private Button btnback;
    private Button btnSrc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_src);

        btnback = findViewById(R.id.btn_cancel);
        btnSrc = findViewById(R.id.tv_search);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SrcActivity.this,MainActivity_.class);
                startActivity(intent);
            }
        });
        btnSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SrcActivity.this, FileActivity.class);
                startActivity(intent);
            }
        });
    }
}
