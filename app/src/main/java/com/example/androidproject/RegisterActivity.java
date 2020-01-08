package com.example.androidproject;

import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText account; //用户名
    private EditText pwd;   //密码


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        account = findViewById(R.id.resetPwd_edit_name);
        pwd =  findViewById(R.id.resetPwd_edit_pwd_old);
    }
}
