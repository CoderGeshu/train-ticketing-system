package com.codergeshu.train.ticketing.system.dao;

import com.codergeshu.train.ticketing.system.utils.SQLHelper;
import com.codergeshu.train.ticketing.system.entity.Contacts;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:38
 * @author: Eric
 * @Description: TODO
 */
public class ContactsDao {
    //通过用户的身份证和联系人编号来获得联系人
    public Contacts contactsQueryById(String userid, int contactno){
        Contacts contact=new Contacts();
        String mysql="select * from contacts where userid='"+userid+"' and contactno="+contactno;
        System.out.println(mysql);
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            while(rs.next()) {
                contact.setUserid(rs.getString(1));
                contact.setContactname(rs.getString(2));
                contact.setContacttel(rs.getString(3));
                contact.setContactid(rs.getString(4));
                contact.setContactno(rs.getString(5));
            }
            SQLHelper.closeConnection();
        }catch(SQLException e) {
            System.out.println("通过用户的身份证和联系人编号来获得联系人方法报错");
        }
        return contact;
    }

    //修改常用联系人信息
    public int modifyContact(String userid,String contactname,String contacttel,String contactid,int contactno) {
        String mysql="update contacts set contactname='"+contactname+"',contacttel='"+contacttel+"',contactid='";
        String mysql1=contactid+"' where userid='"+userid+"' and contactno="+contactno;
        mysql=mysql+mysql1;
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //添加常用联系人信息
    public int addContact(String userid,int contactno) {
        String mysql="insert into contacts values('"+userid+"','','','',"+contactno+")";
        System.out.println(mysql);
        return SQLHelper.executeUpdate(mysql);
    }

    //判断用户是否拥有某号的联系人
    public boolean haveExist(String userid,int contactno) {
        boolean haveExist=false;
        String mysql="select * from contacts where userid='"+userid+"' and contactno="+contactno;
        System.out.println(mysql);
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            rs.last();
            int rows=rs.getRow();
            System.out.println("rows :"+rows);
            if(rows!=0) {
                haveExist=true;
            }
            SQLHelper.closeConnection();
        }catch(SQLException e) {
            System.out.println("判断用户是否拥有联系人方法报错");
        }
        return haveExist;
    }

}
