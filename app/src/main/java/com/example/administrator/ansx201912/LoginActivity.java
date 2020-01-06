package com.example.administrator.ansx201912;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * 功能:登录窗口实现与跳转功能
 * 日期:default
 * 作者
 * */
public class LoginActivity extends Activity {
    private ImageButton btn_login,btn_cancel;
    private Button btn_registered;
    private EditText edit_username,edit_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        final String username = edit_username.getText().toString();
        final String password = edit_password.getText().toString();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.equals("username") & password.equals("password")){}
        //跳转
            }
        });


    }

     private void init(){
        setContentView(R.layout.login_activity);
        btn_registered = findViewById(R.id.btn_login_reg);
        btn_login = findViewById(R.id.img_btn_login);
        btn_cancel = findViewById(R.id.img_btn_cancel);
    }



}
