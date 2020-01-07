package com.example.androidtest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidhttp.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    public int pwdResetFlag = 0;
    private EditText account; //用户名
    private EditText pwd;   //密码
    private Button registerButton;//注册
    private Button loginButton;//登录
    private Button cancelButton;//注销
    private CheckBox rememberCheck;//记住密码
    private SharedPreferences login_sp;
    private View loginView;                           //登录
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private TextView changePwdText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        //通过id找到相应的控件
        account = (EditText) findViewById(R.id.login_edit_account);//获取用户名
        pwd = (EditText) findViewById(R.id.login_edit_pwd);//获取密码
        registerButton = (Button) findViewById(R.id.login_btn_register);//注册按钮
        loginButton = (Button) findViewById(R.id.login_btn_login);//登录按钮
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
                    String loginUrl = "http://localhost:8080/AndroidWeb_war_exploded/LoginServlet";
                    //获取当前输入的用户名和密码信息
                    String userName = account.getText().toString().trim();//trim()去掉空格
                    String userPwd = pwd.getText().toString().trim();
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor editor =login_sp.edit();
                    loginWithOkHttp(loginUrl,userName,userPwd);
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
    public void loginWithOkHttp(String url,String username,String password) {//登录按钮监听事件
        HttpUtil.loginWithOkHttp(url, username, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseData.equals("true")){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

////更改*******************
    public void cancel() {           //注销
//        if (isUserNameAndPwdValid()) {
//            String userName = account.getText().toString().trim();    //获取当前输入的用户名和密码信息
//            String userPwd = pwd.getText().toString().trim();
//
//            int result = 1;
////            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
//
//            if(result==1){                                             //返回1说明用户名和密码均正确
////                Intent intent = new Intent(Login.this,User.class) ;    //切换Login Activity至User Activity
////                startActivity(intent);
//                Toast.makeText(this, getString(R.string.cancel_success),Toast.LENGTH_SHORT).show();//登录成功提示
//                pwd.setText("");
//                account.setText("");
//
////                mUserDataManager.deleteUserDatabyname(userName);
//
//
//            }else if(result==0){
//                Toast.makeText(this, getString(R.string.cancel_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
//            }
//        }
//
    }

    //更改*******************
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
