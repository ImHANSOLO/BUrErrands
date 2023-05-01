package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class DActivity extends AppCompatActivity {

    private Button btnGame1,btnGame2,btnGame3,btnGame4,btnGame5,btnGame6,btnGame7,btnGame8,btnGame9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);

        btnGame1 = findViewById(R.id.bt_game1);
        btnGame2 = findViewById(R.id.bt_game2);
        btnGame3 = findViewById(R.id.bt_game3);
        btnGame4 = findViewById(R.id.bt_game4);
        btnGame5 = findViewById(R.id.bt_game5);
        btnGame6 = findViewById(R.id.bt_game6);
        btnGame7 = findViewById(R.id.bt_game7);
        btnGame8 = findViewById(R.id.bt_game8);
        btnGame9 = findViewById(R.id.bt_game9);

        //setClickListener();
    }
    /*
    private class OnClick implements View.OnClickListener{      //新建私有类OnClick，实现View.OnClickListener
        @Override                                               //重写onClick方法
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case R.id.bt_game1:
                    intent = new Intent(RelaxActivity.this,WebZTypeActivity.class);
                    break;
                case R.id.bt_game2:
                    intent = new Intent(RelaxActivity.this,EatwhatActivity.class);
                    break;
                case R.id.bt_game3:
                    intent = new Intent(RelaxActivity.this, SummondragonActivity.class);
                    break;
                case R.id.bt_game4:
                    intent = new Intent(RelaxActivity.this,WhichoneActivity.class);
                    break;
                case R.id.bt_game5:
                    intent = new Intent(RelaxActivity.this,MikutapActivity.class);
                    break;
                case R.id.bt_game6:
                    intent = new Intent(RelaxActivity.this,HenxiangsiActivity.class);
                    break;
                case R.id.bt_game7:
                    intent = new Intent(RelaxActivity.this, LoopTapActivity.class);
                    break;
                case R.id.bt_game8:
                    intent = new Intent(RelaxActivity.this, FlowerActivity.class);
                    break;
                case R.id.bt_game9:
                    intent = new Intent(RelaxActivity.this, EndlessLakeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
    //设计方法
    private void setClickListener(){
        OnClick onClick = new OnClick();       //在OnClick新建onClick实体，否则36行OnClick是灰色的，也就是没被调用
        btnGame1.setOnClickListener(onClick); //每个button都调用监听，并用上面的方法
        btnGame2.setOnClickListener(onClick);
        btnGame3.setOnClickListener(onClick);
        btnGame4.setOnClickListener(onClick);
        btnGame5.setOnClickListener(onClick);
        btnGame6.setOnClickListener(onClick);
        btnGame7.setOnClickListener(onClick);
        btnGame8.setOnClickListener(onClick);
        btnGame9.setOnClickListener(onClick);
    }

     */

}