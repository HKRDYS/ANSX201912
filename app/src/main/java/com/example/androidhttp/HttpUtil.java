package com.example.androidhttp;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
public class HttpUtil {
    //登录
    public static  void  loginWithOkHttp(String url,String username,String password,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();
        Request request = new Request.Builder()
                .url(url).post(body).build();
        client.newCall(request).enqueue(callback);
    }
    //注册
    public static void registerWithOkHttp(String url,String username,String password,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username",username).add("password",password).build();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(callback);

    }
}
