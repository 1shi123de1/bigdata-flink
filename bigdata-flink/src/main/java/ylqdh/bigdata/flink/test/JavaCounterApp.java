package ylqdh.bigdata.flink.test;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.LongCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;

/**
 * @ClassName JavaCounterApp
 * @Description TODO 计数器 Java 实现
 * @Author ylqdh
 * @Date 2020/1/16 17:13
 */
public class JavaCounterApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<String> data = env.fromElements("Hadoop","Flink","Spark","Python","HBase","Hive");

        DataSet<String> data2 = data.map(new RichMapFunction<String, String>() {

            // step1.
            LongCounter counter = new LongCounter();

            @Override
            public void open(Configuration parameters) throws Exception {
                // step2.
                getRuntimeContext().addAccumulator("counter-java-app",counter);
            }

            @Override
            public String map(String s) throws Exception {
                counter.add(1);
                return s;
            }
        });

        String filePath = "file:///C:\\data\\cleandata\\test-java";
        data2.writeAsText(filePath, FileSystem.WriteMode.OVERWRITE).setParallelism(5);  // 触发数据保存

        JobExecutionResult jobExecutionResult = env.execute("JavaCounterApp");
        // step3.
        Long num = jobExecutionResult.getAccumulatorResult("counter-java-app");

        System.out.println("counter num = [" + num + "]");
    }
}
