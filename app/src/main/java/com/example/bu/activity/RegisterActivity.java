package com.example.bu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bu.R;
import com.example.bu.tool.HttpLogin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class RegisterActivity extends AppCompatActivity {
    private Context mContext;
    private EditText etName, etPassword, etEmail, etPassword2;
    private QMUIRoundButton mBtnRegister;
    private int ResultCodeSuccess = 1;
    private int ResultCodeFail= 2;
    private final static int REGISTER_JUDGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        initView();
    }

    private void initView(){
        etName = findViewById(R.id.et_reg_name);
        etPassword = findViewById(R.id.et_reg_pwd);
        etPassword2 = findViewById(R.id.et_reg_pwd2);
        etEmail = findViewById(R.id.et_reg_email);
        mBtnRegister = findViewById(R.id.bt_reg_sign);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! etPassword.getText().toString().equals(etPassword2.getText().toString())){
                    Toast.makeText(mContext,getResources().getString(R.string.check_pwd),Toast.LENGTH_LONG).show();
                }else if(! etEmail.getText().toString().contains(String.valueOf("@")) || ! etEmail.getText().toString().contains(".com")){
                    Toast.makeText(mContext,getResources().getString(R.string.check_email),Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = HttpLogin.RegisterByPost(etName.getText().toString(),
                                    etPassword.getText().toString(),etEmail.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("result",result);
                            Message msg = new Message();
                            msg.what = REGISTER_JUDGE;
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case REGISTER_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("id",etName.getText().toString());
                            intent.putExtra("password",etPassword.getText().toString());
                            setResult(ResultCodeSuccess,intent);
                        }else {
                            setResult(ResultCodeFail,new Intent());
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

}
