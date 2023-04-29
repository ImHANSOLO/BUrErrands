package com.example.myapplication.util;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Domain.CategoryDomain;
import com.example.myapplication.Domain.FoodDomain;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.PopularAdapter;
import android.os.Bundle;
import android.widget.Button;
import com.example.myapplication.util.DBHelper;
import com.example.myapplication.util.ToastUtil;
import com.example.myapplication.util.User;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    private Button ShopCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ShopCart = findViewById(R.id.bt_shopping_cart);

        setClickListener();

        recyclerViewCategory();
        recyclerViewPopular();


    }

    private class OnClick implements View.OnClickListener{     //新建私有类OnClick，与 View.OnClickListener接口对接
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case R.id.bt_shopping_cart:
                    intent = new Intent(MainPageActivity.this, MainActivity.class);
            }
            startActivity(intent);
        }
    }

    private void setClickListener(){
        OnClick onClick = new OnClick();
        ShopCart.setOnClickListener(onClick);
    }


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        recyclerViewCategory();
        recyclerViewPopular();
    }
     */

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Pizza", "cat_1"));
        category.add(new CategoryDomain("Burgers", "cat_2"));
        category.add(new CategoryDomain("Hotdogs", "cat_3"));
        category.add(new CategoryDomain("Donuts", "cat_4"));
        category.add(new CategoryDomain("Drinks", "cat_5"));

        adapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }
    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni pizza","pizza1","slice pepperoni, mozzarella cheese,fresh oregano,ground black pepper", 12.99));
        foodList.add(new FoodDomain("Cheese Burger","burger","beef, Gouda Cheese, Special Sauce, Lettuce, tomato",6.59));
        foodList.add(new FoodDomain("Vegetable Pizza","pizza2","olive oil, vegetable oil, pitted kalamata, cherry tomatos",10.99));

        adapter2 = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(adapter2);
    }
}
