package ylqdh.bigdata.flink.test

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

/**
  * @Author ylqdh
  * @Date 2020/1/13 14:41
  *  Scala api 方式创建数据集
  */
object DataSourceApp {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

//    fromCollection(env)
    textFile(env)
//    fromCSV(env)
//    RecuDir(env)
  }

  // 从text文件创建数据集,压缩文件也可以直接读
  // 支持的压缩格式有：deflate,gz,gzip,bz2,xz
  def textFile(env:ExecutionEnvironment): Unit = {
    val filePath = "file:///C:\\data\\scalaCode\\HelloWorld.gz"   // 路径可以指定到文件名，也可以是文件夹
    env.readTextFile(filePath).print()
  }

  // 递归地读文件夹里的文件
  def RecuDir(env:ExecutionEnvironment): Unit = {
    val filePath = "file:///C:\\data\\cleandata"
    val parameter = new Configuration()
    parameter.setBoolean("recursive.file.enumeration",true)
    env.readTextFile(filePath).withParameters(parameter).print()
  }

  // 从csv文件读,来创建数据集
  def fromCSV(env:ExecutionEnvironment): Unit = {
    val filePath = "file:///C:\\data\\person.csv"

    import org.apache.flink.api.scala._

//     读csv文件，忽略文件的第一行
//    env.readCsvFile[(String, Int, String)](filePath,ignoreFirstLine = true).print()

//    只要csv的部分列
//    env.readCsvFile[(String, Int)](filePath,ignoreFirstLine = true,includedFields = Array(0,1)).print()

//   case class 方式
//    env.readCsvFile[person](filePath,ignoreFirstLine = true).print()

//    POJO 方式
    env.readCsvFile[PersonPOJO](filePath,ignoreFirstLine = true,pojoFields = Array("name","age","level")).print()
  }


  // 从collection创建数据集
  def fromCollection(env:ExecutionEnvironment): Unit = {

    import org.apache.flink.api.scala._
    val data = 1 to 10
    env.fromCollection(data).print()
  }

}
case class person(name:String,age:Int,level:String)