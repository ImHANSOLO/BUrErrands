package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapplication.adapter.StoreAdapter;
import com.example.myapplication.util.Constant;
import com.example.myapplication.util.GoodsCallback;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoodsCallback, View.OnClickListener {
    public static final String TAG = "MainActivity";

    private RecyclerView rvStore;
    private List<ShoppingCartResponse.OrderDataBean> mList = new ArrayList<>();
    private StoreAdapter storeAdapter;
    private TextView tvEdit;
    private ImageView ivCheckedAll;
    private TextView tvTotal;
    private TextView tvSettlement;
    private LinearLayout layEdit;
    private TextView tvShareGoods;
    private TextView tvCollectGoods;
    private TextView tvDeleteGoods;

    private boolean isEdit = false;
    private boolean isAllChecked = false;

    private double totalPrice = 0.00;//total product price
    private int totalCount = 0;//total product number

    private SmartRefreshLayout refresh;//
    private LinearLayout layEmpty;//

    private List<Integer> shopIdList = new ArrayList<>();//store list
    private AlertDialog dialog;//Pop-ups

    private boolean isHaveGoods = false;//Whether there are products in the shopping cart


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        rvStore = findViewById(R.id.rv_store);
        tvEdit = findViewById(R.id.tv_edit);
        ivCheckedAll = findViewById(R.id.iv_checked_all);
        tvTotal = findViewById(R.id.tv_total);
        tvSettlement = findViewById(R.id.tv_settlement);
        layEdit = findViewById(R.id.lay_edit);
        tvShareGoods = findViewById(R.id.tv_share_goods);
        tvCollectGoods = findViewById(R.id.tv_collect_goods);
        tvDeleteGoods = findViewById(R.id.tv_delete_goods);
        refresh = findViewById(R.id.refresh);
        layEmpty = findViewById(R.id.lay_empty);

        refresh.setEnableRefresh(false);
        refresh.setEnableLoadMore(false);

        refresh.setOnRefreshListener(refreshLayout -> initView());

        tvEdit.setOnClickListener(this);
        ivCheckedAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        tvShareGoods.setOnClickListener(this);
        tvCollectGoods.setOnClickListener(this);
        tvDeleteGoods.setOnClickListener(this);
        ShoppingCartResponse carResponse = new Gson().fromJson(Constant.CAR_JSON, ShoppingCartResponse.class);

        mList.addAll(carResponse.getOrderData());
        storeAdapter = new StoreAdapter(R.layout.item_store, mList,this);
        rvStore.setLayoutManager(new LinearLayoutManager(this));
        rvStore.setAdapter(storeAdapter);


        storeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShoppingCartResponse.OrderDataBean storeBean = mList.get(position);
                if(view.getId() == R.id.iv_checked_store){
                    storeBean.setChecked(!storeBean.isChecked());
                    storeAdapter.notifyDataSetChanged();

                    if(storeBean.isChecked()){
                        storeAdapter.controlGoods(storeBean.getShopId(),true);
                        if(!shopIdList.contains(storeBean.getShopId())){
                            shopIdList.add(storeBean.getShopId());
                        }
                    }else{
                        storeAdapter.controlGoods(storeBean.getShopId(),false);
                        if(shopIdList.contains(storeBean.getShopId())){
                            shopIdList.remove((Integer) storeBean.getShopId());
                        }
                    }
                    controlAllChecked();
                }
            }
        });
        //have items
        isHaveGoods = true;
        refresh.finishRefresh();
        layEmpty.setVisibility(View.GONE);
    }
    /**
     *Control all selected, interact with allSelected
     */
    private void controlAllChecked() {
        if(shopIdList.size()== mList.size() && mList.size()!=0){
            //selected all
            ivCheckedAll.setImageDrawable(getDrawable(R.drawable.ic_checked));
            isAllChecked = true;
        }else{
            //not selected all
            ivCheckedAll.setImageDrawable(getDrawable(R.drawable.ic_check));
            isAllChecked = false;
        }
        //calculate price
        calculationPrice();
    }

    @Override
    public void checkedStore(int shopId,boolean state) {
        for (ShoppingCartResponse.OrderDataBean bean : mList) {

            if(shopId == bean.getShopId()){
                bean.setChecked(state);
                storeAdapter.notifyDataSetChanged();
                //Record the shop id of the selected store and add it to a list.
                if (!shopIdList.contains(bean.getShopId()) && state) {
                    //If there is no store Id in the list and the current store is selected
                    shopIdList.add(bean.getShopId());
                }else {
                    if(shopIdList.contains(bean.getShopId())){
                        //Obtain the subscript of the attribute in the list through list.indexOf, but it is more concise to force Integer
                        shopIdList.remove((Integer) bean.getShopId());
                    }
                }
            }
        }
        controlAllChecked();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit://edit
                if(!isHaveGoods){
                    showMsg("There is nothing in the shopping cart!");
                    return;
                }
                if(isEdit){
                    tvEdit.setText("Edit");
                    layEdit.setVisibility(View.GONE);
                    isEdit = false;
                }else {
                    tvEdit.setText("Finish");
                    layEdit.setVisibility(View.VISIBLE);
                    isEdit = true;
                }
                break;
            case R.id.iv_checked_all://select all
                if(!isHaveGoods){
                    showMsg("There is nothing in the shopping cart!");
                    return;
                }
                if(isAllChecked){
                    isSelectAllStore(false);
                }else{
                    isSelectAllStore(true);
                }
                break;
            case R.id.tv_settlement://checkout
                if(!isHaveGoods){
                    showMsg("There is nothing in the shopping cart!");
                    return;
                }
                if (totalCount == 0) {
                    showMsg("Please select the item to checkout");
                    return;
                }
                //pop-ups
                dialog = new AlertDialog.Builder(this)
                        .setMessage("Total:" + totalCount + "items，" + totalPrice + "dollars")
                        .setPositiveButton("Yes", (dialog, which) -> deleteGoods())
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
                break;
            case R.id.tv_delete_goods://delete
                if (totalCount == 0) {
                    showMsg("Please select an item to delete");
                    return;
                }
                //pop-ups
                dialog = new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to delete the selected items?")
                        .setPositiveButton("Yes", (dialog, which) -> deleteGoods())
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
                break;
            case R.id.tv_collect_goods://save
                if (totalCount == 0){
                    showMsg("Please select the product you want to save");
                }
                showMsg("Successfully Saved!");
                break;
            case R.id.tv_share_goods://share
                if (totalCount == 0){
                    showMsg("Please select the product you want to share");
                }
                showMsg("Successfully Shared!");
                break;
            default:
                break;
        }
    }

    /**
     * delete item
     */
    private void deleteGoods() {
        //shop list
        List<ShoppingCartResponse.OrderDataBean> storeList = new ArrayList<>();

        for (int i = 0; i < mList.size(); i++) {
            ShoppingCartResponse.OrderDataBean store = mList.get(i);
            if (store.isChecked()) {
                //Stores are added to this list if selected
                storeList.add(store);
            }
            //product list
            List<ShoppingCartResponse.OrderDataBean.CartlistBean> goodsList = new ArrayList<>();

            List<ShoppingCartResponse.OrderDataBean.CartlistBean> goods = store.getCartlist();
            //loop through the list of items in the shop
            for (int j = 0; j < goods.size(); j++) {
                ShoppingCartResponse.OrderDataBean.CartlistBean cartlistBean = goods.get(j);
                //Items are added to this list if selected
                if (cartlistBean.isChecked()) {
                    goodsList.add(cartlistBean);
                }
            }
            //delete item
            goods.removeAll(goodsList);
        }
        //delete store
        mList.removeAll(storeList);

        shopIdList.clear();//The selected product is deleted, and the selected logo is cleared
        controlAllChecked();//control to select all
        //Change interface UI
        tvEdit.setText("Edit");
        layEdit.setVisibility(View.GONE);
        isEdit = false;
        //Fresh data
        storeAdapter.notifyDataSetChanged();
        if(mList.size()<=0){
            isHaveGoods = false;
            refresh.finishRefresh(true);
            layEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void showMsg(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    private void isSelectAllStore(boolean state) {
        //change background
        ivCheckedAll.setImageDrawable(getDrawable(state ? R.drawable.ic_checked : R.drawable.ic_check));

        for (ShoppingCartResponse.OrderDataBean orderDataBean : mList) {
            //determine if the product is selected
            storeAdapter.controlGoods(orderDataBean.getShopId(), state);
            //determine if the store is selected
            checkedStore(orderDataBean.getShopId(), state);
        }
        isAllChecked = state;
    }
    /**
     * product price
     */
    @Override
    public void calculationPrice() {
        //Set to zero before each calculation
        totalPrice = 0.00;
        totalCount = 0;
        //Loop through the list of stores in the shopping cart
        for (int i = 0; i < mList.size(); i++) {
            ShoppingCartResponse.OrderDataBean orderDataBean = mList.get(i);
            //Loop through the list of items in the shop
            for (int j = 0; j < orderDataBean.getCartlist().size(); j++) {
                ShoppingCartResponse.OrderDataBean.CartlistBean cartList = orderDataBean.getCartlist().get(j);
                //Calculate the quantity and price when there is a selected item
                if (cartList.isChecked()) {
                    totalCount++;
                    totalPrice += cartList.getPrice() * cartList.getCount();
                }
            }
        }
        tvTotal.setText("$" + totalPrice);
        tvSettlement.setText(totalCount == 0 ? "Total" : "Total(" + totalCount + ")");
    }
}