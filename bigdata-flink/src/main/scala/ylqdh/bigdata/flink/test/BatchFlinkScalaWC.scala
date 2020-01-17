package ylqdh.bigdata.flink.test

import org.apache.flink.api.scala.ExecutionEnvironment

/**
  * @ClassName BatchFlinkWC
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/1/6 16:39
  */
//noinspection ScalaDocUnknownTag
object BatchFlinkScalaWC {

  def main(args: Array[String]): Unit = {
    val input = "file:///C:\\data\\hello"

    val env = ExecutionEnvironment.getExecutionEnvironment

    val text = env.readTextFile(input)

    import org.apache.flink.api.scala._

    text.flatMap(_.toLowerCase.split("\t"))
        .filter(_.nonEmpty)
        .map((_,1))
        .groupBy(0)
        .sum(1).print()

  }
}
