package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.util.DBHelper;
import com.example.myapplication.util.ToastUtil;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtToRegister;
    private DBHelper dbHelper;
    private EditText mEtSetUsername;
    private EditText mEtSetUserPassword1;
    //private EditText mEtSetUserPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEtSetUsername = findViewById(R.id.set_username);
        mEtSetUserPassword1 = findViewById(R.id.set_userPassword1);
        //mEtSetUserPassword2 = findViewById(R.id.set_userPassword2);
        mBtToRegister = findViewById(R.id.bt_startRegister);

        dbHelper = new DBHelper(this);
        mBtToRegister.setOnClickListener(this);
    }

    public void onClick(View view){
        String username = mEtSetUsername.getText().toString().trim();
        String password1 = mEtSetUserPassword1.getText().toString().trim();
        //String password2 = mEtSetUserPassword2.getText().toString().trim();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) ) {
            if (dbHelper.add(username, password1)){

                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                finish();
                ToastUtil.showMsg(RegisterActivity.this,"Register Successfully");
            }
            else {
                ToastUtil.showMsg(RegisterActivity.this,"existed id");
            }
        }
        else {
            ToastUtil.showMsg(RegisterActivity.this,"invalid info");
        }
    }
}