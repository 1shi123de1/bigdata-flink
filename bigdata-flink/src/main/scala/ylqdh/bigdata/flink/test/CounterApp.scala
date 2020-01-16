package ylqdh.bigdata.flink.test

import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode
import org.apache.flink.api.scala._

/**
  * @ClassName CounterApp
  * @Description Flink 编程的计数器
  * @Author ylqdh
  * @Date 2020/1/16 16:52
  *
  *      step1.定义计数器
  *      step2.注册计数器
  *      step3.获取计数器
  */
//noinspection ScalaDocUnknownTag
object CounterApp {
  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val data = env.fromElements("Hadoop","Flink","Spark","Python","HBase","Hive")

    val info = data.map(new RichMapFunction[String,String]() {

      // step1. 定义计数器
      val counter = new LongCounter()


      override def open(parameters: Configuration) = {
        // step2. 注册计数器
        getRuntimeContext.addAccumulator("counter-scala-app",counter)
      }

      override def map(in: String):String = {
        counter.add(1)
        in
      }
    })

    val filePath = "file:///C:\\data\\cleandata\\test"
    info.writeAsText(filePath,WriteMode.OVERWRITE).setParallelism(5)

    val jobResult = env.execute("CounterApp")

    // step3. 获取计数器
    val counterNum = jobResult.getAccumulatorResult[Long]("counter-app")

    println("counter: " + counterNum)

  }
}