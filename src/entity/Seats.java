package entity;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 17:08
 * @author: Eric
 * @Description: TODO 列车座位类
 */
public class Seats {
    private String trainno;
    private String boxno;
    private String seatno;
    private String seatclassno;
    private String seatstateno;

    public String getTrainno() {
        return trainno;
    }
    public void setTrainno(String trainno) {
        this.trainno = trainno;
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
    public String getSeatclassno() {
        return seatclassno;
    }
    public void setSeatclassno(String seatclassno) {
        this.seatclassno = seatclassno;
    }
    public String getSeatstateno() {
        return seatstateno;
    }
    public void setSeatstateno(String seatstateno) {
        this.seatstateno = seatstateno;
    }
}
