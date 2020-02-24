package ylqdh.bigdata.flink.test;

import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

/**
 * @ClassName JavaCustomParallelSourceFunction
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/2/24 10:58
 */
public class JavaCustomParallelSourceFunction implements ParallelSourceFunction<Long> {
    boolean isRunning = true;
    long count = 1L;

    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while (isRunning) {
            ctx.collect(count);
            count += 1;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {

        isRunning = false;
    }
}
