package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import dao.*;
import entity.*;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:49
 * @author: Eric
 * @Description: TODO
 */

public class SearchResultWindow extends JFrame implements ActionListener{
    JButton btn_buy;
    JTable table_train;
    Object[] cols_train= {"车次","类型","起点","终点","出发时间","到达时间","运行时间","商务座","一等座","二等座"};
    Object[][] rows_train;
    JPanel jp_seatclass;
    JComboBox<String> com_seatclass;
    ArrayList<Trains> trainList=new ArrayList<Trains>();
    SeatPrice seatPrice=new SeatPrice();
    String trainno,startplace1,endplace1,starttime,endtime,usertel;
    int seatclassno,traintypeno;

    public SearchResultWindow(String s,String startplace,String endplace,String departure,String tel,int traintypeno1) {
        setTitle(s);
        usertel=tel;
        //设置图标
        ImageIcon icon=new ImageIcon("picture/logo1.jpg");
        setIconImage(icon.getImage());

        btn_buy=new JButton("购买");
        btn_buy.addActionListener(this);
        JPanel jp_btn=new JPanel();
        jp_btn.add(btn_buy);

        //生成筛选的列车的表格
        TrainsDao trainsDao=new TrainsDao();
        SeatPriceDao seatPriceDao=new SeatPriceDao();
        trainList=trainsDao.trainTicketsQuery(startplace, endplace,traintypeno1);
        rows_train=new Object[trainList.size()][cols_train.length];
        int j=0;
        while(j<trainList.size()) {
            String traintype="";
            if(trainList.get(j).getTraintypeno()==0) {
                traintype="普通列车";
            }else if(trainList.get(j).getTraintypeno()==1) {
                traintype="高铁";
            }else if(trainList.get(j).getTraintypeno()==2) {
                traintype="动车";
            }
            seatPrice=seatPriceDao.seatPriceQueryByno1(trainList.get(j).getTraintypeno());
            rows_train[j][0]=trainList.get(j).getTrainno();
            rows_train[j][1]=traintype;
            rows_train[j][2]=trainList.get(j).getStartplace();
            rows_train[j][3]=trainList.get(j).getEndplace();
            rows_train[j][4]=departure+" "+trainList.get(j).getStarttime();
            rows_train[j][5]=departure+" "+trainList.get(j).getEndtime();
            rows_train[j][6]=trainList.get(j).getRuntime();
            rows_train[j][7]=seatPrice.getBusinessprice();
            rows_train[j][8]=seatPrice.getFirstprice();
            rows_train[j][9]=seatPrice.getSecondprice();
            j++;
        }
        table_train=new JTable(rows_train,cols_train);
        //为表格添加监视器
        table_train.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row=table_train.getSelectedRow();
                Object[] value=new Object[cols_train.length];
                for(int i=0;i<cols_train.length;i++) {
                    value[i]=table_train.getModel().getValueAt(row,i);
                }
                trainno=value[0].toString();
                if(value[1].toString().equals("普通列车")) {
                    traintypeno=0;
                }else if(value[1].toString().equals("高铁")) {
                    traintypeno=1;
                }else if(value[1].toString().equals("动车")) {
                    traintypeno=2;
                }
                startplace1=value[2].toString();
                endplace1=value[3].toString();
                starttime=value[4].toString();
                endtime=value[5].toString();
            }
        });

        JPanel jp_tableOfTrain=new JPanel();
        jp_tableOfTrain.setBorder(new TitledBorder("车票详情"));
        jp_tableOfTrain.setLayout(new BorderLayout());
        jp_tableOfTrain.add(new JScrollPane(table_train),BorderLayout.CENTER);


        add(jp_tableOfTrain,BorderLayout.CENTER);
        add(jp_btn,BorderLayout.SOUTH);

        setVisible(true);
        setSize(1400,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btn_buy) {
            if(trainno==null) {
                JOptionPane.showMessageDialog(this, "请先选择要购买的票！","提醒",JOptionPane.WARNING_MESSAGE);
            }else if(haveBought()==true){
                JOptionPane.showMessageDialog(this, "您已买过该日期该车次的票！","提醒",JOptionPane.WARNING_MESSAGE);
            }else {
                Object[] obj2 ={ "商务座","一等座","二等座"};
                String seatclass= (String) JOptionPane.showInputDialog(this,"请选择座位类型:\n","座位类型",JOptionPane.PLAIN_MESSAGE,new ImageIcon(""),obj2,"商务座");
                SeatPriceDao seatPriceDao=new SeatPriceDao();
                int price=0;
                seatPrice=seatPriceDao.seatPriceQueryByno1(traintypeno);
                int business=seatPrice.getBusinessprice();
                int first=seatPrice.getFirstprice();
                int second=seatPrice.getSecondprice();
                if(seatclass.equals("商务座")) {
                    seatclassno=0;
                    price=business;
                }else if(seatclass.equals("一等座")) {
                    seatclassno=1;
                    price=first;
                }else if(seatclass.equals("二等座")) {
                    seatclassno=2;
                    price=second;
                }
                UsersDao usersDao=new UsersDao();
                Users user=new Users();
                user=usersDao.userQueryByTel(usertel);
                String userid=user.getUserid();
                String username=user.getUsername();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String buytime=df.format(new Date());
                System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                int boxno=(int)(Math.random()*10+1);
                int seatno=(int)(Math.random()*31+1);
                if(seatclass!="") {
                    OrderSheetsDao orderSheetsDao=new OrderSheetsDao();
                    int k=orderSheetsDao.addOrderSheet(userid, username,trainno,traintypeno,startplace1,endplace1,starttime,endtime,boxno,seatno,seatclassno,price,buytime);
                    JOptionPane.showMessageDialog(this, "购买 "+trainno+" "+seatclass+" 成功！","提醒",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
    }

    public boolean haveBought() {
        OrderSheetsDao orderSheetsDao=new OrderSheetsDao();
        UsersDao usersDao=new UsersDao();
        Users auser=new Users();
        auser=usersDao.userQueryByTel(usertel);
        String userid=auser.getUserid();
        boolean havebought=orderSheetsDao.haveBought(userid, trainno, starttime);

        return havebought;
    }

//    public static void main(String[] args) {
//        new SearchResultWindow("查询结果","上海","北京","2019-1-3","110",0);
//    }
}

