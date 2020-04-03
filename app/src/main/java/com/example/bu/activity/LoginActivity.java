package com.example.bu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bu.R;
import com.example.bu.tool.HttpLogin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private QMUIRoundButton mBtnLogin;
    private EditText etName, etPwd;
    private TextView tvForget, tvRegister;

    private final static int LOGIN_JUDGE = 1;
    private int RequestCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                Toast.makeText(mContext,getResources().getString(R.string.reg_success),Toast.LENGTH_LONG).show();
                etName.setText(data.getStringExtra("id"));
                etPwd.setText(data.getStringExtra("password"));
                break;
            case 2:
                Toast.makeText(mContext,getResources().getString(R.string.reg_fail),Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(mContext,getResources().getString(R.string.network_err),Toast.LENGTH_LONG).show();
                break;
        }
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case LOGIN_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("success")) {
                            String s = etName.getText().toString();
                            setResult(1,new Intent().putExtra("name",etName.getText().toString()));
                        }else {
                            setResult(2,new Intent().putExtra("name",getResources().getString(R.string.click_sign)));
                        }
                        finish();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };


    private void initView(){
        mBtnLogin = findViewById(R.id.bt_login_sign);
        etName = findViewById(R.id.et_login_name);
        etPwd = findViewById(R.id.et_login_pwd);
        tvForget = findViewById(R.id.tv_login_forget);
        tvRegister = findViewById(R.id.tv_login_register);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = HttpLogin.LoginByPost(etName.getText().toString(),etPwd.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = LOGIN_JUDGE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext,RegisterActivity.class),1);
            }
        });
    }
}
