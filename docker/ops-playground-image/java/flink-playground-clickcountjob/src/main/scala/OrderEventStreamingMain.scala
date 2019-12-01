import java.util.Properties

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.table.api.scala.StreamTableEnvironment

/**
 *
 * @author jianyuan
 * @version $Id: OrderEventStreamingMain.scala, v0.1 2019-12-02 12:28 上午 by jianyuan
 */
object OrderEventStreamingMain {
  /**
   * Todo consume the source
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val execEnv = StreamExecutionEnvironment.getExecutionEnvironment
    val tableEnv = StreamTableEnvironment.create(execEnv)

    val kafkaProps = new Properties()
  }
}
