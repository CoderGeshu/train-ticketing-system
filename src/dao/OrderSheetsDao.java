package dao;

import dbutil.SQLHelper;
import entity.OrderSheets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:12
 * @author: Eric
 * @Description: TODO
 */
public class OrderSheetsDao {
    //获取全部订单信息
    public ArrayList<OrderSheets> orderSheetsQueryAll() {
        String mysql = "select * from ordersheets;";
        System.out.println(mysql);
        return getOrderSheets(mysql);
    }

    //通过id信息获得订单详情
    public ArrayList<OrderSheets> orderSheetsQueryById(String userid) {
        String mysql = "select * from ordersheets where userid='" + userid + "'";
        System.out.println(mysql);
        return getOrderSheets(mysql);
    }

    //增加车票
    public int addOrderSheet(String userid, String username, String trainno, int traintypeno, String startplace, String endplace,
                             String starttime, String endtime, int boxno, int seatno, int seatclassno, int price, String buytime) {
        String mysql = "insert into ordersheets values('" + userid + "','" + username + "','" + trainno + "',";
        String mysql1 = traintypeno + ",'" + startplace + "','" + endplace + "','" + starttime + "','" + endtime + "','";
        String mysql2 = boxno + "','" + seatno + "'," + seatclassno + "," + price + ",'" + buytime + "')";
        mysql = mysql + mysql1 + mysql2;
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //删除车票(退票)
    public int deleteOrderSheet(String buytime) {
        String mysql = "delete from ordersheets where buytime='" + buytime + "'";
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //判断用户是否买过此车次此日期的票
    public boolean haveBought(String userid, String trainno, String starttime) {
        boolean havebought = false;
        String mysql = "select * from ordersheets where userid='" + userid + "' and trainno='" + trainno + "' and ";
        String mysql1 = "starttime='" + starttime + "'";
        mysql = mysql + mysql1;
        System.out.println(mysql);
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            if (rs.next()) {
                havebought = true;
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return havebought;
    }

    //获取订单信息
    private ArrayList<OrderSheets> getOrderSheets(String mysql) {
        ArrayList<OrderSheets> orderSheets = new ArrayList<>();
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            while (rs.next()) {
                OrderSheets orderSheet = new OrderSheets();
                orderSheet.setUserid(rs.getString(1));
                orderSheet.setUsername(rs.getString(2));
                orderSheet.setTrainno(rs.getString(3));
                orderSheet.setTraintypeno(Integer.parseInt(rs.getString(4)));
                orderSheet.setStartplace(rs.getString(5));
                orderSheet.setEndplace(rs.getString(6));
                orderSheet.setStarttime(rs.getString(7));
                orderSheet.setEndtime(rs.getString(8));
                orderSheet.setBoxno(rs.getString(9));
                orderSheet.setSeatno(rs.getString(10));
                orderSheet.setSeatclassno(Integer.parseInt(rs.getString(11)));
                orderSheet.setPrice(rs.getString(12));
                orderSheet.setBuytime(rs.getString(13));
                orderSheets.add(orderSheet);
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println("获取订单信息方法报错");
        }
        return orderSheets;
    }
}
