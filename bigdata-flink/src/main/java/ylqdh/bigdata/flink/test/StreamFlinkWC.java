package ylqdh.bigdata.flink.test;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.omg.Dynamic.Parameter;

/**
 * @ClassName StreamFlinkWC
 * @Description TODO 通过Java api，处理Flink 实时数据，socket过来的数据
 * @Author ylqdh
 * @Date 2020/1/6 17:39
 */
public class StreamFlinkWC {
    public static void main(String[] args) throws Exception {
        int port = 0;

       try {
           ParameterTool tool = ParameterTool.fromArgs(args);
           port = tool.getInt("port");
       } catch (Exception e) {
           port = 9999;
           System.out.println("set default port as 9999");
       }

        // step1. get environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // step2. read data
        DataStreamSource<String> text = env.socketTextStream("172.16.13.151",port);

        // step3. transform
        text.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {
            public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = value.toLowerCase().split(",");
                for (String token:tokens) {
                    if(token.length()>0) {
                        collector.collect(new Tuple2<String, Integer>(token,1));
                    }
                }
            }
        }).keyBy(0).timeWindow(Time.seconds(5)).sum(1).setParallelism(1).print();

        env.execute("StreamFlinkApp");
    }
}
