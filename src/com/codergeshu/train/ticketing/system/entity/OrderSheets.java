package com.codergeshu.train.ticketing.system.entity;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 17:08
 * @author: Eric
 * @Description: TODO 车票订单类
 */
public class OrderSheets {
    private String userid;
    private String username;
    private String trainno;
    private int traintypeno;
    private String startplace;
    private String endplace;
    private String starttime;
    private String endtime;
    private String boxno;
    private String seatno;
    private int  seatclassno;
    private String price;
    private String buytime;

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTrainno() {
        return trainno;
    }
    public void setTrainno(String trainno) {
        this.trainno = trainno;
    }
    public int getTraintypeno() {
        return traintypeno;
    }
    public void setTraintypeno(int traintypeno) {
        this.traintypeno = traintypeno;
    }
    public String getStartplace() {
        return startplace;
    }
    public void setStartplace(String startplace) {
        this.startplace = startplace;
    }
    public String getEndplace() {
        return endplace;
    }
    public void setEndplace(String endplace) {
        this.endplace = endplace;
    }
    public String getStarttime() {
        return starttime;
    }
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    public String getEndtime() {
        return endtime;
    }
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
    public String getBoxno() {
        return boxno;
    }
    public void setBoxno(String boxno) {
        this.boxno = boxno;
    }
    public String getSeatno() {
        return seatno;
    }
    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }
    public int getSeatclassno() {
        return seatclassno;
    }
    public void setSeatclassno(int seatclassno) {
        this.seatclassno = seatclassno;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getBuytime() {
        return buytime;
    }
    public void setBuytime(String buytime) {
        this.buytime = buytime;
    }
}
