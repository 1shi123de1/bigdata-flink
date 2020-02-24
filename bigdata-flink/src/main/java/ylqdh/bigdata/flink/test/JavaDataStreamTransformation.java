package ylqdh.bigdata.flink.test;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JavaDataStreamTransformation
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/2/24 11:51
 */
public class JavaDataStreamTransformation {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        filterFunction(env);
//        unionFunction(env);
        splitSelectFunction(env);

        env.execute("JavaDataStreamTransformation");
    }

    public static void splitSelectFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new JavaCustomNonParallelSourceFunction());

        SplitStream splits = data.split(new OutputSelector<Long>() {
            @Override
            public Iterable<String> select(Long value) {
                List list = new ArrayList<String>();
                if (value %2 == 0) {
                    list.add("even");
                } else {
                    list.add("odd");
                }
                return list;
            }
        });
        splits.select("odd").print();
    }

    public  static void unionFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data1 = env.addSource(new JavaCustomNonParallelSourceFunction());
        DataStreamSource<Long> data2 = env.addSource(new JavaCustomNonParallelSourceFunction());
        data1.union(data2).print();
    }

    public static void filterFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new JavaCustomNonParallelSourceFunction());
        data.map(new MapFunction<Long, Long>() {

            @Override
            public Long map(Long aLong) throws Exception {
                System.out.println("received: "+ aLong);
                return aLong;
            }
        }).filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long aLong) throws Exception {
                return aLong % 2 == 0;
            }
        }).print().setParallelism(1);
    }
}
