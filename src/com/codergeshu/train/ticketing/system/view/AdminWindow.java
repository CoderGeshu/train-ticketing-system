package com.codergeshu.train.ticketing.system.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.codergeshu.train.ticketing.system.dao.*;
import com.codergeshu.train.ticketing.system.entity.*;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:45
 * @author: Eric
 * @Description: TODO 管理员界面类
 */

public class AdminWindow extends JFrame implements ActionListener {

    //菜单项目
    private JMenuItem menuitem_manageRoute, menuitem_setTicketPrice;
    private JMenuItem menuitem_usersInfo, menuitem_exit;
    //列车车次表格，用户信息表格
    private JTable trainTable, userTable;
    //单个车次信息面板，单个用户信息面板
    private JPanel jp_singleRouteInfo, jp_singleUserInfo, jp_main;
    //用到的下拉框
    private JComboBox<String> com_trainType, com_gender, com_userType;
    //列车实体数组列表
    private ArrayList<Trains> trains = new ArrayList<>();
    //对实体的dao属性
    private TrainsDao trainsDao = new TrainsDao();
    private UsersDao usersDao = new UsersDao();
    //列车信息表头
    private Object[] cols_train = {"车次", "类型", "起点", "终点", "出发时间", "到达时间", "用时"};
    private Object[][] rows_train;
    //单个列车信息显示框及功能按钮
    private JTextField txt_trainNo, txt_startPlace, txt_midPlace, txt_endPlace;
    private JTextField txt_startTime, txt_endTime, txt_runtime;
    private JButton btn_search, btn_confirmSearch, btn_return, btn_input;
    private JButton btn_add, btn_modify, btn_delete;
    private JPanel jp_functionBtn;
    //用户信息表头
    private Object[] cols_user = {"手机号", "密码", "姓名", "性别", "类型", "ID"};
    private Object[][] rows_user;
    //单个用户信息显示框及功能按钮
    private JTextField txt_tel, txt_password, txt_name, txt_id;
    private JButton btn_confirm, btn_deleteUser;
    //票价设置
    private JButton btn_ordinaryTrain, btn_highSpeedTrain, btn_bulletTrain;
    private JLabel lbl_businessSeat, lbl_firstSeat, lbl_secondSeat;
    private JTextField txt_businessSeat, txt_firstSeat, txt_secondSeat;
    private JButton btn_modifyPrice;
    private JPanel jp_seatPrice;
    private int trainTypeNo;

