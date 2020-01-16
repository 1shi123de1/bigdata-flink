package ylqdh.bigdata.flink.test

import org.apache.flink.api.scala._
import org.apache.flink.api.scala.operators.ScalaCsvOutputFormat
import org.apache.flink.core.fs.FileSystem.WriteMode

import scala.collection.mutable.ListBuffer

/**
  * @ClassName SinkApp
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/1/16 11:42
  */
//noinspection ScalaDocUnknownTag
object DataSetSinkApp {
  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val list = 1 to 100
    val data = env.fromCollection(list).setParallelism(5)

    // 保存的目录
    val filePath = "file:///C:\\data\\cleandata\\test"  // 如果上面设置的并行度是大于1，那么这里会保存成一个文件夹，否则是一个文件

    // 保存为text文件
    data.writeAsText(filePath,WriteMode.OVERWRITE)  // 第二个参数表示覆盖源文件/文件夹

    val tt = new ListBuffer[(Int,String,String,String)]()
    tt.append((1,"a","100001","beijing"))
    tt.append((2,"b","100002","shanghai"))
    tt.append((3,"c","100003","shenzhen"))
    tt.append((4,"d","100004","guangzhou"))
    tt.append((5,"e","100005","hangzhou"))
    tt.append((6,"f","100006","chengdu"))
    tt.append((7,"g","100007","changsha"))

    val data2 = env.fromCollection(tt)

    // 保存为csv文件,数据必须是tuple类型的
    data2.writeAsCsv(filePath+"-csv",ScalaCsvOutputFormat.DEFAULT_LINE_DELIMITER,",",WriteMode.OVERWRITE)

    env.execute("DataSetSinkApp")   // 需要这句才会执行任务
  }
}
