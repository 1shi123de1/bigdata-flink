package ylqdh.bigdata.flink.test

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.table.api.scala.BatchTableEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.types.Row

/**
  * @ClassName TableSQLAPI
  * @Description TODO 没有运行成功，参考官网完成例子
  *             https://ci.apache.org/projects/flink/flink-docs-release-1.9/dev/table/common.html
  * @Author ylqdh
  * @Date 2020/2/24 17:43
  */
object TableSQLAPI {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val tableEnv = BatchTableEnvironment.create(env)

    val filePath = "file:///C:\\data\\cleandata\\sales.csv"
    val csv = env.readCsvFile[SaleLog](filePath,ignoreFirstLine = true)  // DataSet

    // DataSet --> Table
    val salesTable = tableEnv.fromDataSet(csv)
    tableEnv.registerTable("sales",salesTable)

    // sql
    val resultTable = tableEnv.sqlQuery("select transactionId,sum(amountPaid) from sales group by customerId ")
    tableEnv.toDataSet[Row](resultTable).print()
  }

  case class SaleLog(transactionId:String,
                     customerId:String,
                     itemId:String,
                     amountPaid:Double)

}
