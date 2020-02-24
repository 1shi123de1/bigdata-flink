package ylqdh.bigdata.flink.test;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @ClassName SinkToMysql
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/2/24 14:58
 */
public class SinkToMysql extends RichSinkFunction<StudentPOJO> {
    Connection connection = null;
    PreparedStatement pstmt = null;

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://172.16.1.46:3306/test";
            conn = DriverManager.getConnection(url,"root","123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
        return conn;
    }

    /*
        在open 方法中建立connection
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        connection = getConnection();
        String sql = "insert into flink_student(id,name,age) value (?,?,?)";
        pstmt = connection.prepareStatement(sql);

        System.out.println("open");
    }

    /*
        每条记录插入时调用一次
     */
    @Override
    public void invoke(StudentPOJO value, Context context) throws Exception {
        System.out.println("invoke~~~~~~~");
        pstmt.setInt(1,value.getId());
        pstmt.setString(2,value.getName());
        pstmt.setInt(3,value.getAge());

        pstmt.execute();
    }

    /*
        在close方法中释放资源
     */

    @Override
    public void close() throws Exception {
        if (pstmt != null) {
            pstmt.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
