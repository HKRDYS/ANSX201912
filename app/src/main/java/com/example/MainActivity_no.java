package com.example.lenovo.a1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity_no extends AppCompatActivity {

    //声明用到的组件
    private Button getVersionBtn, cancel;
    private EditText userName, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_no);

        //事例画所有控件
        getVersionBtn = (Button) this.findViewById(R.id.yes);
        cancel = (Button)this.findViewById(R.id.no);

        //给按钮添加监听事件
        getVersionBtn.setOnClickListener(onClickListenr);
        cancel.setOnClickListener(onClickListenr);
    }

    /**
     * 按钮监听类，处理按钮事件
     */
    private View.OnClickListener onClickListenr = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId() == R.id.yes) {

                //要开启一个新的线程，主线程无法获得僌输入流
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        testVersion();

                    }
                }).start();
            }
            if(v.getId()==R.id.no){
                MainActivity_no.this.finish();
            }
        }
    };


    /**
     * 自定义一个消息提示窗口
     *
     * @param msg
     */
    private void showDialog(final String msg) {

        //回到主线程，否则拿不到msg
        MainActivity_no.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("LOGCAT", msg);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_no.this);
                    builder.setMessage(msg).setCancelable(false).setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // TODO Auto-generated method stub

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } catch (Exception e) {
                    showDialog(e.getMessage());
                }
            }
        });

    }


    private void testVersion() {

        //要传递的数据
        String urlStr = "http://xxxxxxx/version";

        try {
            URL url = new URL(urlStr);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setConnectTimeout(3000);
            //conn.setReadTimeout(3000);
            if (true) {

                //获得输入流
                InputStream in = conn.getInputStream();

                //创建一个2048字节数的缓冲区
                byte[] buffer = new byte[2048];

                //在输入流中读取数据并存放到缓冲字节数组中
                in.read(buffer);

                //将字节转换成字符串
                String msg = new String(buffer);

                showDialog(msg);

                //关闭数据流
                in.close();
            } else {

                //否则就关闭连接
                conn.disconnect();
                showDialog("连接失败");
            }
        } catch (Exception e) {
            showDialog(e.getMessage());
        }
    }

}
