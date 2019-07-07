
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
public class login extends WindowAdapter implements ActionListener{
    static String id;
    JFrame f;
    JLabel la;
    JLabel lp;
    JLabel l1;//验证码
    JLabel p;
    JTextField ta;
    JPasswordField tp;
    JTextField t1;//验证码输入框
    JButton b1;
    JButton b2;
    //JButton b3;
    JButton b4;
    JButton b11;
    JLabel p1;
    ValidCode vcode;
    //management mmm;
    //yonghuduan yyy;
    //jieshu jjj;
    String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String userName="sa";
    String userPwd="ljh123.L";
    String sql;String sql2;
    Font font=new Font("黑体",Font.PLAIN,20);
    public static JPanel GImage=null;
    String [] itemlist= {"          管理员","          读者"};
    @SuppressWarnings("unchecked")
    JComboBox jcb=new JComboBox(itemlist);


    public static void main(String[] args)
    {
        new login();


    }

    public login()
    {
        Font font=new Font("黑体",Font.PLAIN,20);
        f=new JFrame("登陆");
        f.setSize(1500,1200);
        /* 设置背景图片*/
        GImage =new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("Manager_back.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                f.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
        };

        p=new JLabel();//主界面

        la=new JLabel("账号");
        la.setBounds(500,220,60,50);
        la.setFont(font);
        lp=new JLabel("密码");
        lp.setBounds(500,270,60,50);
        lp.setFont(font);
        l1=new JLabel("验证码");
        l1.setBounds(490, 340, 60, 50);
        l1.setFont(font);
        b1=new JButton("登陆");
        b1.setBounds(565,450,100,50);
        b1.setFont(font);
        b2=new JButton("取消");
        b2.setBounds(730,450,100,50);
        b2.setFont(font);
        ta=new JTextField();

        ta.setBounds(565,230,250,50);
        ta.setFont(font);
        tp=new JPasswordField();
        tp.setBounds(565,270,250,50);
        tp.setFont(font);
        t1=new JTextField();
        t1.setBounds(565,340,120,50);
        t1.setFont(font);
        //b3=new JButton("忘记密码");
        //b3.setBounds(820,285,70,30);
        //b3.setFont(new Font("黑体",Font.PLAIN,8));
        //b4=new JButton("注册");
        //b4.setBounds(820,235,70,30);
        //b4.setFont(new Font("黑体",Font.PLAIN,10));
        jcb.setBounds(565, 190, 250, 30);jcb.setFont(new Font("黑体",Font.PLAIN,18));
        //b_student.setBounds(100, 20, 100, 30);b_student.setFont(font);
        //b_teacher.setBounds(250, 20, 150, 30);b_teacher.setFont(font);
        vcode = new ValidCode();
        vcode.setBounds(730, 340, 120, 50);
        p.add(vcode);p.add(jcb);jcb.setSelectedIndex(1);
        f.add(p);//p.add(b_teacher);p.add(b_student);p.add(b_teacher);
        p.add(la);p.add(b1);p.add(b2);p.add(lp);p.add(ta);p.add(tp);//p.add(b3);
        //p.add(b4);
        p.add(l1);p.add(t1);
        f.setVisible(true);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocation(400,300);
        f.add(GImage);
        b1.addActionListener(this);
        b2.addActionListener(this);
        //b3.addActionListener(this);
        //b4.addActionListener(this);
    }
    //addActionListener(this)中的this指的是实现了ActionLinsten接口的类的实例(对象)，在这里便是new login()
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        id=ta.getText();
        if(e.getActionCommand().equals("登陆"))
        {
            if (!isValidCodeRight()) {
                JOptionPane.showMessageDialog(null, "验证码输入错误","错误",JOptionPane.ERROR_MESSAGE);
            }
            else if (ta.getText()!="" && tp.getText()!="") {
                int flag=1;
                try
                {
                    Class.forName(driverName);
                    Connection conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=class",userName,userPwd);
                    System.out.println("连接数据库成功");
                    //String t1 = ta.getText();
                    // String t2 = tp.getText();
                    Statement stmt=conn.createStatement();

                    String role;
                    if(jcb.getSelectedItem().equals("          读者"))
                        role = "读者";
                    else
                        role = "管理员";

                    sql="select * from PI_Sheet";
                    ResultSet rs=stmt.executeQuery(sql);
                    //逐个筛选与数据库中的对比，用户名和密码都正确的话才可以进入数据库
                    while(rs.next())
                    {

                        if(rs.getString(1).equals(ta.getText()) && rs.getString(3).equals(tp.getText())&&rs.getString(5).equals(role))
                        {
                            flag=1;
                            f.setVisible(false);
                            if(jcb.getSelectedItem().equals("          管理员"))
                            {new Manager1(f);}
                            else {
                                new GuiSwing(id,f);
                            }
                            //mmm.f_book.setVisible(true);
                            break;
                        }
                        else
                        {
                            flag=0;
                        }
                    }
                    //错误弹框
                    if(flag==0)
                    {
                        JOptionPane d=new JOptionPane();
                        JOptionPane.showMessageDialog(null, "用户名或密码输入错误","错误",JOptionPane.ERROR_MESSAGE);
                        d.setFont(font);
                        p.add(d);
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                }
                catch(Exception e1)
                {
                    e1.printStackTrace();
                    System.out.print("连接失败");
                }
            }
            else
            {
                JOptionPane d=new JOptionPane();
                JOptionPane.showMessageDialog(null, "请输入账号和密码","错误",JOptionPane.ERROR_MESSAGE);
                d.setFont(font);
                p.add(d);
            }

        }
        if(e.getActionCommand().equals("取消"))
        {
            closeWindow();
        }
        if(e.getActionCommand().equals("返回"))
        {
            p.setVisible(true);p1.setVisible(false);
            f.setVisible(true);
        }



        if(e.getSource().equals(b4))
        {
            //构建注册的主界面并实现功能
            p.setVisible(false);
            p1=new JLabel();//注册主界面
            la=new JLabel("账号");
            la.setBounds(20,50,60,50);
            la.setFont(font);
            lp=new JLabel("密码");
            lp.setBounds(20,120,60,50);
            lp.setFont(font);
            b11=new JButton("注册");
            b11.setBounds(90,220,100,50);
            b11.setFont(font);
            b2=new JButton("返回");
            b2.setBounds(250,220,100,50);
            b2.setFont(font);
            ta=new JTextField();
            ta.setBounds(85,50,250,50);
            ta.setFont(font);
            tp=new JPasswordField();
            tp.setBounds(85,120,250,50);
            tp.setFont(font);
            p1.add(jcb);
            f.add(p1);
            p1.add(la);p1.add(b11);p1.add(b2);p1.add(lp);p1.add(ta);p1.add(tp);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLocation(400,300);

            b11.addActionListener(this);
            b2.addActionListener(this);
        }

        if(e.getSource().equals(b11))//确认注册
        {
            try
            {
                Class.forName(driverName);
                Connection conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=login",userName,userPwd);
                System.out.println("连接数据库成功");
                Statement stmt=conn.createStatement();
                String t3 = ta.getText();
                String t4 = tp.getText();
                if(jcb.getSelectedItem().equals("          读者"))
                {
                    sql2="insert into Student values ("+"'"+t3+"'"+","+"'"+t4+"'"+")";
                }
                else
                {
                    sql2="insert into Teacher values ("+"'"+t3+"'"+","+"'"+t4+"'"+")";
                }
                int i=stmt.executeUpdate(sql2);
                System.out.println(i+"行受影响");
                JOptionPane d=new JOptionPane();
                JOptionPane.showMessageDialog(null,"注册成功");
                d.setFont(font);
                p.add(d);
                stmt.close();
                conn.close();

            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                System.out.print("连接失败");

            }
        }
        if(e.getActionCommand().equals("忘记密码"))
        {

        }

    }

        private void closeWindow()
        {
            int result = JOptionPane.showConfirmDialog(null, "是否要退出？", "退出确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION)
                f.dispose();
        }

        public boolean isValidCodeRight() {

            if (t1 == null) {
                return false;
            }
            if (vcode == null) {
                return true;
            }
            if (vcode.getCode().equals(t1.getText())) {
                return true;
            }
            return false;
        }

}
