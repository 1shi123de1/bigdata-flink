package ylqdh.bigdata.flink.test;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @ClassName BatchFlinkWC
 * @Description TODO 使用Java API 开发Flink的批处理
 * @Author ylqdh
 * @Date 2020/1/6 15:56
 */
public class BatchFlinkWC {
    public static void main(String[] args) throws Exception {
        String input = "file:///C:\\data\\hello";

        // step1. 获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // step2. 读数据
        DataSource<String> text = env.readTextFile(input);

//        text.print();

        // step3. transform
        text.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {


            public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {

                String[] tokens = value.toLowerCase().split("\t");
                for (String token:tokens) {
                    if(token.length()>0) {
                        collector.collect(new Tuple2<String, Integer>(token,1));
                    }
                }
            }
        }).groupBy(0).sum(1).print();

        // step4. 保存数据
    }
}
