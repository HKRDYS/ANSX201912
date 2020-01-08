package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.webservice.WebService;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    public int pwdResetFlag = 0;
    private EditText account; //用户名
    private EditText pwd;   //密码
    private Button registerButton;//注册
    private Button loginButton;//登录
    private Button cancelButton;//注销


    class MyThread extends Thread{
        private Context context;
        private String ip;
        private String username;
        private String password;
        private MyHandler handler;
        private int mode;
        public MyThread(Context context,String ip,int mode,String username,String password,MyHandler handler){
            this.context = context;
            this.ip = ip;
            this.mode = mode;
            this.username = username;
            this.password = password;
            this.handler = handler;
        }

        @Override
        public void run() {
            super.run();
            //访问网络
            //逻辑

            try {
                //第一步:URL对象
                URL url = new URL(ip+"?username="+username+"&password="+password + "&mode=" + mode);
                //第二步:连接HTTP
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //第三步:设置连接对象的属性
                conn.setRequestMethod("GET");//设置连接的请求方式为get方式
                conn.setConnectTimeout(5000);//设置连接超时的时间
                conn.setReadTimeout(5000);//设置读取超时的时间
                //第四步:读取链接的数据
                InputStream is = conn.getInputStream();//获取连接的输入流,并将其赋值给读取流对象
                //第五步:创建输入流读取器
                InputStreamReader isr = new InputStreamReader(is);
                //第六步:将读取器中的数据存放在缓冲区中
                BufferedReader br = new BufferedReader(isr);
                //第七步:创建两个变量
                String line;//用于保存我们待会儿从缓冲区里面读取出来的数据
                StringBuilder sb = new StringBuilder();//创建StringBuilder对象,将line拼接成一个字符串
                //第八步:读数据
                while ((line = br.readLine()) != null){//读取缓冲区中的数据(如果不为空,就一直循环,为空的话,就停止循环)
                    sb.append(line);//用于拼接字符串
                }
                Log.d("com.example.logindemo", "run: " + sb.toString());
                Message message = new Message();
                message.obj = sb.toString();
                //将消息传递到handler
                handler.sendMessage(message);
                //第九步:关闭资源,以免浪费资源
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            //12312 dwaas
            //解析json字符串
            try {
                JSONObject jsonObject = new JSONObject(result);
                //方法说明：optString方法表示取json对象里面的某个键对应的值，如果没有则取到得是null
                String name = jsonObject.optString("username");
                String password = jsonObject.optString("password");
                System.out.println(name);
                System.out.println(password);
                if ( name.equals("login") ||  password.equals("true")) {

                    //登录成功
                    //表示登陆成功,登陆信息保存的位置
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();

                } else {
                    //用户名和密码输入错误
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，登录失败", Toast.LENGTH_LONG).show();
                    //利用sharePreference保存登陆成功的一些基本信息
                    /**
                     * 参数说明：
                     * 参数1为文件名字
                     * 参数2为访问的方式
                     */
                    SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
                    //获得SharedPreferences对象的编辑器
                    SharedPreferences.Editor editor = sp.edit();
                    //参数说明：参数1表示键，参数2表示值
                    editor.putString("username", name);
                    editor.putString("password", password);
                    //存储完数据以后提交
                    editor.commit();

//                    //页面跳转
//                    Intent intent = new Intent(MainActivity.this, LoginSuccess.class);
//                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    private CheckBox rememberCheck;//记住密码
//
//    private SharedPreferences login_sp;
//    private String username,password;
//
//    private View loginView;                           //登录
//    private View loginSuccessView;
//    private TextView loginSuccessShow;
//    private TextView changePwdText;
//    private UserDataManager mUserDataManager;         //用户数据管理类
    private MyHandler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        handler = new MyHandler();

        //通过id找到相应的控件
        account = (EditText) findViewById(R.id.login_edit_account);
        pwd = (EditText) findViewById(R.id.login_edit_pwd);
        registerButton = (Button) findViewById(R.id.login_btn_register);
        loginButton = (Button) findViewById(R.id.login_btn_login);
        cancelButton = (Button) findViewById(R.id.login_btn_cancel);
//        loginView=findViewById(R.id.login_view);
//        loginSuccessView=findViewById(R.id.login_success_view);
//        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);
//
//        changePwdText = (TextView) findViewById(R.id.login_text_change_pwd);
//
//        rememberCheck = (CheckBox) findViewById(R.id.Login_Remember);
//        login_sp = getSharedPreferences("userInfo", 0);
//        String name=login_sp.getString("USER_NAME", "");
//        String userPwd =login_sp.getString("PASSWORD", "");
//        boolean choseRemember =login_sp.getBoolean("rememberCheck", false);
//        boolean choseAutoLogin =login_sp.getBoolean("autoLoginCheck", false);
//        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
//        if(choseRemember){
//            account.setText(name);
//            pwd.setText(userPwd);
//            rememberCheck.setChecked(true);
//        }

        registerButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        loginButton.setOnClickListener(mListener);
        cancelButton.setOnClickListener(mListener);
//        changePwdText.setOnClickListener(mListener);

//        ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo
//        image.setImageResource(R.drawable.logo);

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
        String username = account.getText().toString();
        String password = pwd.getText().toString();
        int mode = 0;
        //这部分应该访问网络的代码
        String ip="http://192.168.43.218:8080/AZSXBACK_war_exploded/Login";
        MyThread mythread = new MyThread(LoginActivity.this,ip,mode,username,password,handler);
        mythread.start();
    }




    public void cancel() {           //注销


    }

//    public boolean isUserNameAndPwdValid() {
//        if (account.getText().toString().trim().equals("")) {
//            Toast.makeText(this, getString(R.string.account_empty),
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (pwd.getText().toString().trim().equals("")) {
//            Toast.makeText(this, getString(R.string.pwd_empty),
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }

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
