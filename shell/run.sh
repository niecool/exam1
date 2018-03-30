#!/bin/sh
LIBDIR=/var/yhd/exam/exam1/lib
CLASSPATH=$LIBDIR/gson-2.7.jar:$LIBDIR/hedwig-zk-0.2.4-SNAPSHOT.jar:$LIBDIR/zookeeper-3.4.7.jar:$LIBDIR/log4j-1.2.16.jar:$LIBDIR/slf4j-api-1.6.2.jar:$LIBDIR/slf4j-log4j12-1.6.2.jar

# 使用多个实例并行运行
#n=1
#for i in {1..$n}
#do
    java -cp $CLASSPATH:exam1-1.0-SNAPSHOT.jar com.yhd.exam1.Executor /var/yhd/exam/exam1 santa /tmp
#done