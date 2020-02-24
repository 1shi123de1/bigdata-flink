package ylqdh.bigdata.flink.test

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

import scala.collection.mutable.ListBuffer

/**
  * @ClassName BroadcastApp
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/2/21 11:14
  */
object BroadcastApp {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._

    // step1. 要广播的变量，要DataSet 类型才能被广播，不能是List/Map/Int/String等
    val toBroadcast = ListBuffer[Tuple2[String,Int]]()
    toBroadcast.append(("java",100))
    toBroadcast.append(("spark",101))
    toBroadcast.append(("python",102))
    val tupleData = env.fromCollection(toBroadcast)
    val toBroadcastData = tupleData.map(x => {
      Map(x._1->x._2)
    })

    val data = env.fromElements("java","spark","python")

    data.map(new RichMapFunction[String,String] {
      var broadcast:Traversable[String] = null
      var listData:java.util.List[Map[String,Int]] = null
      var allMap = Map[String,Int]()

      override def open(parameters: Configuration): Unit = {
        listData = getRuntimeContext().getBroadcastVariable[Map[String,Int]]("broadcast-scala-app")
        val it = listData.iterator()
        while (it.hasNext) {
          val next = it.next()
          allMap = allMap.++(next)
        }
      }

      override def map(in: String): String = {
        val price = allMap.get(in).get
        in + "," + price
      }
    }).withBroadcastSet(toBroadcastData,"broadcast-scala-app")
      .print()
  }

}
