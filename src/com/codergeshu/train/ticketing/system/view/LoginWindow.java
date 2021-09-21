package com.codergeshu.train.ticketing.system.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.codergeshu.train.ticketing.system.dao.*;
import com.codergeshu.train.ticketing.system.utils.Background;
import com.codergeshu.train.ticketing.system.entity.Users;

/**
 * @Project: StationTicketingSystem
 * @Date: 2020/2/23 18:44
 * @author: Eric
 * @Description: TODO 登录界面类
 */
public class LoginWindow extends JFrame implements ActionListener {

    private JTextField txt_tel;                           //用户电话
    private JPasswordField txt_password;                  //用户密码
    private JComboBox<String> com_role;                   //用户角色类型
    private JButton btn_login, btn_register, btn_cancel;  //功能按钮
    private static Users user; //登录的用户信息，在乘客界面中用到


    public LoginWindow(String title) {
        //设置登录界面题头和符号
        setTitle(title);
        String iconSrc = "picture/logo1.jpg";
        ImageIcon icon = new ImageIcon(iconSrc);
        setIconImage(icon.getImage());

        //自定义设置主界面主面板的背景
        String bgdSrc = "picture/rail5.jpg";
        ImageIcon background = new ImageIcon(bgdSrc);
        Background.setBackgroundPicture(this, background);

        //界面显示信息面板
        JLabel lbl_show = new JLabel("售票登陆系统");
        lbl_show.setForeground(Color.WHITE);
        lbl_show.setFont(new Font("楷体", Font.PLAIN, 65));
        lbl_show.setHorizontalAlignment(JLabel.CENTER);
        JPanel jp_show = new JPanel();
        jp_show.setOpaque(false);
        jp_show.add(lbl_show);

        //用户信息模块
        //1.手机号
        JLabel lbl_tel;
        lbl_tel = new JLabel("手机号:");
        lbl_tel.setForeground(Color.WHITE);
        lbl_tel.setFont(new Font("楷体", Font.BOLD, 30));
        lbl_tel.setHorizontalAlignment(SwingConstants.CENTER);
        //2.密码
        JLabel lbl_password = new JLabel("密  码:");
        lbl_password.setForeground(Color.WHITE);
        lbl_password.setFont(new Font("楷体", Font.BOLD, 30));
        lbl_password.setHorizontalAlignment(SwingConstants.CENTER);
        //3.身份选择
        JLabel lbl_role = new JLabel("身  份:");
        lbl_role.setForeground(Color.WHITE);
        lbl_role.setFont(new Font("楷体", Font.BOLD, 30));
        lbl_role.setHorizontalAlignment(SwingConstants.CENTER);
        //4.信息输入框
        txt_tel = new JTextField(15);
        txt_password = new JPasswordField(20);
        com_role = new JComboBox<>(new String[]{"乘客", "管理员"});
        //5.用户信息面板（排版）
        JPanel jp_userInfo = new JPanel();
        jp_userInfo.setOpaque(false);//将面板背景设计为透明，因为要显示自定义的背景图片
        jp_userInfo.setLayout(new GridLayout(7, 2));
        jp_userInfo.add(new JLabel());
        jp_userInfo.add(new JLabel());
        jp_userInfo.add(lbl_tel);
        jp_userInfo.add(txt_tel);
        jp_userInfo.add(new JLabel());
        jp_userInfo.add(new JLabel());
        jp_userInfo.add(lbl_password);
        jp_userInfo.add(txt_password);
        jp_userInfo.add(new JLabel());
        jp_userInfo.add(new JLabel());
        jp_userInfo.add(lbl_role);
        jp_userInfo.add(com_role);
        jp_userInfo.add(new JLabel());
        jp_userInfo.add(new JLabel());

        // 登录界面功能按钮模块
        //1.登录按钮
        btn_login = new JButton("登录");
        btn_login.setFont(new Font("楷体", Font.PLAIN, 20));
        btn_login.addActionListener(this);
        //2.注册按钮
        btn_register = new JButton("注册");
        btn_register.setFont(new Font("楷体", Font.PLAIN, 20));
        btn_register.addActionListener(this);
        //3.取消按钮
        btn_cancel = new JButton("取消");
        btn_cancel.setFont(new Font("楷体", Font.PLAIN, 20));
        btn_cancel.addActionListener(this);
        //4.功能按钮面板
        JPanel jp_functionBtn = new JPanel();
        jp_functionBtn.setOpaque(false);
        jp_functionBtn.add(btn_login);
        jp_functionBtn.add(btn_register);
        jp_functionBtn.add(btn_cancel);

        //设置主面板布局，并添加上面自定义的面板
        this.setLayout(new BorderLayout());
        this.add(jp_show, BorderLayout.NORTH);
        this.add(jp_userInfo, BorderLayout.CENTER);
        this.add(jp_functionBtn, BorderLayout.SOUTH);
        this.validate();
        this.setVisible(true);
        this.setSize(background.getIconWidth(), background.getIconHeight());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        //登录
        if (e.getSource() == btn_login) {
            String userTel = txt_tel.getText().trim();
            String password = new String(txt_password.getPassword());
            int type = com_role.getSelectedIndex();
            login(userTel, password, type);
            return;
        }

        //注册判断
        if (e.getSource() == btn_register) {
            RegisterWindow RegisterWindow = new RegisterWindow("注册为乘客");
            return;
        }

        //取消登陆
        if (e.getSource() == btn_cancel) {
            txt_tel.setText("");
            txt_password.setText("");
            com_role.setSelectedIndex(0);
        }
    }

    public static Users getUser() {
        return user;
    }

    //用户点击登录按钮时调用此方法
    private void login(String userTel, String password, int type) {
        Icon success = new ImageIcon("picture/success.jpg");
        Icon failed = new ImageIcon("picture/failed.jpg");
        final int PASSENGER = 0;
        final int ADMIN = 1;
        //用户信息均不能为空
        if (userTel.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "输入不能为空!", "提醒", JOptionPane.WARNING_MESSAGE, failed);
            return;
        }
        //如果用户信息不为空，验证此用户是否存在
        UsersDao usersDao = new UsersDao();
        boolean existed = usersDao.userValidate(userTel, password, type);
        if (!existed) {
            JOptionPane.showMessageDialog(this, "输入信息有误", "提醒", JOptionPane.WARNING_MESSAGE, failed);
            return;
        }
        //如果用户存在，判断是哪种用户类型，并进入用户界面
        if (type == PASSENGER) {
            user = usersDao.userQueryByTel(userTel);
            JOptionPane.showMessageDialog(this, "欢迎您！  " + userTel + " 用户！", "登陆成功", JOptionPane.PLAIN_MESSAGE, success);
            new PassengerWindow("用户");
            this.dispose();
        } else if (type == ADMIN) {
            JOptionPane.showMessageDialog(this, "欢迎您！  " + userTel + " 管理员！", "登陆成功", JOptionPane.PLAIN_MESSAGE, success);
            new AdminWindow("管理员");
            this.dispose();
        }
    }
}


