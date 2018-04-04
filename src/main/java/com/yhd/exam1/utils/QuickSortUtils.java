package com.yhd.exam1.utils;

import com.yhd.exam1.model.Comb;

public class QuickSortUtils {

    public static int partition(Comb[] array, int lo, int hi) {
        //三数取中
        int mid = lo + (hi - lo) / 2;
        if (array[mid].getProfit() > array[hi].getProfit()) {
            swap(array[mid], array[hi]);
        }
        if (array[lo].getProfit() > array[hi].getProfit()) {
            swap(array[lo], array[hi]);
        }
        if (array[mid].getProfit() > array[lo].getProfit()) {
            swap(array[mid], array[lo]);
        }
        double key = array[lo].getProfit();

        while (lo < hi) {
            while (array[hi].getProfit() >= key && hi > lo) {
                hi--;
            }
            array[lo] = array[hi];
            while (array[lo].getProfit() <= key && hi > lo) {
                lo++;
            }
            array[hi] = array[lo];
        }
        array[hi].setProfit(key);
        return hi;
    }

    public static void swap(Comb a, Comb b) {
        Comb temp = a;
        a = b;
        b = temp;
    }

    public static void sort(Comb[] array, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int index = partition(array, lo, hi);
        sort(array, lo, index - 1);
        sort(array, index + 1, hi);
    }
}
