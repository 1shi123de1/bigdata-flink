package ylqdh.bigdata.flink.test

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @ClassName DataStreamExecuEnvironmentApp
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/2/21 16:15
  */
object DataStreamExecuEnvironmentApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.createLocalEnvironment()
    val text = env.socketTextStream("172.16.13.143",9999)

    import org.apache.flink.streaming.api.scala._
    val counts = text.flatMap(_.toLowerCase.split(" "))
      .filter(_.nonEmpty)
      .map((_,1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counts.print()

    env.execute("scala-DataStreamExecuEnvironmentApp")
  }

}
