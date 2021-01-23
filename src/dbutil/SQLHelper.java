package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 16:27
 * @author: Eric
 * @Description: TODO 实现对数据库的连接与操作工具类
 */
public class SQLHelper {

    //创建数据库连接、预处理和结果集对象
    private static Connection conn = null;
    private static PreparedStatement pre = null;
    private static ResultSet rs = null;

    //加载驱动，建立连接
    private static void buildConnection() {
        //数据库配置信息
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/train?serverTimezone=GMT";
        String user = "root", password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行查询SQL语句，返回查询结果集
     *
     * @param sql SQL语句
     * @return 结果集对象
     */
    public static ResultSet executeQuery(String sql) {
        buildConnection();
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //需要在调用处手动关闭连接
        return rs;
    }

    /**
     * 执行更新语句
     *
     * @param sql 更新语句
     * @return 0为未更新，否则为更新成功
     */
    public static int executeUpdate(String sql) {
        buildConnection();
        int r = 0;
        try {
            pre = conn.prepareStatement(sql);
            r = pre.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        closeConnection();
        return r;
    }

    //执行单个语句，返回结果对象
    public static Object executeSingleQuery(String singleSql) {
        buildConnection();
        Object obj = null;
        try {
            pre= conn.prepareStatement(singleSql);
            rs = pre.executeQuery();
            if (rs.next()){
                obj = rs.getObject(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        closeConnection();//??
        return obj;
    }

    /**
     * 关闭连接，注意关闭顺序，最后使用的最先关闭
     */
    public static void closeConnection() {
        try {
            if(rs != null && !rs.isClosed()){
                rs.close();
            }
            if(pre != null && !pre.isClosed()){
                pre.close();
            }
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
            System.out.println("数据库连接已关闭");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
