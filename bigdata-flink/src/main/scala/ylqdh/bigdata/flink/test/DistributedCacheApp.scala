package ylqdh.bigdata.flink.test

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

/**
  * @ClassName DistributedCacheApp
  * @Description TODO 分布式缓存例子
  *             step1. 注册一个分布式缓存，本地文件或者HDFS文件
  *             step2. 使用分布式缓存，从名字读取
  * @Author ylqdh
  * @Date 2020/2/21 10:25
  */
object DistributedCacheApp {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val filePath = "file:///C:\\data\\cleandata\\test\\hello.txt"
    // step1. 注册一个本地/HDFS文件
    env.registerCachedFile(filePath,"ylqdh-scala-dc")

    import org.apache.flink.api.scala._
    val data = env.fromElements("Hadoop","Flink","Spark","Python","HBase","Hive")

    data.map(new RichMapFunction[String,String] {


      override def open(parameters: Configuration) = {

        // step2. 在open方法中获取到分布式缓存即可
        val dcFile = getRuntimeContext.getDistributedCache().getFile("ylqdh-scala-dc")

        val lines = FileUtils.readLines(dcFile)

        // 此时出现一个异常，Java集合和scala集合不兼容，集合循环报错,需要把集合转换为scala集合
        import scala.collection.JavaConverters._
        for (ele <- lines.asScala) {
          println(ele)
        }
      }

      override def map(in: String) = {
        in
      }
    }).print()
  }
}
