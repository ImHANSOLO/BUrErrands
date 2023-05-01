package com.example.myapplication.util;

import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.R;

import java.util.List;

public class OrderListAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    public OrderListAdapter() {
        super(R.layout.order_layout);
    }


    public void setInstance(List<OrderInfo> orders) {
        setNewData(orders);
        // 更新适配器的数据源并刷新RecyclerView
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo order) {
        helper.setText(R.id.order_id, "Order ID: " + order.getOrderID())
                .setText(R.id.item_name,  order.getitemname())
                .setText(R.id.Delivery_ID, "Delivery ID:" + order.getDeliveryID())
                .setText(R.id.Customer_ID,"Customer ID: " + order.getCustomerID())
                .setText(R.id.order_status,  order.getOrder_status());
        helper.itemView.getContext();
    }
}
