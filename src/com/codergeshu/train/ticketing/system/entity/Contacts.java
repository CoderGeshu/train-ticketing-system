package com.codergeshu.train.ticketing.system.entity;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 17:08
 * @author: Eric
 * @Description: TODO 用户常用联系人类
 */
public class Contacts {
    private String userid;
    private String contactname;
    private String contacttel;
    private String contactid;
    private String contactno;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContacttel() {
        return contacttel;
    }

    public void setContacttel(String contacttel) {
        this.contacttel = contacttel;
    }

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }
}
