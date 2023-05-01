package com.example.myapplication;


import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.chad.library.adapter.base.BaseQuickAdapter;
        import com.chad.library.adapter.base.BaseViewHolder;
        import com.example.myapplication.R;
        import com.example.myapplication.Order;

import java.util.List;

public class DeliveryAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public DeliveryAdapter() {
        super(R.layout.item_delivery);
    }


    public void setNewInstance(List<Order> orders) {
        setNewData(orders);
        // 更新适配器的数据源并刷新RecyclerView
    }



    @Override
    protected void convert(BaseViewHolder helper, Order item) {
        helper.setText(R.id.order_id, "Order ID: " + item.getOrderNumber())
                .setText(R.id.order_name, "Order Name: " + item.getOrderName())
                .setText(R.id.start_address, "Start Address: " + item.getStartAddress())
                .setText(R.id.end_address, "End Address: " + item.getEndAddress());

        Button acceptOrderButton = helper.getView(R.id.accept_order_button);
        acceptOrderButton.setOnClickListener(v -> {
            // Perform actions when the accept order button is clicked
            Toast.makeText(helper.itemView.getContext(), "Order accepted: " + item.getOrderNumber(), Toast.LENGTH_SHORT).show();
        });
    }
}
