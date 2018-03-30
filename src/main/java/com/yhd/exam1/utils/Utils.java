package com.yhd.exam1.utils;

import com.yhd.exam1.model.Comb;
import com.yhd.exam1.model.Sku;

import java.io.*;

public class Utils {

    public static String readFile(String path) {
        String v = null;
        if (!isBlank(path)) {
            if (path != null) {
                FileReader fr = null;
                try {
                    fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        sb.append(line);
                        line = br.readLine();
                    }
                    v = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fr != null) {
                        try {
                            fr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return v;
    }

    public static void writeFile(String path, String content) {
        if (!isBlank(path) && !isBlank(content)) {
            FileWriter writer = null;
            try {
                writer = new FileWriter(path);
                writer.append(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void createFolder(String path) {
        if (!isBlank(path)) {
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
    }

    public static boolean isBlank(String val) {
        if (val == null || "".equals(val)) {
            return true;
        } else {
            return false;
        }
    }

    public static double round(double value, int scale) {
        int realScale = 1;
        while (--scale >= 0) {
            realScale = realScale * 10;
        }
        return (double) Math.round(value * realScale) / realScale;
    }

    /**
     * 计算组合品价格
     *
     * @param x1 price1 低价
     * @param y1 price1 高价
     * @param x2 price2 低价
     * @param y2 price2 高价
     * @return
     */
    public static double computeCombPrice(double x1, double y1, double x2, double y2) {
        double price = (x1 + y1 + x2 + y2) / 2 + Math.log(2.5 + (Math.max(x1, x2) + Math.max(y1, y2)) - (Math.max(x1, x2) + Math.max(y1, y2)));
        return round(price, 2);
    }

    /**
     * 计算利润率
     *
     * @param comb 组合品价格
     * @param x    商品1一号店价格
     * @param y    商品2一号店价格
     * @return
     */
    public static double computeProfitRate(double comb, double x, double y) {
        return round((comb - (x + y)) / (x + y), 4);
    }

    public static Comb[] concatArray(Comb[] combs1, Comb[] combs2) {
        Comb[] combs = new Comb[combs1.length + combs2.length];
        for (int i = 0; i < combs1.length; i++) {
            combs[i] = combs1[i];
        }

        for (int j = combs1.length; j < combs.length; j++) {
            combs[j] = combs[j - combs1.length];
        }
        return combs;
    }

}
