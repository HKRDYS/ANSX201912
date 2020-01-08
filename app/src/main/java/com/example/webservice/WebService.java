package com.example.webservice;

import android.content.Context;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {
    private static String IP = "192.168.42.127";

    public static String httpGet(String username, String password, int mode) throws Exception {
        HttpURLConnection connection = null;//新建连接
        InputStream inputStream = null;//连接输入流
        try {
            //url 对象
            String url = "http://" + IP + ":8080" + "/AZSXBACK_war_exploded/TLogin"
                    + "?username = " + username + "&password=" + password + "&mode" + mode;
            //连接HTTP
            connection = (HttpURLConnection) new URL(url).openConnection();
            // 设置连接对象的属性
            connection.setConnectTimeout(3000); // 设置超时时间
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            //设置连接的请求方式为get方式
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式
            if (connection.getResponseCode() == 200) {
                //获取连接的输入流，并将其赋值给读取流对象
                inputStream = connection.getInputStream();
                return parseInfo(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 将输入流转化为 String 型
    private static String parseInfo(InputStream inStream) throws Exception {
        byte[] data = read(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

    // 将输入流转化为byte型
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }
}