    public AdminWindow(String s) {
        //设置界面题头和符号
        setTitle(s);
        ImageIcon icon = new ImageIcon("picture/logo1.jpg");
        setIconImage(icon.getImage());

        //设置系统界面菜单条
        this.setMenuBar();

        //初始化列车信息表格
        trainTable = new JTable(rows_train, cols_train);
        //为列车信息添加监视器
        trainTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showSelectedRouteInfo();//选中某行会出现在信息显示模块
            }
        });

        //初始化显示单个车次信息的模块
        initSingleRouteInfoModule();

        //初始化票价管理模块
        initPriceModule();

        //初始化所有用户信息表格
        userTable = new JTable(rows_user, cols_user);
        //为用户信息表格添加监视器
        userTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showSelectedUserInfo();
            }
        });

        //初始化显示单个用户信息的模块
        initSingleUserInfoModule();

        //主面板
        jp_main = new JPanel();
        jp_main.setLayout(new BorderLayout());
        this.add(jp_main);
        this.validate();
        this.setVisible(true);
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //实现监视器中的方法
    public void actionPerformed(ActionEvent e) {
        //路线管理事件，获得所有列车信息数组列表并生成表格，更新主面板
        if (e.getSource() == menuitem_manageRoute) {
            trains = trainsDao.trainQueryAll();
            this.updateTrainTable();
            jp_main.removeAll();
            jp_main.setLayout(new BorderLayout());
            jp_main.add(new JScrollPane(trainTable), BorderLayout.CENTER);
            jp_main.add(jp_singleRouteInfo, BorderLayout.SOUTH);
            jp_main.updateUI();
            return;
        }
        //查询按钮事件
        if (e.getSource() == btn_search) {
            this.clearText(); //清除文本框内容
            txt_startTime.setEditable(false);
            txt_endTime.setEditable(false);
            txt_runtime.setEditable(false);
            txt_midPlace.setEditable(false);
            jp_functionBtn.removeAll();
            jp_functionBtn.add(btn_confirmSearch);
            jp_functionBtn.add(btn_return);
            jp_functionBtn.updateUI();
            return;
        }
        //确认查询路线
        if (e.getSource() == btn_confirmSearch) {
            String startPlace = txt_startPlace.getText().trim();
            String endPlace = txt_endPlace.getText().trim();
            int trainTypeNo = com_trainType.getSelectedIndex();
            trains = this.searchTrain(startPlace, endPlace, trainTypeNo);
            this.updateTrainTable();
            return;
        }
        //查询后的返回按钮
        if (e.getSource() == btn_return) {
            txt_startTime.setEditable(true);
            txt_endTime.setEditable(true);
            txt_runtime.setEditable(true);
            txt_midPlace.setEditable(true);
            jp_functionBtn.removeAll();
            jp_functionBtn.add(btn_search);
            jp_functionBtn.add(btn_input);
            jp_functionBtn.add(btn_modify);
            jp_functionBtn.add(btn_delete);
            jp_functionBtn.updateUI();
            //更新车次信息表
            trains = trainsDao.trainQueryAll();
            this.updateTrainTable();
            this.clearText();
            return;
        }
        //录入路线按钮
        if (e.getSource() == btn_input) {
            this.clearText();
            txt_trainNo.setEditable(true);
            jp_functionBtn.removeAll();
            jp_functionBtn.add(btn_add);
            jp_functionBtn.updateUI();
            return;
        }
        //确认增加路线按钮
        if (e.getSource() == btn_add) {
            this.addTrain();
            txt_trainNo.setEditable(false);
            jp_functionBtn.removeAll();
            jp_functionBtn.add(btn_search);
            jp_functionBtn.add(btn_input);
            jp_functionBtn.add(btn_modify);
            jp_functionBtn.add(btn_delete);
            jp_functionBtn.updateUI();
            //更新车次信息
            trains = trainsDao.trainQueryAll();
            this.updateTrainTable();
            return;
        }
        //修改路线
        if (e.getSource() == btn_modify) {
            String trainNo = txt_trainNo.getText().trim();
            if (trainNo.equals("")) {
                JOptionPane.showMessageDialog(this, "请先选择要修改的路线！", "提醒", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int k = JOptionPane.showConfirmDialog(this, "确定修改此路线吗？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (k == 0) {
                this.updateTrain();
            }
            //更新车次信息
            trains = trainsDao.trainQueryAll();
            this.updateTrainTable();
            return;
        }
        //删除路线
        if (e.getSource() == btn_delete) {
            String trainNo = txt_trainNo.getText().trim();
            if (trainNo.equals("")) {
                JOptionPane.showMessageDialog(this, "请先选择要删除的路线！", "提醒", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int k = JOptionPane.showConfirmDialog(this, "确定删除此路线吗？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (k == 0) {
                this.deleteTrain(trainNo);
            }
            //更新车次信息
            trains = trainsDao.trainQueryAll();
            this.updateTrainTable();
            return;
        }

        //设置车票价格
        if (e.getSource() == menuitem_setTicketPrice) {
            trainTypeNo = 0;
            lbl_businessSeat.setText("普通列车商务座:");
            lbl_firstSeat.setText("普通列车一等座:");
            lbl_secondSeat.setText("普通列车二等座:");
            this.showSeatPrice();

            jp_main.removeAll();
            jp_main.setLayout(new BorderLayout());
            jp_main.add(jp_seatPrice, BorderLayout.CENTER);
            jp_main.updateUI();
            return;
        }

        //不同列车的座位等级
        if (e.getSource() == btn_ordinaryTrain) {
            trainTypeNo = 0;
            lbl_businessSeat.setText("普通列车商务座:");
            lbl_firstSeat.setText("普通列车一等座:");
            lbl_secondSeat.setText("普通列车二等座:");
            jp_main.updateUI();
            this.showSeatPrice();
            return;
        }
        if (e.getSource() == btn_highSpeedTrain) {
            trainTypeNo = 1;
            lbl_businessSeat.setText("高铁商务座:");
            lbl_firstSeat.setText("高铁一等座:");
            lbl_secondSeat.setText("高铁二等座:");
            this.showSeatPrice();
            return;
        }
        if (e.getSource() == btn_bulletTrain) {
            trainTypeNo = 2;
            lbl_businessSeat.setText("动车商务座:");
            lbl_firstSeat.setText("动车一等座:");
            lbl_secondSeat.setText("动车二等座:");
            this.showSeatPrice();
            return;
        }

        //确认修改票价事件
        if (e.getSource() == btn_modifyPrice) {
            if (txt_businessSeat.getText().equals("") ||
                    txt_firstSeat.getText().equals("") ||
                    txt_secondSeat.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "票价均不能为空", "提醒", JOptionPane.WARNING_MESSAGE);
                return;
            }
            this.modifySeatPrice(); //调用修改票价方法
            this.showSeatPrice(); //更新修改后的后的票价
            return;
        }

        //用户服务事件
        if (e.getSource() == menuitem_usersInfo) {
            this.updateUserTable();
            jp_main.removeAll();
            jp_main.setLayout(new BorderLayout());
            jp_main.add(new JScrollPane(userTable), BorderLayout.CENTER);
            jp_main.add(jp_singleUserInfo, BorderLayout.SOUTH);
            jp_main.updateUI();
            return;
        }
        //修改用户信息
        if (e.getSource() == btn_confirm) {
            this.updateUser();
            this.updateUserTable();//更新用户表
            return;
        }
        //删除用户
        if (e.getSource() == btn_deleteUser) {
            int r = JOptionPane.showConfirmDialog(this, "确认删除此用户？", "提醒", JOptionPane.OK_CANCEL_OPTION);
//			System.out.println(r);//确认则r返回为0
            if (r == 0) {
                this.deleteUser();
                this.updateUserTable();//更新用户表
                return;
            }
        }
        //退出系统
        if (e.getSource() == menuitem_exit) {
            int k = JOptionPane.showConfirmDialog(this, "是否退出系统？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (k == 0) {
                dispose();
            }
        }
    }

    /*******************方法**********************/

    //更新列车信息表格，利用全局变量的数组列表trains
    private void updateTrainTable() {
        rows_train = new Object[trains.size()][cols_train.length];
        int j = 0;
        for (Trains train : trains) {
            String trainType = "";
            if (train.getTraintypeno() == 0) {
                trainType = "普通列车";
            } else if (train.getTraintypeno() == 1) {
                trainType = "高铁";
            } else if (train.getTraintypeno() == 2) {
                trainType = "动车";
            }
            rows_train[j][0] = train.getTrainno();
            rows_train[j][1] = trainType;
            rows_train[j][2] = train.getStartplace();
            rows_train[j][3] = train.getEndplace();
            rows_train[j][4] = train.getStarttime();
            rows_train[j][5] = train.getEndtime();
            rows_train[j][6] = train.getRuntime();
            ++j;
        }
        trainTable.setModel(new DefaultTableModel(rows_train, cols_train));
    }

    //在路线管理的面板下获得输入的信息用来判断输入是否为空以及判断是否更新路线还是添加路线
    private void judgeAndExecuteOperation(String operation) {
        String trainNo = txt_trainNo.getText().trim();
        int trainTypeNo = com_trainType.getSelectedIndex();
        String startPlace = txt_startPlace.getText().trim();
        String endPlace = txt_endPlace.getText().trim();
        String startTime = txt_startTime.getText().trim();
        String endTime = txt_endTime.getText().trim();
        String input_runtime = txt_runtime.getText().trim();
        //判断是否有效
        if (trainNo.equals("") || startPlace.equals("") || endPlace.equals("")
                || startTime.equals("") || endTime.equals("") || input_runtime.equals("")) {
            JOptionPane.showMessageDialog(this, "信息均不能为空", "提醒", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        //执行更新操作
        if (operation.equals("update")) {
            trainsDao.updateTrain(trainNo, trainTypeNo, startPlace, endPlace, startTime, endTime, input_runtime);
            JOptionPane.showMessageDialog(this, "更新成功", "提醒", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        //执行添加列车操作
        trainsDao.addTrain(trainNo, trainTypeNo, startPlace, endPlace, startTime, endTime, input_runtime);
        JOptionPane.showMessageDialog(this, "添加成功", "提醒", JOptionPane.PLAIN_MESSAGE);
    }

    //清除文本框内容并使车次可输入
    private void clearText() {
        txt_trainNo.setText("");
        com_trainType.setSelectedIndex(0);
        txt_startPlace.setText("");
        txt_endPlace.setText("");
        txt_startTime.setText("");
        txt_endTime.setText("");
        txt_runtime.setText("");
    }

    //查询列车，返回列车信息数组列表
    private ArrayList<Trains> searchTrain(String startPlace, String endPlace, int trainTypeNo) {
        return trainsDao.trainQueryByAdmin(startPlace, endPlace, trainTypeNo);
    }

    //增加列车
    private void addTrain() {
        this.judgeAndExecuteOperation("add");
    }

    //修改列车信息
    private void updateTrain() {
        this.judgeAndExecuteOperation("update");
    }

    //删除列车
    private void deleteTrain(String trainNo) {
        boolean success = trainsDao.deleteTrain(trainNo);
        if (success) {
            JOptionPane.showMessageDialog(this, "删除成功！", "提醒", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "删除失败！", "提醒", JOptionPane.WARNING_MESSAGE);
    }

    //显示票价信息
    private void showSeatPrice() {
        SeatPriceDao seatPriceDao = new SeatPriceDao();
        SeatPrice seatPrice = seatPriceDao.seatPriceQueryByno1(trainTypeNo);
        int business = seatPrice.getBusinessprice();
        int first = seatPrice.getFirstprice();
        int second = seatPrice.getSecondprice();
        txt_businessSeat.setText(String.valueOf(business));
        txt_firstSeat.setText(String.valueOf(first));
        txt_secondSeat.setText(String.valueOf(second));
    }

    //修改票价
    private void modifySeatPrice() {
        int businessPrice = Integer.parseInt(txt_businessSeat.getText().trim());
        int firstPrice = Integer.parseInt(txt_firstSeat.getText().trim());
        int secondPrice = Integer.parseInt(txt_secondSeat.getText().trim());
        SeatPriceDao seatPriceDao = new SeatPriceDao();
        int k = seatPriceDao.updateSeatPrice(trainTypeNo, businessPrice, firstPrice, secondPrice);
        if (k == 1) {
            JOptionPane.showMessageDialog(this, "票价修改成功", "提醒", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "票价修改失败", "提醒", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //生成所有用户信息表格
    private void updateUserTable() {
        ArrayList<Users> users = usersDao.userQueryAll();
        rows_user = new Object[users.size()][cols_user.length];
        int k = 0;
        while (k < users.size()) {
            String usertype = "";
            if (users.get(k).getUsertypeno() == 0) {
                usertype = "乘客";
            } else if (users.get(k).getUsertypeno() == 1) {
                usertype = "管理员";
            }
            rows_user[k][0] = users.get(k).getUsertel();
            rows_user[k][1] = users.get(k).getPassword();
            rows_user[k][2] = users.get(k).getUsername();
            rows_user[k][3] = users.get(k).getUsergender();
            rows_user[k][4] = usertype;
            rows_user[k][5] = users.get(k).getUserid();
            k++;
        }
        userTable.setModel(new DefaultTableModel(rows_user, cols_user));
    }

    //修改用户信息
    private void updateUser() {
        String tel = txt_tel.getText().trim();
        String pwd = txt_password.getText().trim();
        String name = txt_name.getText().trim();
        String gender = com_gender.getSelectedItem().toString();
        int userTypeNo = com_userType.getSelectedIndex();
        String id = txt_id.getText().trim();
        int r = usersDao.updateUser(tel, pwd, name, gender, userTypeNo, id);
        if (r == 0) {
            JOptionPane.showMessageDialog(this, "更新失败", "提醒", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "修改成功", "提醒", JOptionPane.PLAIN_MESSAGE);
    }

    //删除用户
    private void deleteUser() {
        String userId = txt_id.getText().trim();
        int k = usersDao.deleteUser(userId);
        if (k == 1) {
            JOptionPane.showMessageDialog(this, "删除成功", "提醒", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "删除失败", "提醒", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //初始化菜单条
    private void setMenuBar() {
        //设置菜单条
        JMenuBar menubar = new JMenuBar();
        JMenu menu_route, menu_price, menu_users, menu_exit;
        menu_route = new JMenu("线路管理");
        menu_price = new JMenu("价格管理");
        menu_users = new JMenu("用户服务");
        menu_exit = new JMenu("退出系统");
        menuitem_manageRoute = new JMenuItem("查看线路");
        menuitem_manageRoute.addActionListener(this);
        menuitem_setTicketPrice = new JMenuItem("票价设置");
        menuitem_setTicketPrice.addActionListener(this);
        menuitem_usersInfo = new JMenuItem("修改用户信息");
        menuitem_usersInfo.addActionListener(this);
        menuitem_exit = new JMenuItem("退出系统");
        menuitem_exit.addActionListener(this);
        menu_route.add(menuitem_manageRoute);
        menu_price.add(menuitem_setTicketPrice);
        menu_users.add(menuitem_usersInfo);
        menu_exit.add(menuitem_exit);
        menubar.add(menu_route);
        menubar.add(menu_price);
        menubar.add(menu_users);
        menubar.add(menu_exit);
        this.setJMenuBar(menubar);
    }

    //选中车次表中某一行后显示在车次信息模块中
    private void showSelectedRouteInfo() {
        int row = trainTable.getSelectedRow();
        Object[] value = new Object[trainTable.getColumnCount()];
        for (int i = 0; i < value.length; i++) {
            value[i] = trainTable.getModel().getValueAt(row, i);
        }
        txt_trainNo.setText(value[0].toString());
        com_trainType.setSelectedItem(value[1].toString());
        txt_startPlace.setText(value[2].toString());
        txt_endPlace.setText(value[3].toString());
        txt_startTime.setText(value[4].toString());
        txt_endTime.setText(value[5].toString());
        txt_runtime.setText(value[6].toString());
    }

    //初始化单个车次路线信息模块
    private void initSingleRouteInfoModule() {
        //单个车次信息
        //1.初始化单个车次路线信息标签
        JLabel lbl_trainNo, lbl_trainType;
        JLabel lbl_startPlace, lbl_midPlace, lbl_endPlace;
        JLabel lbl_startTime, lbl_endTime, lbl_runtime;
        lbl_trainNo = new JLabel("车次:");
        lbl_trainNo.setHorizontalAlignment(JLabel.CENTER);
        lbl_trainType = new JLabel("类型:");
        lbl_trainType.setHorizontalAlignment(JLabel.CENTER);
        lbl_startPlace = new JLabel("起点:");
        lbl_startPlace.setHorizontalAlignment(JLabel.CENTER);
        lbl_midPlace = new JLabel("中转站:");
        lbl_midPlace.setHorizontalAlignment(JLabel.CENTER);
        lbl_endPlace = new JLabel("终点:");
        lbl_endPlace.setHorizontalAlignment(JLabel.CENTER);
        lbl_startTime = new JLabel("出发时间:");
        lbl_startTime.setHorizontalAlignment(JLabel.CENTER);
        lbl_endTime = new JLabel("到达时间:");
        lbl_endTime.setHorizontalAlignment(JLabel.CENTER);
        lbl_runtime = new JLabel("用时:");
        lbl_runtime.setHorizontalAlignment(JLabel.CENTER);

        //2.初始化单个车次路线信息输入框
        txt_trainNo = new JTextField(20);
        txt_trainNo.setEditable(false);
        String[] trainType = {"普通列车", "高铁", "动车"};
        com_trainType = new JComboBox<>(trainType);
        txt_startPlace = new JTextField(20);
        txt_midPlace = new JTextField(20);
        txt_endPlace = new JTextField(20);
        txt_startTime = new JTextField(20);
        txt_endTime = new JTextField(20);
        txt_runtime = new JTextField(20);

        //3.初始化单个车次路线信息面板
        JPanel jp_singleRoute = new JPanel();
        jp_singleRoute.setLayout(new GridLayout(2, 4));
        jp_singleRoute.add(lbl_trainNo);
        jp_singleRoute.add(txt_trainNo);
        jp_singleRoute.add(lbl_trainType);
        jp_singleRoute.add(com_trainType);
        jp_singleRoute.add(lbl_startPlace);
        jp_singleRoute.add(txt_startPlace);
        jp_singleRoute.add(lbl_midPlace);
        jp_singleRoute.add(txt_midPlace);
        jp_singleRoute.add(lbl_endPlace);
        jp_singleRoute.add(txt_endPlace);
        jp_singleRoute.add(lbl_startTime);
        jp_singleRoute.add(txt_startTime);
        jp_singleRoute.add(lbl_endTime);
        jp_singleRoute.add(txt_endTime);
        jp_singleRoute.add(lbl_runtime);
        jp_singleRoute.add(txt_runtime);

        //单个车次信息功能按钮
        //1.初始化功能按钮
        btn_search = new JButton("查询");
        btn_search.addActionListener(this);
        btn_confirmSearch = new JButton("确认查询");
        btn_confirmSearch.addActionListener(this);
        btn_return = new JButton("返回");
        btn_return.addActionListener(this);
        btn_input = new JButton("录入");
        btn_input.addActionListener(this);
        btn_add = new JButton("增加路线");
        btn_add.addActionListener(this);
        btn_modify = new JButton("修改");
        btn_modify.addActionListener(this);
        btn_delete = new JButton("删除");
        btn_delete.addActionListener(this);

        //2.初始化功能按钮面板，支持在事件中更新此面板
        jp_functionBtn = new JPanel();
        jp_functionBtn.add(btn_search);
        jp_functionBtn.add(btn_input);
        jp_functionBtn.add(btn_modify);
        jp_functionBtn.add(btn_delete);

        //合并单个车次路线信息面板和操作功能按钮面板
        jp_singleRouteInfo = new JPanel();
        jp_singleRouteInfo.setLayout(new BorderLayout());
        jp_singleRouteInfo.add(jp_singleRoute, BorderLayout.CENTER);
        jp_singleRouteInfo.add(jp_functionBtn, BorderLayout.SOUTH);
    }

    //初始化设置票价模块
    private void initPriceModule() {
        //列车类型面板
        btn_ordinaryTrain = new JButton("普通列车");
        btn_ordinaryTrain.addActionListener(this);
        btn_highSpeedTrain = new JButton("高铁");
        btn_highSpeedTrain.addActionListener(this);
        btn_bulletTrain = new JButton("动车");
        btn_bulletTrain.addActionListener(this);
        JPanel jp_trainClass = new JPanel();
        jp_trainClass.add(btn_ordinaryTrain);
        jp_trainClass.add(btn_highSpeedTrain);
        jp_trainClass.add(btn_bulletTrain);
        //座位类型以及对应票价面板
        lbl_businessSeat = new JLabel("商务座:");
        lbl_businessSeat.setHorizontalAlignment(JLabel.CENTER);
        lbl_firstSeat = new JLabel("一等座:");
        lbl_firstSeat.setHorizontalAlignment(JLabel.CENTER);
        lbl_secondSeat = new JLabel("二等座:");
        lbl_secondSeat.setHorizontalAlignment(JLabel.CENTER);
        txt_businessSeat = new JTextField(10);
        txt_firstSeat = new JTextField(10);
        txt_secondSeat = new JTextField(10);
        JPanel jp_seatClass = new JPanel();
        jp_seatClass.setLayout(new GridLayout(7, 2));
        jp_seatClass.add(new JLabel());
        jp_seatClass.add(new JLabel());
        jp_seatClass.add(lbl_businessSeat);
        jp_seatClass.add(txt_businessSeat);
        jp_seatClass.add(new JLabel());
        jp_seatClass.add(new JLabel());
        jp_seatClass.add(lbl_firstSeat);
        jp_seatClass.add(txt_firstSeat);
        jp_seatClass.add(new JLabel());
        jp_seatClass.add(new JLabel());
        jp_seatClass.add(lbl_secondSeat);
        jp_seatClass.add(txt_secondSeat);
        jp_seatClass.add(new JLabel());
        jp_seatClass.add(new JLabel());
        //修改票价按钮面板
        btn_modifyPrice = new JButton("修改价格");
        btn_modifyPrice.addActionListener(this);
        JPanel jp_modifyPriceBtn = new JPanel();
        jp_modifyPriceBtn.add(btn_modifyPrice);

        //票价管理总面板
        jp_seatPrice = new JPanel();
        jp_seatPrice.setLayout(new BorderLayout());
        jp_seatPrice.add(jp_trainClass, BorderLayout.NORTH);
        jp_seatPrice.add(jp_seatClass, BorderLayout.CENTER);
        jp_seatPrice.add(jp_modifyPriceBtn, BorderLayout.SOUTH);
    }

    //初始化单个用户信息显示模块
    private void initSingleUserInfoModule() {
        JLabel lbl_tel, lbl_password, lbl_name, lbl_gender, lbl_id, lbl_type;
        lbl_tel = new JLabel("手机号:");
        lbl_tel.setHorizontalAlignment(JLabel.CENTER);
        lbl_password = new JLabel("密码:");
        lbl_password.setHorizontalAlignment(JLabel.CENTER);
        lbl_name = new JLabel("姓名:");
        lbl_name.setHorizontalAlignment(JLabel.CENTER);
        lbl_gender = new JLabel("性别:");
        lbl_gender.setHorizontalAlignment(JLabel.CENTER);
        lbl_type = new JLabel("类型:");
        lbl_type.setHorizontalAlignment(JLabel.CENTER);
        lbl_id = new JLabel("ID:");
        lbl_id.setHorizontalAlignment(JLabel.CENTER);
        //个人信息录入框
        txt_tel = new JTextField(20);
        txt_password = new JTextField(20);
        txt_name = new JTextField(20);
        com_gender = new JComboBox<>(new String[]{"男", "女"});
        com_userType = new JComboBox<>(new String[]{"乘客", "管理员"});
        txt_id = new JTextField(20);
        txt_id.setEditable(false);
        //个人信息显示面板
        JPanel jp_showUserInfo = new JPanel();
        jp_showUserInfo.setLayout(new GridLayout(2, 6));
        jp_showUserInfo.add(lbl_tel);
        jp_showUserInfo.add(txt_tel);
        jp_showUserInfo.add(lbl_password);
        jp_showUserInfo.add(txt_password);
        jp_showUserInfo.add(lbl_name);
        jp_showUserInfo.add(txt_name);
        jp_showUserInfo.add(lbl_gender);
        jp_showUserInfo.add(com_gender);
        jp_showUserInfo.add(lbl_type);
        jp_showUserInfo.add(com_userType);
        jp_showUserInfo.add(lbl_id);
        jp_showUserInfo.add(txt_id);

        //功能按钮
        btn_confirm = new JButton("确认修改");
        btn_confirm.addActionListener(this);
        btn_deleteUser = new JButton("删除用户");
        btn_deleteUser.addActionListener(this);
        JPanel jp_confirm_delete = new JPanel();
        jp_confirm_delete.add(btn_confirm);
        jp_confirm_delete.add(btn_deleteUser);

        //总面板
        this.jp_singleUserInfo = new JPanel();
        this.jp_singleUserInfo.setLayout(new BorderLayout());
        this.jp_singleUserInfo.add(jp_showUserInfo, BorderLayout.CENTER);
        this.jp_singleUserInfo.add(jp_confirm_delete, BorderLayout.SOUTH);
    }

    //选中用户信息后显示在单个用户信息模块中
    private void showSelectedUserInfo() {
        int row = userTable.getSelectedRow();
        Object[] value = new Object[cols_user.length];
        for (int i = 0; i < cols_user.length; i++) {
            value[i] = userTable.getModel().getValueAt(row, i);
        }
        txt_tel.setText(value[0].toString());
        txt_password.setText(value[1].toString());
        txt_name.setText(value[2].toString());
        com_gender.setSelectedItem(value[3].toString());
        com_userType.setSelectedItem(value[4].toString());
        txt_id.setText(value[5].toString());
    }

    //更新主面板的表格和信息模块
    private void updateMainPanel(JTable tablePanel, JPanel panel) {
        jp_main.removeAll();
        jp_main.add(tablePanel, BorderLayout.CENTER);
        jp_main.add(panel, BorderLayout.SOUTH);
        jp_main.updateUI();
    }

    //主方法
//    public static void main(String[] args) {
//        new AdminWindow("管理员");
//    }
}
