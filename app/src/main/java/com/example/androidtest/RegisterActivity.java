package com.example.androidtest;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidhttp.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText account; //用户名
    private EditText pwd;   //密码


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        account = (EditText) findViewById(R.id.resetPwd_edit_name);
        pwd = (EditText) findViewById(R.id.resetPwd_edit_pwd_old);
        String registerUrl="http://localhost:8080/AndroidWeb_war_exploded/RegisterServlet";
        String userName = account.getText().toString().trim();//trim()去掉空格
        String userPwd = pwd.getText().toString().trim();
        registerWithOkHttp(registerUrl,userName,userPwd);

    }
    public void registerWithOkHttp(String url,String username,String password){
        HttpUtil.registerWithOkHttp(url, username, password, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                //异常情况进行处理
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.equals("true")){
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
