package ylqdh.bigdata.flink.test;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.collection.immutable.Stream;

/**
 * @ClassName JavaCustomSinkToMysql
 * @Description TODO 用Java语言实现如下需求：socket发送数据过来，把string类型转成对象，然后把Java对象保存到MySQL中
 *                   再用scala语言实现一次
 *             MySQL创表语句：
 *                   create table student (id int NOT NULL AUTO_INCREMENT primary key, name varchar(256), age int);
 *
 * @Author ylqdh
 * @Date 2020/2/24 14:43
 */
public class JavaCustomSinkToMysql {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从socket获取数据,数据格式: id,name,age --> 1,ylqdh,20
        DataStreamSource<String> sourceData = env.socketTextStream("172.16.13.143",7777);

        // 把输入的数据转换成student对象
        SingleOutputStreamOperator<StudentPOJO> student = sourceData.map(new MapFunction<String, StudentPOJO>() {
            @Override
            public StudentPOJO map(String s) throws Exception {
                String[] splits = s.split(",");
                StudentPOJO stu = new StudentPOJO();
                stu.setId(Integer.parseInt(splits[0]));
                stu.setName(splits[1]);
                stu.setAge(Integer.parseInt(splits[2]));

                return stu;
            }
        });

        // 把对象sink到MySQL中
        student.addSink(new SinkToMysql());

        env.execute("JavaCustomSinkToMysql");
    }
}
