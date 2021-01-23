package view;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

import dao.*;
import entity.*;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:46
 * @author: Eric
 * @Description: TODO 订单界面类
 */

public class OrderSheetWindow extends JFrame implements ActionListener {
    private JButton btn_endorse, btn_refund;
    private JTable orderSheetTable;
    private Object[] cols_orderSheet = {"身份证号", "姓名", "车次", "类型", "起点",
            "终点", "出发时间", "到达时间", "车厢号", "座位号", "座位状态", "价格", "购买日期"};
    private Object[][] rows_orderSheet;
    //    private ArrayList<OrderSheets> orderSheets;
    private String userId, buyTime;
    private int trainTypeNo;
    private String trainNo, startPlace, endPlace;

    public OrderSheetWindow(String s, String userId) {
        setTitle(s);
        this.userId = userId;
        //设置图标
        ImageIcon icon = new ImageIcon("picture/logo1.jpg");
        setIconImage(icon.getImage());

        //功能按钮
        btn_endorse = new JButton("改签");
        btn_endorse.addActionListener(this);
        btn_refund = new JButton("退票");
        btn_refund.addActionListener(this);
        JPanel jp_btn = new JPanel();
        jp_btn.add(btn_endorse);
        jp_btn.add(btn_refund);

        //初始化订单表格（更新）
        orderSheetTable = new JTable(rows_orderSheet, cols_orderSheet);
        orderSheetTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                getSelectedInfo();//获得鼠标选中的信息
            }
        });
        this.updateOrderSheetTable();//初始化获得订单所有信息

        JPanel jp_table = new JPanel();
        jp_table.setBorder(new TitledBorder("订单详情"));
        jp_table.setLayout(new BorderLayout());
        jp_table.add(new JScrollPane(orderSheetTable), BorderLayout.CENTER);


        this.add(jp_table, BorderLayout.CENTER);
        this.add(jp_btn, BorderLayout.SOUTH);
        this.setVisible(true);
        this.setSize(1800, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        //退票事件
        if (e.getSource() == btn_refund) {
            if (buyTime == null) {
                JOptionPane.showMessageDialog(this, "请先选择要退的票", "提醒", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int r = JOptionPane.showConfirmDialog(this, "确认退票？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (r == 0) {
                refundTicket();
                this.updateOrderSheetTable();//更新表格
            }
            return;
        }

        //改签事件
        if (e.getSource() == btn_endorse) {
            if (buyTime == null) {
                JOptionPane.showMessageDialog(this, "请先选择要改签的票", "提醒", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int p = JOptionPane.showConfirmDialog(this, "确认改签？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (p == 0) {
                this.endorseTicket();
                buyTime = null;
            }
        }

    }

    //获得鼠标选中的信息
    private void getSelectedInfo() {
        int row = orderSheetTable.getSelectedRow();
        Object no = orderSheetTable.getModel().getValueAt(row, 2);
        Object type = orderSheetTable.getModel().getValueAt(row, 3);
        Object start = orderSheetTable.getModel().getValueAt(row, 4);
        Object end = orderSheetTable.getModel().getValueAt(row, 5);
        Object time = orderSheetTable.getModel().getValueAt(row, 12);
        trainNo = (String) no;
        startPlace = (String) start;
        endPlace = (String) end;
        String tmp = (String) type;
        buyTime = (String) time;
        if (tmp.equals("普通列车")) {
            trainTypeNo = 0;
        } else if (tmp.equals("高铁")) {
            trainTypeNo = 1;
        } else if (tmp.equals("动车")) {
            trainTypeNo = 2;
        }
    }

    //更新订单表格
    private void updateOrderSheetTable() {
        OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
        ArrayList<OrderSheets> orderSheets = orderSheetsDao.orderSheetsQueryById(userId);
        rows_orderSheet = new Object[orderSheets.size()][cols_orderSheet.length];
        int k = 0;
        for (OrderSheets orderSheet : orderSheets) {
            String trainType = "";
            if (orderSheet.getTraintypeno() == 0) {
                trainType = "普通列车";
            } else if (orderSheet.getTraintypeno() == 1) {
                trainType = "高铁";
            } else if (orderSheet.getTraintypeno() == 2) {
                trainType = "动车";
            }
            String seatClass = "";
            if (orderSheet.getSeatclassno() == 0) {
                seatClass = "商务座";
            } else if (orderSheet.getSeatclassno() == 1) {
                seatClass = "一等座";
            } else if (orderSheet.getSeatclassno() == 2) {
                seatClass = "二等座";
            }
            rows_orderSheet[k][0] = orderSheet.getUserid();
            rows_orderSheet[k][1] = orderSheet.getUsername();
            rows_orderSheet[k][2] = orderSheet.getTrainno();
            rows_orderSheet[k][3] = trainType;
            rows_orderSheet[k][4] = orderSheet.getStartplace();
            rows_orderSheet[k][5] = orderSheet.getEndplace();
            rows_orderSheet[k][6] = orderSheet.getStarttime();
            rows_orderSheet[k][7] = orderSheet.getEndtime();
            rows_orderSheet[k][8] = orderSheet.getBoxno();
            rows_orderSheet[k][9] = orderSheet.getSeatno();
            rows_orderSheet[k][10] = seatClass;
            rows_orderSheet[k][11] = orderSheet.getPrice();
            rows_orderSheet[k][12] = orderSheet.getBuytime();
            k++;
        }
        orderSheetTable.setModel(new DefaultTableModel(rows_orderSheet, cols_orderSheet));
    }

    //退票
    private void refundTicket() {
        OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
        int k = orderSheetsDao.deleteOrderSheet(buyTime);
        if (k == 1) {
            JOptionPane.showMessageDialog(this, "退票成功", "提醒", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "退票失败", "提醒", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //改签票
    private void endorseTicket() {
        String endorseDate = JOptionPane.showInputDialog(this, "请输入要改签的日期：\n", "改签", JOptionPane.PLAIN_MESSAGE);
        System.out.println(endorseDate);
        if (endorseDate.equals("")) {
            JOptionPane.showMessageDialog(this, "请输入改签日期");
            return;
        }
        //输入日期不为空
        //先删除所选票
        OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
        int deleted = orderSheetsDao.deleteOrderSheet(buyTime);

        //重新选座买票，需先获取订单所需的信息
        Object[] obj2 = {"商务座", "一等座", "二等座"};
        String seatLevel = (String) JOptionPane.showInputDialog(
                this, "请选择座位类型:\n",
                "座位类型", JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("/picture/success"),
                obj2, "商务座");

        //设置价格，查找相应列车等级和座位等级的票价
        int price = 0;
        int seatClassNo = 0;
        SeatPriceDao seatPriceDao = new SeatPriceDao();
        SeatPrice seatPrice = seatPriceDao.seatPriceQueryByno1(trainTypeNo);
        switch (seatLevel){
            case "商务座":
                seatClassNo = 0;
                price = seatPrice.getBusinessprice();
                break;
            case "一等座":
                seatClassNo = 1;
                price = seatPrice.getFirstprice();
                break;
            case "二等座":
                seatClassNo = 2;
                price = seatPrice.getSecondprice();
                break;
            default:
                System.out.println("选座出错");
                break;
        }

        //获得用户信息，用于生成订单
        UsersDao usersDao = new UsersDao();
        Users user = usersDao.userQueryById(userId);
        System.out.println("userId:" + userId);
        String username = user.getUsername();
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String buytime = df.format(new Date());
        //车厢号和座位号为随机数
        int boxno = (int) (Math.random() * 10 + 1);
        int seatno = (int) (Math.random() * 31 + 1);
        TrainsDao trainsDao = new TrainsDao();
        //表示搜索除了列车类型为3的所有符合条件的列车
        ArrayList<Trains> trainList = trainsDao.trainTicketsQuery(startPlace, endPlace, 3);
        String startTime = endorseDate + " " + trainList.get(0).getStarttime();
        String endTime = endorseDate + " " + trainList.get(0).getEndtime();

        //执行增加订单
        int r = orderSheetsDao.addOrderSheet(userId, username, trainNo,
                trainTypeNo, startPlace, endPlace, startTime, endTime,
                boxno, seatno, seatClassNo, price, buytime);

        //判断删除订单和重新购买是否都成功
        if (deleted == 1 && r == 1) {
            JOptionPane.showMessageDialog(this, "改签成功", "提醒", JOptionPane.PLAIN_MESSAGE);
            this.updateOrderSheetTable();
        } else {
            JOptionPane.showMessageDialog(this, "改签失败", "提醒", JOptionPane.PLAIN_MESSAGE);
        }
    }

//    public static void main(String[] args) {
//        new OrderSheetWindow("我的车票", "123456789012345671");
//    }

}

