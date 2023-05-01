package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.util.DBHelper;
import com.example.myapplication.util.ImageUtil;
import com.example.myapplication.util.MainPageActivity;
import com.example.myapplication.util.SlideMenu;
import com.example.myapplication.util.User;

import java.util.ArrayList;

public class SlideActivity extends AppCompatActivity {

    private ImageView mIvHead;
    private SlideMenu slideMenu;
    private Button buttonStudy, buttonRelax, buttonToEdit;
    private ImageButton ibtUserHead;
    private TextView mTvUsername, mTvSex, mTvAge, mTvFavorite, mTvSignature;
    private DBHelper dbHelper;
    public static SlideActivity slideActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        slideActivity = this;

        mIvHead = findViewById(R.id.iv_head);

        slideMenu = findViewById(R.id.slideMenu);
        buttonStudy = findViewById(R.id.btn_study);
        buttonRelax = findViewById(R.id.btn_relax);
        buttonToEdit = findViewById(R.id.bt_toEdit);

        ibtUserHead = findViewById(R.id.ibt_userHead);

        mTvUsername = findViewById(R.id.tv_username);               //用户信息显示控件
        mTvSex = findViewById(R.id.tv_userSex);
        mTvAge = findViewById(R.id.tv_userAge);
        mTvFavorite = findViewById(R.id.tv_userFavorite);
        mTvSignature = findViewById(R.id.tv_userSignature);

        mIvHead.setOnClickListener(v -> slideMenu.switchMenu());

        setClickListener();

        dbHelper = new DBHelper(this);
        showUserInformation();
    }

    //由于button太多，设计一个方法，设计方法之前写好要调用的类
    private class OnClick implements View.OnClickListener {      //新建私有类OnClick，实现View.OnClickListener
        @Override                                               //重写onClick方法
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.btn_study:
                    intent = new Intent(SlideActivity.this, MainPageActivity.class);
                    break;
                case R.id.btn_relax:
                    intent = new Intent(SlideActivity.this, DeliveryActivity.class);
                    break;
                case R.id.bt_toEdit:
                    intent = new Intent(SlideActivity.this, EditInformationActivity.class);
                    break;
                case R.id.ibt_userHead:
                    intent = new Intent(SlideActivity.this, ChangeHeadActivity.class);

            }
            startActivity(intent);
        }
    }

    //设计方法
    private void setClickListener() {
        OnClick onClick = new OnClick();         //在OnClick新建onClick实体，否则36行OnClick是灰色的，也就是没被调用
        buttonStudy.setOnClickListener(onClick); //每个button都调用监听，并用上面的方法
        buttonRelax.setOnClickListener(onClick);
        buttonToEdit.setOnClickListener(onClick);
        ibtUserHead.setOnClickListener(onClick);
    }

    private void showUserInformation() {                          //建立获取用户信息的方法
        ArrayList<User> userData2 = dbHelper.getData();           //创建User队列 userData2
        User user = userData2.get(LoginActivity.getUserId());      //创建一个实体user，并获取当前用户id的数据

        mTvUsername.setText(user.getName());
        mTvAge.setText(user.getEmail());
        mTvSex.setText(user.getSex());
        mTvFavorite.setText(user.getPhone());                  //分别获取用户信息到TV控件
        mTvSignature.setText(user.getAddress());

        if (user.getHeadImage() == null) {             //一开始表中的数据是null而不是empty
            mIvHead.setImageResource(R.mipmap.avatar);
        } else {
            mIvHead.setImageBitmap(ImageUtil.base64ToImage(user.getHeadImage()));//若头像不为空，则取出字节，转化为图片，再显示出来
        }

        if (user.getHeadImage() == null) {                             //信息页面的头像
            ibtUserHead.setImageResource(R.mipmap.avatar);
        } else {
            ibtUserHead.setImageBitmap(ImageUtil.base64ToImage(user.getHeadImage()));
        }
    }

    public void toLogout(View view) {                                             //登出
        Intent intent =new Intent(this,LoginActivity.class);
        Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
        startActivity(intent);

        SharedPreferences spf = getSharedPreferences("UsernamePassword", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("auto", false);                                   //取消自动登录勾选
        editor.apply();
        finish();
    }
}