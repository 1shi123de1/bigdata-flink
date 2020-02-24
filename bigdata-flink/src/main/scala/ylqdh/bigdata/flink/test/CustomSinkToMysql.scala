package ylqdh.bigdata.flink.test

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * @ClassName CustomSinkToMysql
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/2/24 15:22
  */
object CustomSinkToMysql {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.createLocalEnvironment()

    val sourceData = env.socketTextStream("172.16.13.143",7777)

    val studentData = sourceData.map(x => {
      val stu:StudentPOJO = new StudentPOJO()
      val splits = x.split(",")
      stu.setId(splits(0).toInt)
      stu.setName(splits(1))
      stu.setAge(splits(2).toInt)
      stu
    })

    studentData.addSink(new SinkToMysql())

    env.execute("CustomSinkToMysql")
  }
}
