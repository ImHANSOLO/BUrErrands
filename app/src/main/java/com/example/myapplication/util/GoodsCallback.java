package com.example.myapplication.util;

public interface GoodsCallback {
    void checkedStore(int shopId,boolean state);
    /**
     * calculate price
     */
    void calculationPrice();
}
