package ylqdh.bigdata.flink.test

import org.apache.flink.streaming.api.functions.source.SourceFunction

/**
  * @ClassName CustomNonParallelSourceFunction
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/2/24 9:48
  */
class CustomNonParallelSourceFunction extends SourceFunction[Long]{

  var count = 1L

  var isRunning = true

  override def cancel() = {
    isRunning = false
  }

  override def run(ctx: SourceFunction.SourceContext[Long]) = {
    while (isRunning) {
      ctx.collect(count)
      count +=1
      Thread.sleep(1000)
    }
  }
}
