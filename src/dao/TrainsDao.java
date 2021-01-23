package dao;

import dbutil.SQLHelper;
import entity.Trains;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 17:55
 * @author: Eric
 * @Description: TODO 对列车信息的数据库操作类
 */
public class TrainsDao {
    //获得全部列车信息
    public ArrayList<Trains> trainQueryAll() {
        String mysql = "select * from trains;";
        System.out.println(mysql);
        return getTrains(mysql);
    }

    //通过列车号来搜索列车
    public Trains trainQueryByno(String trainno) {
        String mysql = "select * from trains where trainno='" + trainno + "'";
        System.out.println(mysql);
        return getTrains(mysql).get(0);
    }

    //搜索除了列车类型为traintypeno的所有符合条件的列车
    public ArrayList<Trains> trainTicketsQuery(String startplace, String endplace, int traintypeno) {
        String mysql = "select * from trains where startplace='" + startplace +
                "' and endplace='" + endplace + "' " +
                "and traintypeno!=" + traintypeno;
        System.out.println(mysql);
        return getTrains(mysql);
    }

    //搜索所有符合条件的列车（管理员用）
    public ArrayList<Trains> trainQueryByAdmin(String startplace, String endplace, int traintypeno) {
        String mysql = "select * from trains where startplace='" + startplace +
                "' and endplace='" + endplace + "' " +
                "and traintypeno=" + traintypeno;
        System.out.println(mysql);
        return getTrains(mysql);
    }

    //判断列车是否存在
    public boolean trainIsExist(String trainno) {
        boolean isExist = false;
        String mysql = "select * from trains where trainno='" + trainno + "'";
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            isExist = rs.next();//如果列车存在，则会返回一个true值
            SQLHelper.closeConnection();
        } catch (Exception e) {
            System.out.println("判断列车是否存在方法报错");
        }
        return isExist;
    }

    //修改列车信息
    public int updateTrain(String trainno, int traintypeno, String startplace, String endplace,
                           String starttime, String endtime, String runtime) {

        String mysql = "update trains set traintypeno=" + traintypeno + ",";
        String mysql1 = "startplace='" + startplace + "',endplace='" + endplace + "',";
        String mysql2 = "starttime='" + starttime + "',endtime='" + endtime + "',runtime='";
        String mysql3 = runtime + "' where trainno='" + trainno + "'";
        mysql = mysql + mysql1 + mysql2 + mysql3;
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //增加列车
    public int addTrain(String trainno, int traintypeno, String startplace, String endplace,
                        String starttime, String endtime, String runtime) {
        String mysql = "insert into trains values('" + trainno + "'," + traintypeno;
        String mysql1 = ",'" + startplace + "','" + endplace + "','";
        String mysql2 = starttime + "','" + endtime + "','" + runtime + "')";
        mysql = mysql + mysql1 + mysql2;
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //删除列车
    public boolean deleteTrain(String trainNo) {
        String mysql = "delete from trains where trainno=" + trainNo + "'";
        System.out.println(mysql);
        return 0 != SQLHelper.executeUpdate(mysql);
    }

    //获取列车信息数组列表
    private ArrayList<Trains> getTrains(String mysql) {
        ArrayList<Trains> trains = new ArrayList<>();
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            while (rs.next()) {
                Trains train = new Trains();
                train.setTrainno(rs.getString(1));
                train.setTraintypeno(Integer.parseInt(rs.getString(2)));
                train.setStartplace(rs.getString(3));
                train.setEndplace(rs.getString(4));
                train.setStarttime(rs.getString(5));
                train.setEndtime(rs.getString(6));
                train.setRuntime(rs.getString(7));
                trains.add(train);
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println("获取列车数组列表方法中报错");
        }
        return trains;
    }
}
