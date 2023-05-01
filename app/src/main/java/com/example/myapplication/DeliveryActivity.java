package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.myapplication.R;
import com.example.myapplication.DeliveryAdapter;
import com.example.myapplication.Order;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private DeliveryAdapter deliveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        recyclerView = findViewById(R.id.recyclerView);
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);

        deliveryAdapter = new DeliveryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(deliveryAdapter);

        // Populate the adapter with sample data
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("#202301", "Restaurant name 1 ", "1075 CommonWealth Ave", "455 CommonWealth Ave"));
        orders.add(new Order("#202302", "Restaurant name 2 ", "1075 CommonWealth Ave", "455 CommonWealth Ave"));
        orders.add(new Order("#202303","KFC","1100 Commonwealth Ave","585 Commonwealth Ave"));
        orders.add(new Order("#202304", "Restaurant name 3 ", "1075 CommonWealth Ave", "455 CommonWealth Ave"));
        orders.add(new Order("#202305", "Restaurant name 4 ", "1075 CommonWealth Ave", "455 CommonWealth Ave"));
        orders.add(new Order("#202306", "Restaurant name 5 ", "1075 CommonWealth Ave", "455 CommonWealth Ave"));
        orders.add(new Order("#202307", "Restaurant name 6 ", "1075 CommonWealth Ave", "455 CommonWealth Ave"));


        deliveryAdapter.setNewInstance(orders); //将新的数据列表设置为适配器的数据源

        // Set up refresh listener
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // Perform actions when the layout is refreshed, 下拉刷新
            refreshLayout.finishRefresh(2000); // Set the refresh duration to 2 seconds
        });
    }
}
