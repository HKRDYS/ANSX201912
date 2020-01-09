package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.home.MainActivity_;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private EditText username; //用户名
    private EditText password;   //密码
    private EditText sure_password;//确认密码
    private Button sureButton;//注册确认按钮
    private Button cancelButton;//注册取消按钮

    class MyThread extends Thread{
        private Context context;
        private String ip;
        private String registerUsername;
        private String registerPassword;
        private MyHandler handler;
        public MyThread(Context context,String ip,String registerUsername,String registerPassword,MyHandler handler){
            this.context = context;
            this.ip = ip;
            this.registerUsername = registerUsername;
            this.registerPassword = registerPassword;
            this.handler = handler;
        }

        @Override
        public void run() {
            super.run();
            try {
                //第一步:URL对象
                URL url = new URL(ip+"?username="+registerUsername+"&password="+registerPassword );
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
            super.handleMessage(msg);
            String result = (String) msg.obj;
            //解析json字符串
            try {
                JSONObject jsonObject = new JSONObject(result);
                //方法说明：optString方法表示取json对象里面的某个键对应的值，如果没有则取到得是null
                String registerCheck = jsonObject.optString("register");
                if ( registerCheck.equals("2")) {
                    Toast.makeText(RegisterActivity.this, "恭喜，注册成功！", Toast.LENGTH_LONG).show();
                    //页面跳转
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (registerCheck.equals("1")){
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
                }
                else {
                    //注册失败
                    Toast.makeText(RegisterActivity.this, "遗憾，注册失败", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private MyHandler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        handler = new MyHandler();
        username = findViewById(R.id.resetPwd_edit_name);
        password =  findViewById(R.id.resetPwd_edit_pwd_old);
        sure_password = findViewById(R.id.resetPwd_edit_pwd_new);
        sureButton = findViewById(R.id.register_btn_sure);
        cancelButton = findViewById(R.id.register_btn_cancel);
        sureButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        cancelButton.setOnClickListener(mListener);
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_sure:                            //登录界面的注册按钮
                    register();
                    finish();
                    break;
                case R.id.login_btn_cancel:                             //登录界面的注销按钮
                    cancel();
                    finish();
                    break;
            }
        }
    };
    public void register() {
        String registerUsername = username.getText().toString();
        String registerPassword = password.getText().toString();
        String registerPassword_sure = sure_password.getText().toString();
        if (registerPassword.equals(registerPassword_sure)){
            //这部分应该访问网络的代码
            String ip="http://192.168.43.218:8080/AZSXBACK_war_exploded/Register";
            MyThread mythread = new MyThread(RegisterActivity.this,ip,registerUsername,registerPassword,handler);
            mythread.start();
        }else {
            Toast.makeText(RegisterActivity.this, "前后密码不同，注册失败", Toast.LENGTH_LONG).show();
        }

    }
    public void cancel(){
        Intent cancelIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(cancelIntent);
    }
}
