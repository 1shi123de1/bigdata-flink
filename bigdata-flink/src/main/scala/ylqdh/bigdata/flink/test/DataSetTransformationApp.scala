package ylqdh.bigdata.flink.test

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

import scala.collection.mutable.ListBuffer

/**
  * @ClassName DataSetTransformationApp
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/1/14 18:04
  */
object DataSetTransformationApp {
  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

//    mapFunction(env)
//    filterFunction(env)
//    mapPartitionFunction(env)
//    firstFunction(env)
//    flatMapFunction(env)
    distinctFunction(env)
  }


  // map，对集合的每个元素做map中的函数操作
  def mapFunction (env: ExecutionEnvironment): Unit = {
    val data = env.fromCollection(List(1,2,3,4,5,6,7,8,9,10))

    // 对data每个元素做+1
//    data.map((x:Int) => x+1).print()
//    data.map((x) => x+1).print()
//    data.map(x => x+1).print()
    data.map(_+1).print()  // 从上到下，可以一步步简化
  }

  // filter,对每个元素做判断，为true的留下，剩下的过滤掉
  def filterFunction(env:ExecutionEnvironment): Unit = {
    val data = env.fromCollection(List(1,2,3,4,5,6,7,8,9,10))

    // 过滤，留下偶数元素
    data.map(_+1)
//      .filter(_%2==0)
        .filter(x => x>=7)
      .print()

  }

  // mapPartition,对每个分区做一个函数操作
  def mapPartitionFunction (env:ExecutionEnvironment): Unit = {
    val students = new ListBuffer[String]
    for (x <- 1 to 100) {
      students.append("student: " + x)
    }
    val data = env.fromCollection(students).setParallelism(5)

    // TODO 使用map来操作数据库，那么每一条数据都要获取到一个连接，如果有上千万条数据，那么数据库就很容易崩溃
    data.map(x => {
      // TODO 获取数据库连接
      val connection = DBUtils.getConnection()
      println(connection + "....")

      /*
      TODO 获取到数据库链接之后，接下来做一些数据库的操作，这里省略
       */

      // TODO 数据库操作完之后,回收连接
      DBUtils.returnConnection(connection)
    })

    // 改用mapPartition
    data.mapPartition(x => {
      val connection = DBUtils.getConnection()
      println(connection)

      DBUtils.returnConnection(connection)

      x
    }).print()
  }

  // first,取数据的前多少条
  def firstFunction (env:ExecutionEnvironment): Unit = {
    val info = ListBuffer[(Int,String)]()
    info.append((1,"Hadoop"))
    info.append((1,"Spark"))
    info.append((1,"Flink"))
    info.append((2,"Scala"))
    info.append((2,"Java"))
    info.append((2,"Python"))
    info.append((3,"Linux"))
    info.append((3,"Window"))
    info.append((3,"MacOS"))

    val data = env.fromCollection(info)

    // 所有数据,取前3条
//    data.first(3).print()
    // 按第1列聚合,然后取前2条数据
//    data.groupBy(0).first(2).print()
    // 聚合后，第二列降序排序，然后取前2个
    data.groupBy(0).sortGroup(1,Order.DESCENDING).first(2).print()

  }

  // flatMap,按照指定分隔符，对数据切分
  def flatMapFunction (env:ExecutionEnvironment): Unit = {
    val list = new ListBuffer[String]()
    list.append("Hadoop,Spark")
    list.append("Spark,Flink")
    list.append("Hadoop,Flink")

    val data = env.fromCollection(list)

    data.flatMap(x => x.split(",")).print()

    println("~~~~~~~~~~~~~~")

    // flink worccount，和Spark有点不一样
    data.flatMap(_.split(","))
      .map(x => (x,1))
      .groupBy(0)
      .sum(1)
      .print()
  }

  // distinct,去重
  def distinctFunction(env:ExecutionEnvironment): Unit = {
    val list = new ListBuffer[String]()
    list.append("Hadoop,Spark")
    list.append("Spark,Flink")
    list.append("Hadoop,Flink")

    val data = env.fromCollection(list)

    data.flatMap(_.split(","))
      .distinct()
      .print()
  }
}
