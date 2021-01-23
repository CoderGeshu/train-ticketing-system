package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import dao.*;
import dbutil.Background;
import entity.*;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:47
 * @author: Eric
 * @Description: TODO 乘客界面类
 */
public class PassengerWindow extends JFrame implements ActionListener {
    private JButton btn_ticketBusiness, btn_contact, btn_search;
    private JTextField txt_from, txt_to, txt_departure;
    private JRadioButton rbtn_student, rbtn_highSpeedTrain;
    private JButton btn_buy, btn_orderSheet, btn_switch, btn_exit;
    //联系人信息
    private JButton btn_self, btn_contact1, btn_contact2, btn_modifyContact;
    private JTextField txt_tel, txt_password, txt_name, txt_id;
    //面板
    private JPanel jp_right, jp_contact, jp_ticketBusiness;
    //联系人的编号
    private int contactno;

    //获得从登录界面进来的乘客实体
    private static Users user = LoginWindow.getUser();

    public PassengerWindow(String s) {
        //设置界面题头和符号
        setTitle(s);
        String iconSrc = "picture/logo1.jpg";
        ImageIcon icon = new ImageIcon(iconSrc);
        setIconImage(icon.getImage());

        //自定义设置界面背景
        String bgdSrc = "picture/hexiehao3.jpg";
        ImageIcon background = new ImageIcon(bgdSrc);
        Background.setBackgroundPicture(this, background);

        //初始化车票业务和联系人信息功能按钮
        initTicketAndContactButton();
        //初始化车票业务模块
        initTicketBusinessModule();
        //初始化联系人信息模块
        initContactInfoModule();

        //主界面左部分模块:分为车票业务按钮和常用联系人按钮
        JPanel jp_left = new JPanel();
        jp_left.setOpaque(false);
        jp_left.setLayout(new GridLayout(2, 1));
        jp_left.add(btn_ticketBusiness);
        jp_left.add(btn_contact);

        //主界面右部分模块总面板：初始显示车票业务面板
        jp_right = new JPanel();
        jp_right.setOpaque(false);
        jp_right.setLayout(new BorderLayout());
        jp_right.add(jp_ticketBusiness, BorderLayout.CENTER);

        //把左右模块面板添加到主窗体
        this.add(jp_left, BorderLayout.WEST);
        this.add(jp_right, BorderLayout.CENTER);
        this.validate();
        this.setVisible(true);
        this.setSize(700, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        //选择车票业务或联系人事件
        if (e.getSource() == btn_ticketBusiness) {
            jp_right.removeAll();
            jp_right.add(jp_ticketBusiness);
            jp_right.updateUI();
            return;
        }
        //订单事件
        if (e.getSource() == btn_orderSheet) {
            new OrderSheetWindow("我的车票", user.getUserid());
            return;
        }
        //切换用户
        if (e.getSource() == btn_switch) {
            btn_buy.setForeground(Color.white);
            btn_switch.setForeground(Color.blue);
            btn_exit.setForeground(Color.white);
            int k = JOptionPane.showConfirmDialog(this, "确认切换账户？", "提醒", JOptionPane.OK_CANCEL_OPTION);
//			System.out.println(k);
            if (k == 0) {
                new LoginWindow("登陆");
                this.dispose();
            } else {
                btn_buy.setForeground(Color.blue);
                btn_switch.setForeground(Color.white);
                btn_exit.setForeground(Color.white);
            }
        }
        //退出系统
        if (e.getSource() == btn_exit) {
            btn_buy.setForeground(Color.white);
            btn_switch.setForeground(Color.white);
            btn_exit.setForeground(Color.blue);
            int k = JOptionPane.showConfirmDialog(this, "是否退出系统？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (k == 0) {
                dispose();
            } else {
                btn_buy.setForeground(Color.blue);
                btn_switch.setForeground(Color.white);
                btn_exit.setForeground(Color.white);
            }
        }
        //查询购票事件
        if (e.getSource() == btn_search) {
            String from = txt_from.getText().trim();
            String to = txt_to.getText().trim();
            String departure = txt_departure.getText().toString().trim();
            int traintypeno;
            if (rbtn_highSpeedTrain.isSelected()) {
                traintypeno = 0;
            } else {
                traintypeno = 3; //在列车类型编号中没有3,即可以搜出所有类型的车
            }
            if (from.equals("") || to.equals("") || departure.equals("")) {
                JOptionPane.showMessageDialog(this, "输入均不能为空", "注意", JOptionPane.WARNING_MESSAGE);
            } else {
                new SearchResultWindow("查询结果", from, to, departure, user.getUsertel(), traintypeno);
            }
        }
        //联系人详细信息事件
        if (e.getSource() == btn_contact || e.getSource() == btn_self) {
            contactno = 0;//表示自己
            jp_contact.setBorder(new TitledBorder("联系人"));
            jp_right.removeAll();
            jp_right.add(jp_contact);
            jp_right.updateUI();
            btn_self.setForeground(Color.blue);
            btn_contact1.setForeground(Color.white);
            btn_contact2.setForeground(Color.white);
            getSelfInfo();
            validate();
            return;
        }
        if (e.getSource() == btn_contact1) {
            contactno = 1;
            btn_self.setForeground(Color.white);
            btn_contact1.setForeground(Color.blue);
            btn_contact2.setForeground(Color.white);
            getContactInfo();
            return;
        }
        if (e.getSource() == btn_contact2) {
            contactno = 2;
            btn_self.setForeground(Color.white);
            btn_contact1.setForeground(Color.white);
            btn_contact2.setForeground(Color.blue);
            getContactInfo();
            return;
        }
        //修改联系人信息，联系人编号为0则修改自己的信息
        if (e.getSource() == btn_modifyContact) {
            int r = JOptionPane.showConfirmDialog(this, "确认修改信息？", "注意", JOptionPane.OK_CANCEL_OPTION);
            if (r != 0) {
                return;
            }
            if (contactno == 0) {
                modifySelf();
            } else {
                modifyContact();
            }
        }
    }

    //获得自己的信息
    public void getSelfInfo() {
        txt_tel.setEditable(false);
        txt_password.setEditable(true);//允许修改自己的密码
        txt_id.setEditable(false);
        txt_tel.setText(user.getUsertel());
        txt_password.setText(user.getPassword());
        txt_name.setText(user.getUsername());
        txt_id.setText(user.getUserid());
    }

    //获得联系人信息
    private void getContactInfo() {
        txt_tel.setEditable(true);
        txt_password.setEditable(false);
        txt_id.setEditable(true);

        ContactsDao contactDao = new ContactsDao();
        //判断用户的此编号联系人是否已经存在，若不存在则创建新的空联系人
        boolean haveExist = contactDao.haveExist(user.getUserid(), contactno);
        if (!haveExist) {
            contactDao.addContact(user.getUserid(), contactno);
            txt_tel.setText("");
            txt_password.setText("*******");
            txt_name.setText("");
            txt_id.setText("");
            validate();
            return;
        }
        //如果存在就查询联系人信息
        Contacts contact = contactDao.contactsQueryById(user.getUserid(), contactno);
        txt_tel.setText(contact.getContacttel());
        txt_password.setText("*******");
        txt_name.setText(contact.getContactname());
        txt_id.setText(contact.getContactid());
        validate();
    }

    //修改自己的信息
    public void modifySelf() {
        String selftel = txt_tel.getText().trim();
        String selfpassword = txt_password.getText().trim();
        String selfname = txt_name.getText().trim();
        String selfid = txt_id.getText().trim();
        if (selftel.equals("") || selfpassword.equals("") || selfname.equals("")) {
            JOptionPane.showMessageDialog(this, "输入均不能为空", "提醒", JOptionPane.WARNING_MESSAGE);
        } else {
            UsersDao usersDao = new UsersDao();
            int k = usersDao.modifySelfInfo(selftel, selfpassword, selfname, selfid);
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "修改成功", "提醒", JOptionPane.DEFAULT_OPTION);
                user = usersDao.userQueryByTel(user.getUsertel());//修改后更新乘客实体
                getSelfInfo();
            } else {
                JOptionPane.showMessageDialog(this, "修改失败", "提醒", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    //修改联系人信息
    public void modifyContact() {
        String userid = user.getUserid();
        String contacttel = txt_tel.getText().trim();
        String contactname = txt_name.getText().trim();
        String contactid = txt_id.getText().trim();
        if (contacttel.equals("") || contactname.equals("") || contactid.equals("")) {
            JOptionPane.showMessageDialog(this, "输入均不能为空", "提醒", JOptionPane.WARNING_MESSAGE);
        } else {
            ContactsDao contactDao = new ContactsDao();
            int k = contactDao.modifyContact(userid, contactname, contacttel, contactid, contactno);
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "修改成功", "提醒", JOptionPane.DEFAULT_OPTION);
                getContactInfo();//获得修改后的联系人信息
            } else {
                JOptionPane.showMessageDialog(this, "修改失败", "提醒", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //主界面左部分模块:分为车票业务按钮和常用联系人按钮
    private void initTicketAndContactButton() {
        //1.车票业务模块按钮
        btn_ticketBusiness = new JButton("车票业务");
        btn_ticketBusiness.setOpaque(false);
        btn_ticketBusiness.addActionListener(this);
        btn_ticketBusiness.setForeground(Color.BLACK);
        btn_ticketBusiness.setBackground(Color.DARK_GRAY); // 不设置此项按钮不透明
        btn_ticketBusiness.setHorizontalAlignment(JButton.CENTER);
        btn_ticketBusiness.setFont(new Font("楷体", Font.PLAIN, 30));
        //2.联系人模块按钮
        btn_contact = new JButton("联系人");
        btn_contact.setOpaque(false);
        btn_contact.addActionListener(this);
        btn_contact.setForeground(Color.black);
        btn_contact.setBackground(Color.DARK_GRAY);
        btn_contact.setHorizontalAlignment(JLabel.CENTER);
        btn_contact.setFont(new Font("楷体", Font.PLAIN, 30));
    }

    //初始化车票业务模块
    // 车票业务模块又分为四大模块：购票、订单查询、切换用户和退出系统
    private void initTicketBusinessModule() {
        //1. 车票业务界面四大模块功能按钮
        //1.1 购票模块按钮
        btn_buy = new JButton("购票");
        btn_buy.addActionListener(this);
        btn_buy.setForeground(Color.blue);
        btn_buy.setBackground(Color.ORANGE);
        btn_buy.setFont(new Font("楷体", Font.PLAIN, 25));
        //1.2 订单模块按钮
        btn_orderSheet = new JButton("订单");
        btn_orderSheet.addActionListener(this);
        btn_orderSheet.setForeground(Color.white);
        btn_orderSheet.setBackground(Color.ORANGE);
        btn_orderSheet.setFont(new Font("楷体", Font.PLAIN, 25));
        //1.3 切换账户模块按钮
        btn_switch = new JButton("切换账户");
        btn_switch.addActionListener(this);
        btn_switch.setForeground(Color.white);
        btn_switch.setBackground(Color.ORANGE);
        btn_switch.setFont(new Font("楷体", Font.PLAIN, 25));
        //1.4 退出系统按钮
        btn_exit = new JButton("退出");
        btn_exit.addActionListener(this);
        btn_exit.setForeground(Color.white);
        btn_exit.setBackground(Color.ORANGE);
        btn_exit.setFont(new Font("楷体", Font.PLAIN, 25));
        //1.5 车票业务界面上部功能按钮面板
        JPanel jp_ticketTop = new JPanel();
        jp_ticketTop.setOpaque(false);
        jp_ticketTop.add(btn_buy);
        jp_ticketTop.add(new JLabel("   "));
        jp_ticketTop.add(btn_orderSheet);
        jp_ticketTop.add(new JLabel("   "));
        jp_ticketTop.add(btn_switch);
        jp_ticketTop.add(new JLabel("   "));
        jp_ticketTop.add(btn_exit);

        //2.车票业务界面中间面板
        //2.1 出发地
        JLabel lbl_from = new JLabel("出发地:");
        lbl_from.setForeground(Color.black);
        lbl_from.setHorizontalAlignment(JLabel.CENTER);
        lbl_from.setFont(new Font("楷体", Font.PLAIN, 30));
        //2.2 到达地
        JLabel lbl_to = new JLabel("到达地:");
        lbl_to.setForeground(Color.black);
        lbl_to.setHorizontalAlignment(JLabel.CENTER);
        lbl_to.setFont(new Font("楷体", Font.PLAIN, 30));
        //2.3 出发日期
        JLabel lbl_departure = new JLabel("出发日期:");
        lbl_departure.setForeground(Color.black);
        lbl_departure.setHorizontalAlignment(JLabel.CENTER);
        lbl_departure.setFont(new Font("楷体", Font.PLAIN, 30));
        //2.3 录入信息框
        txt_from = new JTextField(10);
        txt_to = new JTextField(10);
        txt_departure = new JTextField(10);
        //2.4 车票业务界面中间面板
        JPanel jp_tickedMid = new JPanel();
        jp_tickedMid.setOpaque(false);
        jp_tickedMid.setLayout(new GridLayout(7, 2));
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(lbl_from);
        jp_tickedMid.add(txt_from);
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(lbl_to);
        jp_tickedMid.add(txt_to);
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(lbl_departure);
        jp_tickedMid.add(txt_departure);
        jp_tickedMid.add(new JLabel());
        jp_tickedMid.add(new JLabel());

        //3.车票业务界面底部面板
        //3.1 学生单选按钮
        rbtn_student = new JRadioButton("学生");
        rbtn_student.setOpaque(false);
        rbtn_student.setForeground(Color.white);
        rbtn_student.setFont(new Font("楷体", Font.PLAIN, 25));
        //3.2 只看高铁/动车单选按钮
        rbtn_highSpeedTrain = new JRadioButton("只看高铁/动车");
        rbtn_highSpeedTrain.setOpaque(false);
        rbtn_highSpeedTrain.setForeground(Color.white);
        rbtn_highSpeedTrain.setFont(new Font("楷体", Font.PLAIN, 25));
        //3.3 多选框模块面板
        JPanel jp_moreChoice = new JPanel();
        jp_moreChoice.setOpaque(false);
        jp_moreChoice.add(rbtn_student);
        jp_moreChoice.add(new JLabel("      "));
        jp_moreChoice.add(rbtn_highSpeedTrain);
        //3.3 查票功能按钮
        btn_search = new JButton("查询/购票");
        btn_search.addActionListener(this);
        btn_search.setBackground(Color.orange);
        btn_search.setFont(new Font("楷体", Font.PLAIN, 20));
        //3.4 车票业务界面底部面板
        JPanel jp_ticketBottom = new JPanel();
        jp_ticketBottom.setOpaque(false);
        jp_ticketBottom.setLayout(new BorderLayout());
        jp_ticketBottom.add(jp_moreChoice, BorderLayout.CENTER);
        jp_ticketBottom.add(btn_search, BorderLayout.SOUTH);

        //把上中下面板添加到车票业务模块总面板
        jp_ticketBusiness = new JPanel();
        jp_ticketBusiness.setOpaque(false);
        jp_ticketBusiness.setBorder(new TitledBorder("车票查询"));
        jp_ticketBusiness.setLayout(new BorderLayout());
        jp_ticketBusiness.add(jp_ticketTop, BorderLayout.NORTH);
        jp_ticketBusiness.add(jp_tickedMid, BorderLayout.CENTER);
        jp_ticketBusiness.add(jp_ticketBottom, BorderLayout.SOUTH);
    }

    //初始化联系人信息模块
    private void initContactInfoModule() {
        //联系人界面：个人资料模块、联系人1模块、联系人2模块
        jp_contact = new JPanel();
        jp_contact.setOpaque(false);
        JLabel lbl_tel, lbl_pwd, lbl_name, lbl_id;
        //联系人界面上部模块
        //1.个人资料按钮
        btn_self = new JButton("个人资料");
        btn_self.setOpaque(false);
        btn_self.addActionListener(this);
        btn_self.setBackground(Color.GRAY);
        btn_self.setForeground(Color.white);
        btn_self.setHorizontalAlignment(JButton.CENTER);
        btn_self.setFont(new Font("楷体", Font.PLAIN, 20));
        //2.联系人1按钮
        btn_contact1 = new JButton("联系人1");
        btn_contact1.setOpaque(false);
        btn_contact1.addActionListener(this);
        btn_contact1.setBackground(Color.GRAY);
        btn_contact1.setForeground(Color.white);
        btn_contact1.setHorizontalAlignment(JButton.CENTER);
        btn_contact1.setFont(new Font("楷体", Font.PLAIN, 20));
        //3.联系人2按钮
        btn_contact2 = new JButton("联系人2");
        btn_contact2.setOpaque(false);
        btn_contact2.addActionListener(this);
        btn_contact2.setBackground(Color.GRAY);
        btn_contact2.setForeground(Color.white);
        btn_contact2.setHorizontalAlignment(JButton.CENTER);
        btn_contact2.setFont(new Font("楷体", Font.PLAIN, 20));
        //4.联系人界面上部模块面板
        JPanel jp_contactTop = new JPanel();
        jp_contactTop.setOpaque(false);
        jp_contactTop.add(btn_self);
        jp_contactTop.add(btn_contact1);
        jp_contactTop.add(btn_contact2);

        //联系人界面中部基本信息模块
        //1.标签
        //1.1 手机号
        lbl_tel = new JLabel("手机号:");
        lbl_tel.setHorizontalAlignment(JLabel.CENTER);
        lbl_tel.setFont(new Font("楷体", Font.PLAIN, 30));
        //1.2 密码
        lbl_pwd = new JLabel("密码:");
        lbl_pwd.setHorizontalAlignment(JLabel.CENTER);
        lbl_pwd.setFont(new Font("楷体", Font.PLAIN, 30));
        //1.3姓名
        lbl_name = new JLabel("姓名:");
        lbl_name.setHorizontalAlignment(JLabel.CENTER);
        lbl_name.setFont(new Font("楷体", Font.PLAIN, 30));
        //1.4身份证号
        lbl_id = new JLabel("身份证号:");
        lbl_id.setFont(new Font("楷体", Font.PLAIN, 30));
        lbl_id.setHorizontalAlignment(JLabel.CENTER);
        //2.显示信息框
        //2.1 电话
        txt_tel = new JTextField();
        txt_tel.setOpaque(false);
		txt_tel.setEditable(false);
        txt_tel.setForeground(Color.white);
        txt_tel.setFont(new Font("楷体", Font.PLAIN, 20));
        //2.2 密码
        txt_password = new JTextField();
        txt_password.setOpaque(false);
        txt_password.setForeground(Color.white);
        txt_password.setFont(new Font("楷体", Font.PLAIN, 20));
        //2.3 姓名
        txt_name = new JTextField();
        txt_name.setOpaque(false);
        txt_name.setForeground(Color.white);
        txt_name.setFont(new Font("楷体", Font.PLAIN, 20));
        //2.4 身份证号
        txt_id = new JTextField();
        txt_id.setOpaque(false);
        txt_id.setEditable(false);
        txt_id.setForeground(Color.WHITE);
        txt_id.setFont(new Font("楷体", Font.PLAIN, 20));
        //3.联系人界面中部信息面板
        JPanel jp_contactMid = new JPanel();
        jp_contactMid.setOpaque(false);
        jp_contactMid.setLayout(new GridLayout(4, 2));
        jp_contactMid.add(lbl_tel);
        jp_contactMid.add(txt_tel);
        jp_contactMid.add(lbl_pwd);
        jp_contactMid.add(txt_password);
        jp_contactMid.add(lbl_name);
        jp_contactMid.add(txt_name);
        jp_contactMid.add(lbl_id);
        jp_contactMid.add(txt_id);

        //联系人界面底部修改信息按钮
        btn_modifyContact = new JButton("修改");
        btn_modifyContact.addActionListener(this);
        btn_modifyContact.setBackground(Color.orange);
        btn_modifyContact.setHorizontalAlignment(JButton.CENTER);
        btn_modifyContact.setFont(new Font("楷体", Font.PLAIN, 20));

        //联系人界面模块总模板
        jp_contact = new JPanel();
        jp_contact.setOpaque(false);
        jp_contact.setLayout(new BorderLayout());
        jp_contact.add(jp_contactTop, BorderLayout.NORTH);
        jp_contact.add(jp_contactMid, BorderLayout.CENTER);
        jp_contact.add(btn_modifyContact, BorderLayout.SOUTH);
    }

//    public static void main(String[] args) {
//        new PassengerWindow("用户");
//    }
}
