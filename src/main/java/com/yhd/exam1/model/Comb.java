package com.yhd.exam1.model;

import com.yhd.exam1.utils.Utils;

public class Comb {
    String combSku;
    double combPrice;
    double profit;

    public Comb(Sku sku1, Sku sku2) {
        this.combSku = sku1.getSku() + "|" + sku2.getSku();
        this.combPrice = Utils.computeCombPrice(sku1.getPrice1(), sku1.getPrice2(), sku2.getPrice1(), sku2.getPrice2());
        this.profit = Utils.computeProfitRate(this.combPrice, sku1.getPrice0(), sku2.getPrice0());
    }

    public String getCombSku() {
        return combSku;
    }

    public void setCombSku(String combSku) {
        this.combSku = combSku;
    }

    public double getCombPrice() {
        return combPrice;
    }

    public void setCombPrice(double combPrice) {
        this.combPrice = combPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
