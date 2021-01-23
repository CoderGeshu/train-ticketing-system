package dao;

import dbutil.SQLHelper;
import entity.SeatPrice;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:06
 * @author: Eric
 * @Description: TODO
 */
public class SeatPriceDao {
    //按照列车类型从数据库中获得票价信息
    public SeatPrice seatPriceQueryByno1(int traintypeno){
        SeatPrice seatPrice=new SeatPrice();
        String mysql="select * from seatprice where traintypeno="+traintypeno;
		System.out.println(mysql);
        try {
            ResultSet rs= SQLHelper.executeQuery(mysql);
            while(rs.next()) {
                seatPrice.setTraintypeno(Integer.parseInt(rs.getString(1)));
                seatPrice.setBusinessprice(Integer.parseInt(rs.getString(2)));
                seatPrice.setFirstprice(Integer.parseInt(rs.getString(3)));
                seatPrice.setSecondprice(Integer.parseInt(rs.getString(4)));
            }
            SQLHelper.closeConnection();
        }catch(SQLException e) {
            System.out.println("按照列车类型从数据库中获得票价信息方法报错");
        }
        return seatPrice;
    }

    //
    public int updateSeatPrice(int traintypeno,int businessprice,int firstprice,int secondprice) {
        String mysql="update seatprice set businessprice="+businessprice+",firstprice="+firstprice;
        String mysql1=",secondprice="+secondprice+" where traintypeno="+traintypeno;
        mysql=mysql+mysql1;
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }
}
