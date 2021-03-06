package ylqdh.bigdata.flink.test;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @ClassName JavaDataStreamExecuEnvironmentApp
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/2/21 16:27
 */
public class JavaDataStreamExecuEnvironmentApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        socketStream(env);
//        CustomNonParallellSourceFunction(env);
//        CustomParallellSourceFunction(env);
        CustomRichParallellSourceFunction(env);

        env.execute("JavaDataStreamExecuEnvironmentApp");
    }

    // rich custom parallel source function
    public static void CustomRichParallellSourceFunction(StreamExecutionEnvironment env) {
        DataStreamSource data = env.addSource(new JavaCustomRichParallelSourceFunction()).setParallelism(2);
        data.print();
    }

    // custom parallel source function
    public static void CustomParallellSourceFunction(StreamExecutionEnvironment env) {
        DataStreamSource data = env.addSource(new JavaCustomParallelSourceFunction()).setParallelism(2);
        data.print();
    }

    // custom non parallel source function
    public static void CustomNonParallellSourceFunction(StreamExecutionEnvironment env) {
        DataStreamSource data = env.addSource(new JavaCustomNonParallelSourceFunction());
        data.print();
    }

    // socket dataSource
    public static void socketStream(StreamExecutionEnvironment env) {
        DataStream<Tuple2<String,Integer>> text = env.socketTextStream("172.16.13.143",9999)
                .flatMap(new Splitter())
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1);

        text.print();
    }

    public static class Splitter implements FlatMapFunction<String,Tuple2<String,Integer>>{
        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            for (String word:s.split(" ")) {
                collector.collect(new Tuple2<String,Integer>(word,1));
            }
        }
    }
}
