package ylqdh.bigdata.flink.test;

import org.apache.avro.generic.GenericData;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import scala.Int;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JavaDataSetTransformation
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/1/15 9:23
 */
public class JavaDataSetTransformation {
    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

//        mapFunction(env);
//        filterFunction(env);
//        mapPartitionFunction(env);
//        firstFunction(env);
//        flatMapFunction(env);
        distinctFunction(env);
    }

    // map
    public static void mapFunction(ExecutionEnvironment env) throws Exception {
        List<Integer> data = new ArrayList<Integer>();
        for (int i = 1; i<=10; i++) {
            data.add(i);
        }

        DataSet<Integer> dataSet = env.fromCollection(data);

        dataSet.map(new MapFunction<Integer, Integer>() {
            public Integer map(Integer value) throws Exception {
                return value+1;
            }
        }).print();
    }

    // filter
    public static void filterFunction(ExecutionEnvironment env) throws Exception {

        List<Integer> data = new ArrayList<Integer>();
        for (int i = 1; i<=10; i++) {
            data.add(i);
        }

        DataSet<Integer> dataSet = env.fromCollection(data);

        dataSet.map(new MapFunction<Integer, Integer>() {
            public Integer map(Integer value) throws Exception {
                return value+1;
            }
        }).filter(new FilterFunction<Integer>() {
                    @Override
                    public boolean filter(Integer value) throws Exception {
                        return value>5;
                    }
                })
                .print();
        System.out.println("filter function : 过滤出大于5的值");
    }

    public static void mapPartitionFunction(ExecutionEnvironment env) throws Exception {
        List<String> students = new ArrayList<>();
        for (int x=0; x<100; x++) {
            students.add("student : " + x);
        }

        DataSet<String> data = env.fromCollection(students).setParallelism(5);

        data.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                String connection = DBUtils.getConnection();
                System.out.println("connection: " + connection);
                DBUtils.returnConnection(connection);
                return s;
            }
        });

        // 使用map和mapPartition对比，看看哪个使用数据库连接多
        data.mapPartition(new MapPartitionFunction<String, String>() {
            @Override
            public void mapPartition(Iterable<String> s, Collector<String> collector) throws Exception {

                String connection = DBUtils.getConnection();
                System.out.println("connection: " + connection);
                DBUtils.returnConnection(connection);
            }
        }).print();
    }

   // first
    public static void firstFunction(ExecutionEnvironment env) throws Exception {
        List<Tuple2<Integer,String>> info = new ArrayList<>();
        info.add(new Tuple2(1,"Hadoop"));
        info.add(new Tuple2(1,"Spark"));
        info.add(new Tuple2(1,"Flink"));
        info.add(new Tuple2(2,"Scala"));
        info.add(new Tuple2(2,"Java"));
        info.add(new Tuple2(2,"Python"));
        info.add(new Tuple2(3,"Linux"));
        info.add(new Tuple2(3,"Window"));
        info.add(new Tuple2(3,"MacOS"));

        DataSet<Tuple2<Integer,String>> dataSet = env.fromCollection(info);

        dataSet.first(3).print();
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        dataSet.groupBy(0).first(2).print();
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        dataSet.groupBy(0).sortGroup(1, Order.DESCENDING).first(2).print();

    }

    // flatMap
    public static void flatMapFunction (ExecutionEnvironment env) throws Exception {
        List<String> info = new ArrayList<>();
        info.add("Hadoop,Spark");
        info.add("Spark,Flink");
        info.add("Hadoop,Flink");

        DataSource<String> dataSource = env.fromCollection(info);

        dataSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {

                String[] splits = s.split(",");
                for (String split:splits) {
                    collector.collect(split);
                }
            }
        })
        .map(new MapFunction<String, Tuple2<String,Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String s) throws Exception {
                return new Tuple2<String,Integer>(s,1);
            }
        })
        .groupBy(0)
        .sum(1)
        .print();
    }

    // distinct
    public static void distinctFunction (ExecutionEnvironment env) throws Exception {
        List<String> info = new ArrayList<>();
        info.add("Hadoop,Spark");
        info.add("Spark,Flink");
        info.add("Hadoop,Flink");

        DataSource<String> dataSource = env.fromCollection(info);

        dataSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {

                String[] splits = s.split(",");
                for (String split:splits) {
                    collector.collect(split);
                }
            }
        }).distinct().print();
    }

}
