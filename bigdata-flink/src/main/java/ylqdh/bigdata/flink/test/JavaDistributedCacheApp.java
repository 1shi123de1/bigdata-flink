package ylqdh.bigdata.flink.test;

import org.apache.commons.io.FileUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JavaDistributedCacheApp
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/2/21 10:47
 */
public class JavaDistributedCacheApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String filePath = "file:///C:\\data\\cleandata\\test\\hello.txt";
        // step1. 注册一个本地/HDFS文件
        env.registerCachedFile(filePath,"ylqdh-java-dc");

        DataSource<String> data = env.fromElements("Hadoop","Flink","Spark","Python","HBase","Hive");

        data.map(new RichMapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                return s;
            }

            List<String> list = new ArrayList<>();
            @Override
            public void open(Configuration parameters) throws Exception {
                File filedc = getRuntimeContext().getDistributedCache().getFile("ylqdh-java-dc");
                List<String> lines = FileUtils.readLines(filedc);
                for (String ele : lines) {
                    list.add(ele);
                    System.out.println(ele);
                }
            }
        }).print();

    }
}
