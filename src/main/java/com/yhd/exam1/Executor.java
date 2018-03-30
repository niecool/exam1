package com.yhd.exam1;

import java.lang.management.ManagementFactory;
import com.yhd.exam1.utils.Utils;

public class Executor {
	/**
	 * args[0] 	考试文件存储路径 d:/GITCode/exam1.git/src/main/resources
	 * args[1] 	域账号登录名如：xiaoming14
	 * arg[2]	临时文件存储路径
	 * example:
	 * args[0]
	 * 	|-data
	 * 	|	|-candidateSku.data
	 * 	|-result
	 * 	|	|-args[1]
	 * 	|		|-result-pid.data
	 * 	|		|-result-pid.cost
	 * */
	public static void main(String[] args){
		String pid = ManagementFactory.getRuntimeMXBean().getName();
		//
		System.out.println(pid);
		if(args==null || args.length==0){
			System.out.println("Parameters must not null");
			System.exit(1);
		}
		if(args[0] == null || "".equals(args[0])){
			System.out.println("exam path must not null");
			System.exit(1);
		}
		String input = args[0]+"/data";
		Utils.createFolder(input);
		input = input+"/candidateSku.data";
		if(args[1] == null || "".equals(args[1])){
			System.out.println("login name must not null");
			System.exit(1);
		}
		String output = args[0]+"/results/"+args[1];
		Utils.createFolder(output);
		output = output+"/result.data";

		if(args[2] == null || "".equals(args[2])){
			System.out.println("tmp path must not null");
			System.exit(1);
		}
		String tmp = args[2]+"/"+args[1];
		Utils.createFolder(tmp);

		String costFile = args[0]+"/results/"+args[1]+"/result.cost";
		SkuSelector select = new SkuSelector(input,output,tmp);
		long start = System.currentTimeMillis();
		select.doSelect();
		long end = System.currentTimeMillis();
		Utils.writeFile(costFile,(end-start)+"ms");
	}
}
