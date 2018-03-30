package com.yhd.exam1;

import com.yhd.exam1.utils.Utils;

public class Test {

    public static void main(String[] args) {
        System.out.println(Utils.round(3.1,4));
        System.out.println((3.14+3.16)/2);

        double price = Utils.computeCombPrice(12.523,14.512,6.52,8.5212);
        price = Utils.computeProfitRate(21.5,12,8);
        System.out.println(price*100+"%");
    }
}
