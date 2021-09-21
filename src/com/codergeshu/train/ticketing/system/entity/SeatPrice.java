package com.codergeshu.train.ticketing.system.entity;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 17:07
 * @author: Eric
 * @Description: TODO 车价实体类
 */
public class SeatPrice {
    private int traintypeno;
    private int businessprice;
    private int firstprice;
    private int secondprice;

    public int getTraintypeno() {
        return traintypeno;
    }
    public void setTraintypeno(int traintypeno) {
        this.traintypeno = traintypeno;
    }
    public int getBusinessprice() {
        return businessprice;
    }
    public void setBusinessprice(int businessprice) {
        this.businessprice = businessprice;
    }
    public int getFirstprice() {
        return firstprice;
    }
    public void setFirstprice(int firstprice) {
        this.firstprice = firstprice;
    }
    public int getSecondprice() {
        return secondprice;
    }
    public void setSecondprice(int secondprice) {
        this.secondprice = secondprice;
    }
}
