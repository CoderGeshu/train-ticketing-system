package entity;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 17:06
 * @author: Eric
 * @Description: TODO 用户实体类
 */
public class Users {
    private String usertel;
    private String password;
    private String username;
    private String usergender;
    private int usertypeno;
    private String userid;
    private int idtypeno;

    public String getUsertel() {
        return usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsergender() {
        return usergender;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }

    public int getUsertypeno() {
        return usertypeno;
    }

    public void setUsertypeno(int usertypeno) {
        this.usertypeno = usertypeno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getIdtypeno() {
        return idtypeno;
    }

    public void setIdtypeno(int idtypeno) {
        this.idtypeno = idtypeno;
    }
}
