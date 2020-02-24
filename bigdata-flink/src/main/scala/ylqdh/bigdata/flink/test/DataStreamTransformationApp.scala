package ylqdh.bigdata.flink.test

import java.{lang, util}

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.collector.selector.OutputSelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * @ClassName DataStreamTransformationApp
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/2/24 11:27
  */
object DataStreamTransformationApp {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.createLocalEnvironment()

//    filterFunction(env)
//    unionFunction(env)
    splitSelectFunction(env)

    env.execute("DataStreamTransformationApp")

  }

  def splitSelectFunction (env:StreamExecutionEnvironment): Unit = {
    val data = env.addSource(new CustomNonParallelSourceFunction)

    val splits = data.split(new OutputSelector[Long] {
      override def select(value: Long): lang.Iterable[String] = {
        val list = new util.ArrayList[String]()
        if (value %2 == 0) {
          list.add("even")
        } else {
          list.add("odd")
        }
        list
      }
    })

    splits.select("even").print()
  }

  def unionFunction(env:StreamExecutionEnvironment): Unit = {
    val data1 = env.addSource(new CustomNonParallelSourceFunction)
    val data2 = env.addSource(new CustomNonParallelSourceFunction)

    data1.union(data2).print()
  }

  def filterFunction(env:StreamExecutionEnvironment): Unit = {
    val data = env.addSource(new CustomNonParallelSourceFunction)
    data.map(x => {
      println("reveived: " + x)
      x
    }).filter(_%2 == 0).print().setParallelism(1)
  }

}
