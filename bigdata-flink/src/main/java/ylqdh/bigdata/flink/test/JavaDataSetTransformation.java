package ylqdh.bigdata.flink.test;

import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

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
//        distinctFunction(env);
//        joinFunction(env);
//        outerJoinFunction(env);
        crossFunction(env);
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

        DataSource<Integer> dataSet = env.fromCollection(data);

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

    // mapPartition
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

    // join
    public static void joinFunction(ExecutionEnvironment env) throws Exception {
        List<Tuple2<Integer,String>> info1 = new ArrayList<>();
        info1.add(new Tuple2<>(1,"可达鸭"));
        info1.add(new Tuple2<>(2,"ylqdh"));
        info1.add(new Tuple2<>(3,"皮卡丘"));
        info1.add(new Tuple2<>(4,"鲤鱼王"));

        List<Tuple2<Integer,String>> info2 = new ArrayList<>();
        info2.add(new Tuple2<>(1,"深圳"));
        info2.add(new Tuple2<>(2,"广州"));
        info2.add(new Tuple2<>(3,"上海"));
        info2.add(new Tuple2<>(5,"杭州"));

        DataSource<Tuple2<Integer,String>> data1 = env.fromCollection(info1);
        DataSource<Tuple2<Integer,String>> data2 = env.fromCollection(info2);

        data1.join(data2).where(0).equalTo(0)
                .with(new JoinFunction<Tuple2<Integer,String>, Tuple2<Integer,String>, Tuple3<Integer,String,String>>() {
                    @Override
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                        return new Tuple3<Integer,String,String>(first.f0,first.f1,second.f1);
                    }
                })
                .print();
    }

    // outer join
    public static void outerJoinFunction (ExecutionEnvironment env) throws Exception {
        List<Tuple2<Integer,String>> info1 = new ArrayList<>();
        info1.add(new Tuple2<>(1,"可达鸭"));
        info1.add(new Tuple2<>(2,"ylqdh"));
        info1.add(new Tuple2<>(3,"皮卡丘"));
        info1.add(new Tuple2<>(4,"鲤鱼王"));

        List<Tuple2<Integer,String>> info2 = new ArrayList<>();
        info2.add(new Tuple2<>(1,"深圳"));
        info2.add(new Tuple2<>(2,"广州"));
        info2.add(new Tuple2<>(3,"上海"));
        info2.add(new Tuple2<>(5,"杭州"));

        DataSource<Tuple2<Integer,String>> data1 = env.fromCollection(info1);
        DataSource<Tuple2<Integer,String>> data2 = env.fromCollection(info2);

        System.out.println("~~~~~~~left outer join~~~~~~~~~~~~");
        // left outer join
        data1.leftOuterJoin(data2).where(0).equalTo(0)
                .with(new JoinFunction<Tuple2<Integer,String>, Tuple2<Integer,String>, Tuple3<Integer,String,String>>() {
                    @Override
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                       if (second == null) {
                           return new Tuple3<>(first.f0,first.f1,"--");
                       } else {
                           return new Tuple3<Integer,String,String>(first.f0,first.f1,second.f1);
                       }
                    }
                })
                .print();

        System.out.println("~~~~~~~~right outer join~~~~~~~~~~~~");
        // right outer join
        data1.rightOuterJoin(data2).where(0).equalTo(0)
                .with(new JoinFunction<Tuple2<Integer,String>, Tuple2<Integer,String>, Tuple3<Integer,String,String>>() {
                    @Override
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                        if (first == null) {
                            return new Tuple3<>(second.f0,"--",second.f1);
                        } else {
                            return new Tuple3<Integer,String,String>(first.f0,first.f1,second.f1);
                        }
                    }
                })
                .print();

        System.out.println("~~~~~~~~~full join~~~~~~~~~~~");
        // left outer join
        data1.fullOuterJoin(data2).where(0).equalTo(0)
                .with(new JoinFunction<Tuple2<Integer,String>, Tuple2<Integer,String>, Tuple3<Integer,String,String>>() {
                    @Override
                    public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                        if (second == null) {
                            return new Tuple3<>(first.f0,first.f1,"--");
                        } else if (first == null) {
                            return new Tuple3<>(second.f0,"--",second.f1);
                        } else {
                            return new Tuple3<Integer,String,String>(first.f0,first.f1,second.f1);
                        }
                    }
                })
                .print();
    }

    // cross
    public static void crossFunction (ExecutionEnvironment env) throws Exception {
        List<String> info1 = new ArrayList<>();
        info1.add("team A");
        info1.add("team B");

        List<Integer> info2 = new ArrayList<>();
        info2.add(0);
        info2.add(1);
        info2.add(2);

        DataSource<String> data1 = env.fromCollection(info1);
        DataSource<Integer> data2 = env.fromCollection(info2);

        data1.cross(data2).print();
    }

}
