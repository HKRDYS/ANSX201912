package com.example.androidtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    public int pwdResetFlag = 0;
    private EditText account; //用户名
    private EditText pwd;   //密码
    private Button registerButton;//注册
    private Button loginButton;//登录
    private Button cancelButton;//注销

    private CheckBox rememberCheck;//记住密码

    private SharedPreferences login_sp;
    private String username,password;

    private View loginView;                           //登录
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private TextView changePwdText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        //通过id找到相应的控件
        account = (EditText) findViewById(R.id.login_edit_account);
        pwd = (EditText) findViewById(R.id.login_edit_pwd);
        registerButton = (Button) findViewById(R.id.login_btn_register);
        loginButton = (Button) findViewById(R.id.login_btn_login);
        cancelButton = (Button) findViewById(R.id.login_btn_cancel);
        loginView=findViewById(R.id.login_view);
        loginSuccessView=findViewById(R.id.login_success_view);
        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

        changePwdText = (TextView) findViewById(R.id.login_text_change_pwd);

        rememberCheck = (CheckBox) findViewById(R.id.Login_Remember);
        login_sp = getSharedPreferences("userInfo", 0);
        String name=login_sp.getString("USER_NAME", "");
        String userPwd =login_sp.getString("PASSWORD", "");
        boolean choseRemember =login_sp.getBoolean("rememberCheck", false);
        boolean choseAutoLogin =login_sp.getBoolean("autoLoginCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            account.setText(name);
            pwd.setText(userPwd);
            rememberCheck.setChecked(true);
        }
        //注册
        registerButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        //登录
        loginButton.setOnClickListener(mListener);
        //注销
        cancelButton.setOnClickListener(mListener);
        //更改密码
        changePwdText.setOnClickListener(mListener);

        ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo
        image.setImageResource(R.drawable.logo);

//        if (mUserDataManager == null) {
//            mUserDataManager = new UserDataManager(this);
//            mUserDataManager.openDataBase();                              //建立本地数据库
//        }

    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:                            //登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(LoginActivity.this,RegisterActivity.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login_btn_login:                              //登录界面的登录按钮
                    login();
                    break;
                case R.id.login_btn_cancel:                             //登录界面的注销按钮
                    cancel();
                    break;
                case R.id.login_text_change_pwd:                             //登录界面的注销按钮
                    Intent intent_Login_to_reset = new Intent(LoginActivity.this,ResetActivity.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_reset);
                    finish();
                    break;
            }
        }
    };
    public void login() {                                              //登录按钮监听事件
//        if (isUserNameAndPwdValid()) {
//            String userName = account.getText().toString().trim();    //获取当前输入的用户名和密码信息
//            String userPwd = pwd.getText().toString().trim();
//            SharedPreferences.Editor editor =login_sp.edit();
//
//            int result = 1;
//            //int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
//
//            if(result==1){                                             //返回1说明用户名和密码均正确
//                //保存用户名和密码
//                editor.putString("USER_NAME", userName);
//                editor.putString("PASSWORD", userPwd);
//
//                //是否记住密码
//                if(rememberCheck.isChecked()){
//                    editor.putBoolean("rememberCheck", true);
//                }else{
//                    editor.putBoolean("rememberCheck", false);
//                }
//                editor.commit();
//
//                Intent intent = new Intent(LoginActivity.this,User.class) ;    //切换Login Activity至User Activity
//                startActivity(intent);
//                finish();
//                Toast.makeText(this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();//登录成功提示
//            }else if(result==0){
//                Toast.makeText(this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
//            }
//        }

        final String userName = account.getText().toString().trim();    //获取当前输入的用户名和密码信息
        final String userPwd = pwd.getText().toString().trim();
        SharedPreferences.Editor editor =login_sp.edit();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path="http://192.168.0.101:8080/AndroidTest/mustLogin?logname="+userName+"&password="+userPwd;
                try {
                    try {
                        URL url = new URL(path); //新建url并实例化
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");//获取服务器数据
                        connection.setReadTimeout(8000);//设置读取超时的毫秒数
                        connection.setConnectTimeout(8000);//设置连接超时的毫秒数
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String result = reader.readLine();//读取服务器进行逻辑处理后页面显示的数据
                        Log.d("MainActivity","run: "+result);
                        if (result.equals("login successfully!")){
                            Log.d("LoginActivity","run2: "+result);
                            Looper.prepare();
                            Log.d("LoginActivity","run3: "+result);
                            Toast.makeText(LoginActivity.this," successfully!",Toast.LENGTH_SHORT).show();
                            Log.d("LoginActivity","run4: "+result);
                            Looper.loop();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }


    public void cancel() {           //注销
        if (isUserNameAndPwdValid()) {
            String userName = account.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = pwd.getText().toString().trim();

            int result = 1;
//            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);

            if(result==1){                                             //返回1说明用户名和密码均正确
//                Intent intent = new Intent(Login.this,User.class) ;    //切换Login Activity至User Activity
//                startActivity(intent);
                Toast.makeText(this, getString(R.string.cancel_success),Toast.LENGTH_SHORT).show();//登录成功提示
                pwd.setText("");
                account.setText("");

//                mUserDataManager.deleteUserDatabyname(userName);


            }else if(result==0){
                Toast.makeText(this, getString(R.string.cancel_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }

    }

    public boolean isUserNameAndPwdValid() {
        if (account.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (pwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
//        if (mUserDataManager == null) {
//            mUserDataManager = new UserDataManager(this);
//            mUserDataManager.openDataBase();
//        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
//        if (mUserDataManager != null) {
//            mUserDataManager.closeDataBase();
//            mUserDataManager = null;
//        }
        super.onPause();
    }
}
