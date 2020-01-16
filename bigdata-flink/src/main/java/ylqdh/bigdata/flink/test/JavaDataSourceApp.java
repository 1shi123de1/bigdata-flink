package ylqdh.bigdata.flink.test;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JavaDataSourceApp
 * @Description TODO java api 各种创建数据集的方式
 * @Author ylqdh
 * @Date 2020/1/13 14:59
 */
public class JavaDataSourceApp {
    public static void main(String[] args) {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

//     fromCollection(env);
//        readFile(env);
//        readCSV(env);
        readRecuDir(env);
    }

//  读text文件创建数据集,也可以是压缩文件
    public static void readFile(ExecutionEnvironment environment) {
        String filePath = "file:///C:\\data\\scalaCode\\HelloWorld.gz";

        try {
            environment.readTextFile(filePath).print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//  递归的从文件夹下的所有文件
    public static void readRecuDir (ExecutionEnvironment environment) {
        String filePath = "file:///C:\\data\\cleandata";

        Configuration parameter = new Configuration();
        parameter.setBoolean("recursive.file.enumeration",true);
        try {
            environment.readTextFile(filePath)
                    .withParameters(parameter)
                    .print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//   读csv文件创建数据集
    public static void readCSV(ExecutionEnvironment environment) {
        String filePath = "file:///C:\\data\\person.csv";

        DataSet<PersonPOJO> csvPojo = environment.readCsvFile(filePath)
                .ignoreFirstLine()
                .pojoType(PersonPOJO.class,"name","age","level");
        try {
            csvPojo.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 集合方式创建
    public static void fromCollection(ExecutionEnvironment environment) {
        List list = new ArrayList();
        for (int i=0;i<10;i++) {
            list.add(i);
        }

        try {
            environment.fromCollection(list).print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
