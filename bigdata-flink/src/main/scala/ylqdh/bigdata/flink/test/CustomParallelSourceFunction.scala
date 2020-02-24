package ylqdh.bigdata.flink.test

import org.apache.flink.streaming.api.functions.source.{ParallelSourceFunction, SourceFunction}

/**
  * @ClassName CustomParallelSourceFunction
  * @Description TODO
  * @Author ylqdh
  * @Date 2020/2/24 10:31
  */
class CustomParallelSourceFunction extends ParallelSourceFunction[Long] {

  var isRunning = true
  var count = 1L

  override def cancel() = {
    isRunning = false
  }

  override def run(ctx: SourceFunction.SourceContext[Long]) = {
    while (isRunning) {
      ctx.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }
}
