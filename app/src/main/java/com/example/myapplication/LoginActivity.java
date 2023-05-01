package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.myapplication.util.DBHelper;
import com.example.myapplication.util.ToastUtil;
import com.example.myapplication.util.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //implements View.OnClickListener
    private Button mBtLogin;
    private EditText mEtUser;
    private EditText mEtPassword;
    private Button mBtRegister;
    private CheckBox mCbRemember;
    private DBHelper dbHelper;

    public static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtLogin = findViewById(R.id.bt_login);
        mEtPassword = findViewById(R.id.Ed_password);
        mEtUser = findViewById(R.id.Ed_username);
        mBtRegister = findViewById(R.id.bt_register);
        mCbRemember = findViewById(R.id.cb_rememberPW);

        initData();

        mBtLogin.setOnClickListener(this);
        mBtRegister.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    private void initData() {                      //checkbox
        SharedPreferences spf = getSharedPreferences("UsernamePassword", MODE_PRIVATE);
        String username = spf.getString("username", "");
        String password = spf.getString("password", "");
        boolean isCheck = spf.getBoolean("isCheck", false);
        //boolean autoCheck = spf.getBoolean("auto", false);
        if (isCheck) {
            mEtUser.setText(username);
            mEtPassword.setText(password);
            mCbRemember.setChecked(true);
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String username = mEtUser.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> userData = dbHelper.getData();
                    boolean judge = false;
                    for (int i = 0; i < userData.size(); i++) {
                        User user = userData.get(i);
                        if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                            judge = true;
                            userId = i;
                            break;
                        }
                        else {
                            judge = false;
                        }
                    }
                    if (judge) {
                        checkCheck(username, password);

                        ToastUtil.showMsg(LoginActivity.this, "Login successfully");
                        Intent intent2 = new Intent(this, SlideActivity.class);
                        startActivity(intent2);
                        finish();
                    } else {
                        ToastUtil.showMsg(LoginActivity.this, "Wrong info");
                    }
                } else {
                    ToastUtil.showMsg(LoginActivity.this, "Invalid info");
                }
                break;
            case R.id.bt_register:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void checkCheck(String username, String password) {
        SharedPreferences spf = getSharedPreferences("UsernamePassword", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        /*
        if (mCbAutoLogin.isChecked()) {
            mCbRemember.setChecked(true);
        }

         */
        if (mCbRemember.isChecked()) {
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("isCheck", true);
        } else {
            editor.putBoolean("isCheck", false);
        }
        /*
        if (mCbAutoLogin.isChecked()) {
            editor.putBoolean("auto", true);
        }

         */
        editor.apply();
    }

    public static int getUserId() {
        return userId;
    }
}
