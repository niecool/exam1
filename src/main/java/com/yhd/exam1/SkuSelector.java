package com.yhd.exam1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhd.exam1.model.Comb;
import com.yhd.exam1.model.Sku;
import com.yhd.exam1.utils.QuickSortUtils;
import com.yhd.exam1.utils.TopK;
import com.yhd.exam1.utils.Utils;
import com.yihaodian.architecture.zkclient.ZkClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SkuSelector implements ISkuSelector {

    private String dataPath;
    private String resultPath;
    private String tmpPath;
    private ZkClient zkClient;

    public SkuSelector(String input, String output, String tmp) {
        dataPath = input;
        resultPath = output;
        tmpPath = tmp;
        zkClient = new ZkClient("113.209.26.156:2181", Integer.MAX_VALUE);
    }

    public void doSelect() {
        System.out.println(toString());
        System.out.println("begin doSelect!");
        //1.两两组合,C30000,2 包含自身,有30000*2999/2+30000=900 000 000 9亿种组合。如果按照文件排序有3万个文件，每个文件里面有3万个组合品。
        try {
            //1.读取文件，转换List
            Long t1 = System.currentTimeMillis();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(dataPath));
            Long t2 = System.currentTimeMillis();
            ObjectMapper mapper1 = new ObjectMapper();
            List<Sku> skus = mapper1.readValue(new File(dataPath), new TypeReference<List<Sku>>() {
            });
            Long t3 = System.currentTimeMillis();
            System.out.println("读取原始数据耗时（ms）：" + (t2 - t1));
            System.out.println("转换List对象耗时（ms）：" + (t3 - t2));

            //2.遍历对象
            Comb[] combsArray = new Comb[skus.size() * 100];
            Comb[] top30w = new Comb[300000];
            Comb[] top1000 = new Comb[1000];
            int flag2=0;//当前游标
            for (int i = 1; i < skus.size()+1; i++) {

                for (int j = 0; j < skus.size(); j++) {
                    //3.生成组合品
                    combsArray[((i-1) % 100) * 30000 + j] = new Comb(skus.get(i-1), skus.get(j));
                }
                if ( i % 100 == 0) {
                    //4.快速从300万数据中获取前1000个组合品
                    TopK topk = new TopK();
                    top1000 = topk.topK(combsArray, 1000);

                    System.out.println(i / 100);
//                    String jsonlist = mapper1.writeValueAsString(top1000);
//                    //5.序列化成文件
//                    Utils.writeFile(tmpPath + '/' + i, jsonlist);
//                    System.out.println("创建文件费时：" + (System.currentTimeMillis() - t3));

                    for (int k = 0; k < 1000; k++) {
                        top30w[flag2*1000+k] = top1000[k];
                    }
                    flag2++;
                }
            }
            Long t4 = System.currentTimeMillis();
            System.out.println("排序耗时：" + (t4 - t3));
            //排序优选的30万数据，选择前1000种写入文件
            String jsonlist1 = mapper1.writeValueAsString(top30w);
            Utils.writeFile(tmpPath + "/30w.data", jsonlist1);
            //读数据
//            top30w = mapper1.readValue(new File(tmpPath + "/30w.data"), new TypeReference<Comb[]>() {});
            QuickSortUtils.sort(top30w, 0, top30w.length - 1);
            System.arraycopy(top30w, 298999, top1000, 0, 1000);
            String jsonlist = mapper1.writeValueAsString(top1000);
            Utils.writeFile(resultPath, jsonlist);
            Long t5 = System.currentTimeMillis();
            System.out.println("写结果文件耗时：" + (t5 - t4));
            System.out.println("==========总耗时：" + (t5 - t1) + "==========：");
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        //2.对每个文件进行按照利润率排序(并行);

        //3.所有结束后进行归并排序。得到最大1000名。


    }

    @Override
    public String toString() {
        return "SkuSelector{" +
                "dataPath='" + dataPath + '\'' +
                ", resultPath='" + resultPath + '\'' +
                ", tmpPath='" + tmpPath + '\'' +
                '}';
    }
}
