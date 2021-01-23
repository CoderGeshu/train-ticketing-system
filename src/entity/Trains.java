package entity;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 17:07
 * @author: Eric
 * @Description: TODO 列车实体类
 */
public class Trains {
    private String trainno;
    private int traintypeno;
    private String startplace;
    private String endplace;
    private String starttime;
    private String endtime;
    private String runtime;

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
    public String getRuntime() {
        return runtime;
    }
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
}
