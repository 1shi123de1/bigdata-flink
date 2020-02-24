package ylqdh.bigdata.flink.test

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._

/**
  * @ClassName DataStreamExecuEnvironmentApp
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/2/21 16:15
  */
object DataStreamExecuEnvironmentApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.createLocalEnvironment()

//    socketFunction(env)
//    nonParallelSourceFunction(env)
//    ParallelSourceFunction(env)
    RichParallelSourceFunction(env)

    env.execute("scala-DataStreamExecuEnvironmentApp")
  }

  // 自定义、并行 数据源
  def RichParallelSourceFunction(env:StreamExecutionEnvironment): Unit = {
    val data = env.addSource(new CustomRichParallelSourceFunction).setParallelism(3)
    data.print()
  }

  // 自定义、并行 数据源
  def ParallelSourceFunction(env:StreamExecutionEnvironment): Unit = {
    val data = env.addSource(new CustomParallelSourceFunction).setParallelism(3)
    data.print()
  }

  // 自定义、非并行 数据源
  def nonParallelSourceFunction(env:StreamExecutionEnvironment): Unit = {
    val data = env.addSource(new CustomNonParallelSourceFunction)
    data.print()
  }

  // socket数据源
  def socketFunction(env:StreamExecutionEnvironment): Unit = {
    val data = env.socketTextStream("172.16.13.143",9999)
    data.print().setParallelism(3)

    val counts = data.flatMap(_.toLowerCase.split(" "))
      .filter(_.nonEmpty)
      .map((_,1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counts.print()
  }

}
