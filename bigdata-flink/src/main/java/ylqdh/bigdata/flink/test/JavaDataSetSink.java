package ylqdh.bigdata.flink.test;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.core.fs.FileSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JavaDataSetSink
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/1/16 12:00
 */
public class JavaDataSetSink {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        List<Integer> info = new ArrayList<>();
        for (int i = 1; i<=100; i++) {
            info.add(i);
        }

        DataSource<Integer> data = env.fromCollection(info);

        String filePath = "file:///C:\\data\\cleandata\\test-java";
        data.writeAsText(filePath, FileSystem.WriteMode.OVERWRITE);

        List<Tuple4<Integer,String,String,String>> tt = new ArrayList<>();
        tt.add(new Tuple4<>(1,"a","100001","beijing"));
        tt.add(new Tuple4<>(2,"b","100002","shanghai"));
        tt.add(new Tuple4<>(3,"c","100003","shenzhen"));
        tt.add(new Tuple4<>(4,"d","100004","guangzhou"));
        tt.add(new Tuple4<>(5,"e","100005","hangzhou"));
        tt.add(new Tuple4<>(6,"f","100006","chengdu"));
        tt.add(new Tuple4<>(7,"g","100007","changsha"));

        DataSource<Tuple4<Integer,String,String,String>> data2 = env.fromCollection(tt);

        data2.writeAsCsv(filePath+"java-csv","\n",",", FileSystem.WriteMode.OVERWRITE);

        env.execute("JavaDataSetSink");
    }
}
