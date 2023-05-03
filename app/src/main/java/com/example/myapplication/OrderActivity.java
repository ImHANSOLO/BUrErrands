package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Helper.ManagementCart;
import com.example.myapplication.util.MainPageActivity;
import com.example.myapplication.util.OrderHistory;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    TextView itemTotal, tax, delivery, total,item_Name;
    Button placeOrder;

    public static ArrayList<OrderModel> orderHistoryList = new ArrayList<OrderModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        item_Name = findViewById(R.id.item_name);
        itemTotal = findViewById(R.id.tv_item_total);
        tax = findViewById(R.id.tv_tax);
        delivery = findViewById(R.id.tv_delivery);
        total = findViewById(R.id.tv_total);
        placeOrder = findViewById(R.id.btn_place_order);

        // 获取购物车信息
        String itemName = getIntent().getStringExtra("item_Name");
        double itemTotalValue = getIntent().getDoubleExtra("itemTotal", 0);
        double taxValue = getIntent().getDoubleExtra("tax", 0);
        double deliveryValue = getIntent().getDoubleExtra("delivery", 0);
        double totalValue = getIntent().getDoubleExtra("total", 0);

        // 设置文本
        item_Name.setText(itemName);
        itemTotal.setText(String.format("$%.2f", itemTotalValue));
        tax.setText(String.format("$%.2f", taxValue));
        delivery.setText(String.format("$%.2f", deliveryValue));
        total.setText(String.format("$%.2f", totalValue));

        // 设置按钮事件
        placeOrder.setOnClickListener(view -> {
            placeOrder(itemName,itemTotalValue,taxValue,deliveryValue,totalValue);
        });
    }
    private void placeOrder(String itemName,Double itemTotalValue,Double taxValue,Double deliveryValue,Double totalValue) {

        ManagementCart managementCart = new ManagementCart(this);
        managementCart.clear();


        Toast.makeText(this, "The order has been completed, thank you for your purchase!", Toast.LENGTH_SHORT).show();



        Intent intent = new Intent(this, OrderHistoryActivity.class);
        startActivity(intent);

        String orderDetails = String.format("Item Name:%s%n",itemName) + String.format("Item Total: $%.2f%n",itemTotalValue)+
                String.format("Tax: $%.2f%n",taxValue) +String.format("Delivery: $%.2f%n",deliveryValue)+String.format("Total: $%.2f%n",totalValue);
      
         
        OrderHistory orderHistory = new OrderHistory(this);
        orderHistory.addOrder(orderDetails);
//        String orderDetail = "Sample order details";
        OrderModel newOrder = new OrderModel(String.valueOf(System.currentTimeMillis()), orderDetails);
        orderHistoryList.add(newOrder);
        startActivity(new Intent(OrderActivity.this, OrderHistoryActivity.class));

        finish();

    }
}

