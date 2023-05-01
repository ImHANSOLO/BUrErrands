package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class CActivity extends AppCompatActivity {

    private Button btnStudy1,btnStudy2,btnStudy3,btnStudy4,btnStudy5,btnStudy6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        btnStudy1 = findViewById(R.id.btn_study1);
        btnStudy2 = findViewById(R.id.btn_study2);
        btnStudy3 = findViewById(R.id.btn_study3);
        btnStudy4 = findViewById(R.id.btn_study4);
        btnStudy5 = findViewById(R.id.btn_study5);
        btnStudy6 = findViewById(R.id.btn_study6);

        //setClickListener();
    }

    /*
    private class OnClick implements View.OnClickListener{     //新建私有类OnClick，与 View.OnClickListener接口对接
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case R.id.btn_study1:
                    intent = new Intent(StudyActivity.this,RunoobActivity.class);
                    break;
                case R.id.btn_study2:
                    intent = new Intent(StudyActivity.this,TechnologyMuseumActivity.class);
                    break;
                case R.id.btn_study3:
                    intent = new Intent(StudyActivity.this,MedicineActivity.class);
                    break;
                case R.id.btn_study4:
                    intent = new Intent(StudyActivity.this,AnswerActivity.class);
                    break;
                case R.id.btn_study5:
                    intent = new Intent(StudyActivity.this,SpaceActivity.class);
                    break;
                case R.id.btn_study6:
                    intent = new Intent(StudyActivity.this, DeepSeaActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
    private void setClickListener(){
        OnClick onClick = new OnClick();
        btnStudy1.setOnClickListener(onClick);
        btnStudy2.setOnClickListener(onClick);
        btnStudy3.setOnClickListener(onClick);
        btnStudy4.setOnClickListener(onClick);
        btnStudy5.setOnClickListener(onClick);
        btnStudy6.setOnClickListener(onClick);          //每个button都调用监听，并用上面的方法
    }

     */


}

