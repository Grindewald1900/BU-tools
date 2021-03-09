package com.example.bu.tool;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpLogin {
    public static String LoginByPost(String id,String password){
        String address = "http://10.0.2.2:8080/BU_war_exploded/LoginServlet.do";
        String result = "";
        try{
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);

            // Request data
            String data = "id="+ URLEncoder.encode(id,"UTF-8")+
                    "&password="+ URLEncoder.encode(password,"UTF-8");
            Log.e("BU",data);

            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            Log.e("BU","Conn_code:"+conn.getResponseCode());
            Log.e("BU","Conn_message:"+conn.getResponseMessage());
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                result = new String(message.toByteArray());
                Log.e("BU",result);
                return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    public static String RegisterByPost(String id,String password,String email){
        String address = "http://10.0.2.2:8080/BU_war_exploded/RegisterServlet.do";
        String result = "";

        try{
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //Set up timeout
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            conn.setUseCaches(false);

            String data = "id="+ URLEncoder.encode(id,"UTF-8")+
                    "&password="+ URLEncoder.encode(password,"UTF-8")+
                    "&email="+ URLEncoder.encode(email,"UTF-8");
            Log.e("BU","reg" + data);

            OutputStream out = conn.getOutputStream();

            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();
            Log.e("BU","Conn_code2:"+conn.getResponseCode());
            Log.e("BU","Conn_message2:"+conn.getResponseMessage());
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                result = new String(message.toByteArray());
                Log.e("BU","reg"+result);
                //return result;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;

    }

}
