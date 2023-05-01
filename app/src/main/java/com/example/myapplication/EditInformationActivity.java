package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.util.DBHelper;
import com.example.myapplication.util.ToastUtil;
import com.example.myapplication.util.User;

import java.util.ArrayList;

public class EditInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtUsername, mEtAge, mEtFavorite, mEtSignature;
    private Button mBtSaveInformation;
    private DBHelper dbHelper;
    private RadioButton mRbMale, mRbFemale;
    private String sexString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infor);

        mEtUsername = findViewById(R.id.Et_EditUsername);
        mEtAge = findViewById(R.id.Et_editAge);
        mRbMale = findViewById(R.id.rdB_male);
        mRbFemale = findViewById(R.id.rdB_female);
        mEtFavorite = findViewById(R.id.Et_editFavorite);
        mEtSignature = findViewById(R.id.Et_editSignature);
        mBtSaveInformation = findViewById(R.id.bt_saveInformation);

        dbHelper = new DBHelper(this);

        showInformation();

        mBtSaveInformation.setOnClickListener(this);
    }

    private void showInformation() {            //定义一个根据用户性别显示 显示对应的radiobutton被选中的方法
        ArrayList<User> userData = dbHelper.getData();
        User user = userData.get(LoginActivity.getUserId()); //找到当前用户

        mEtUsername.setText(user.getName());
        mEtAge.setText(user.getEmail());
        mEtFavorite.setText(user.getPhone());                  //分别获取用户信息到TV控件
        mEtSignature.setText(user.getAddress());

        if (!(user.getSex() == null)) {
            if (user.getSex().equals("MALE")) {
                mRbMale.setChecked(true);
            } else if (user.getSex().equals("FEMALE")) {
                mRbFemale.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String username = mEtUsername.getText().toString().trim();
        String email = mEtAge.getText().toString();
        String phone = mEtFavorite.getText().toString();
        String address = mEtSignature.getText().toString();         //分别获取输入的用户信息

        if (!username.isEmpty()) {                           //此处不能用 if(!username == null),因为对象已经存在
            dbHelper.updateUsername(username);            //用户名更新
            if (mRbMale.isChecked()) {
                sexString = "MALE";
            } else if (mRbFemale.isChecked()) {
                sexString = "FEMALE";
            }
            dbHelper.updateEmail(email);
            dbHelper.updateSex(sexString);
            dbHelper.updatePhone(phone);
            dbHelper.updateAddress(address);
                                                    //分别保存到数据库中的User表，必须做一个是否为空的检查 否则表中数据可能会被覆盖为空
            SlideActivity.slideActivity.finish();              //更改后销毁原来的信息显示页面
            ToastUtil.showMsg(this, "info saved");
            Intent intent = new Intent(EditInformationActivity.this, SlideActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "invalid id", Toast.LENGTH_SHORT).show();
        }
    }
}