package ylqdh.bigdata.flink.test

import scala.util.Random

/**
  * @ClassName DBUtils
  * @Description TODO 模拟数据库的连接池，生成连接和回收连接函数
  * @Author ylqdh
  * @Date 2020/1/15 9:51
  */
object DBUtils {
  def getConnection (): String = {
    new Random().nextInt(10) + ""
  }

  def returnConnection(connection:String): Unit = {

  }

}
