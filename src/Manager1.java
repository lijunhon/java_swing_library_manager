import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.*;
import java.util.Map;
import java.util.HashMap;

public class Manager1
{
    public static JPanel GImage=null;
    private JFrame jf = new JFrame("管理系统");
    private JMenuBar menuBar = new JMenuBar();
    private JMenu select = new JMenu("查询");
    private JMenu manager = new JMenu("管理");
    private JMenu system = new JMenu("系统");

    //表头
    private String[] selectusername;
    private String[] selectbookname;
    private String[] selectborrowname;
    private String[] bookname;
    private String[] personname;
    //容器包含两个面板
    //Container con = jf.getContentPane();
    //下面是JScrollPane和JPanel用来做表格
    private JPanel jp = new JPanel();
    private JTable table;
    private JTable table1;
    private JTable table2;
    private JTable table_pi;
    private JTable table_bci;

    private JScrollPane jsp;
    private JScrollPane jsp1;
    private JScrollPane jsp2;
    private JScrollPane jsp_pi;
    private JScrollPane jsp_bci;
    //hashmap
    private HashMap<String,String> hashmap = new HashMap<String,String>();

    private JMenuItem selectReader = new JMenuItem("读者查询");//现在做这个
    private JMenuItem selectBook = new JMenuItem("图书查询");
    private JMenuItem selectBorrow = new JMenuItem("借阅查询");
    private JMenuItem managerUser = new JMenuItem("用户管理");
    private JMenuItem userupdate = new JMenuItem("用户更新");
    private JMenuItem useradd = new JMenuItem("用户增加");
    private JMenuItem userdel = new JMenuItem("用户删除");
    private JMenuItem bookupdate = new JMenuItem("图书更新");
    private JMenuItem bookadd = new JMenuItem("图书增加");
    private JMenuItem bookaddtype = new JMenuItem("图书类型增加");
    private JMenuItem bookdel = new JMenuItem("图书删除");
    private JMenuItem managerBook = new JMenuItem("图书管理");
    private JMenuItem managerBorrow = new JMenuItem("借阅管理");
    private JMenuItem Return = new JMenuItem("回到主界面");
    private JMenuItem logOut = new JMenuItem("退出");
    //下面是读者查询控件
    private JButton selectreaderButton = new JButton("读者查询");
    private JButton selectbookButton = new JButton("图书查询");
    private JButton selectborrowButton = new JButton("借阅查询");

    //label 控件
    //读者查询
    private JLabel selectreaderLabel1 = new JLabel("用户名");
    private JLabel selectreaderLabel2 = new JLabel("学生号");
    //图书查询
    private JLabel selectbooktitleLabel = new JLabel("书名");
    private JLabel selectbookidLabel = new JLabel("图书编号");
    private JLabel selectbookauthorLabel = new JLabel("作者");
    private JLabel selectbookpubLabel = new JLabel("出版社");
    //借阅查询
    private JLabel selectborrowidLabel = new JLabel("学生号");
    private JLabel selectborrowacnameLabel = new JLabel("用户名");


    //jtextfield 控件
    //读者查询
    private JTextField selectContent1 = new JTextField(80);
    private JTextField selectContent2 = new JTextField(80);
    //图书查询
    private JTextField selectbooktitleContent = new JTextField();
    private JTextField selectbookidContent = new JTextField();
    private JTextField selectbookauthorContent = new JTextField();
    private JTextField selectbookpubContent = new JTextField();
    //借阅查询
    private JTextField selectborrowidContent = new JTextField();
    private JTextField selectborrowacnameContent = new JTextField();

    private CardLayout card;
    private CardLayout card1;
    private CardLayout card2;

    //个人信息增加，修改表,删除
    private JButton piaddButton = new JButton("增加");
    private JButton pidelButton = new JButton("删除");
    private JButton piupgButton = new JButton("修改");

    private JTextField piid = new JTextField();
    private JTextField piacname = new JTextField();
    private JTextField pipasswd = new JTextField();
    private JTextField piname = new JTextField();
    private JTextField pirole = new JTextField();
    private JTextField pibdnumber = new JTextField();
    private JTextField pisex = new JTextField();
    private JTextField pimajor = new JTextField();
    private JTextField pitel = new JTextField();
    private JTextField pivinumber = new JTextField();
    private JTextField pipremarks = new JTextField();

    private JLabel piidlabel = new JLabel("编号");
    private JLabel piacnamelabel = new JLabel("账户名");
    private JLabel pipasswdlabel = new JLabel("密码");
    private JLabel pinamelabel = new JLabel("姓名");
    private JLabel pirolelabel = new JLabel("身份");
    private JLabel pibdnumberlabel = new JLabel("已借阅数");
    private JLabel pisexlabel = new JLabel("性别");
    private JLabel pimajorlabel = new JLabel("专业");
    private JLabel pitellabel = new JLabel("联系方式");
    private JLabel pivinumberlabel = new JLabel("违章次数");
    private JLabel pipremarkslabel = new JLabel("备注");

    //图书信息增加，修改，删除
    private JButton bciaddButton = new JButton("增加");
    private JButton bcidelButton = new JButton("删除");
    private JButton bciupgButton = new JButton("修改");

    private JTextField bcititle = new JTextField();
    private JTextField bciauthor = new JTextField();
    private JTextField bcipubhouse = new JTextField();
    private JTextField bcipubdate = new JTextField();
    private JTextField bcicategory = new JTextField();
    private JTextField bcitotal = new JTextField();
    private JTextField bcistock = new JTextField();
    private JTextField bciintroduction = new JTextField();
    private JTextField bcibremarks = new JTextField();

    private JLabel bcititlelabel = new JLabel("书名");
    private JLabel bciauthorlabel = new JLabel("作者");
    private JLabel bcipubhouselabel = new JLabel("出版社");
    private JLabel bcipubdatelabel = new JLabel("出版日期");
    private JLabel bcicategorylabel = new JLabel("类别");
    private JLabel bcitotallabel = new JLabel("总数");
    private JLabel bcistocklabel = new JLabel("库存");
    private JLabel bciintroductionlabel = new JLabel("简介");
    private JLabel bcibremarkslabel = new JLabel("备注");

    //下面是构造表
    private Object[][] selectUserInfo;
    private Object[][] selectBookInfo;
    private Object[][] selectBorrowInfo;

    //private String[] selectUserHead = {"学号","姓名","账户","性别","专业","电话"};
    //private String[] selectBooHead = {"书籍编号","学号","姓名","账户","性别","专业","电话"};
    //private String[] selectBorrowHead = {"书籍编号","学号","姓名","账户","性别","专业","电话"};
    //private JTextArea jta = new JLabel();
    //
    //
    //
    //
    //下面是

    /*
	public static void main(String args[])
	{
		new Manager1();
	}
    */
    /*
	public void actionPerformed(ActionEvent e)
    {
        System.out.println("hahahah");
    }
    */
	public Manager1(JFrame f)
    {

        GImage =new JPanel() {
                         protected void paintComponent(Graphics g) {
                        ImageIcon icon = new ImageIcon("Manager_back.jpg");
                        Image img = icon.getImage();
                        g.drawImage(img, 0, 0, icon.getIconWidth(),
                                icon.getIconHeight(), icon.getImageObserver());
                        jf.setSize(icon.getIconWidth(), icon.getIconHeight());
                }
                };
        /*
        JPanel jpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("Manager_back.jpg");
                img.paintIcon(jf, g, 0, 0);
            }
        };
        */

        //添加值
        ///*
        hashmap.put("id","编号");
        hashmap.put("ac_name","账户名");
        hashmap.put("passwd","密码");
        hashmap.put("name","姓名");
        hashmap.put("role","身份");
        hashmap.put("bd_number","已借阅数");
        hashmap.put("sex","性别");
        hashmap.put("major","专业");
        hashmap.put("tel","联系方式");
        hashmap.put("vi_number","违章次数");
        hashmap.put("p_remarks","备注");
        hashmap.put("title","书名");
        hashmap.put("author","作者");
        hashmap.put("pub_house","出版社");
        hashmap.put("pub_date","出版日期");
        hashmap.put("category","类别");
        hashmap.put("total","总数");
        hashmap.put("stock","库存");
        hashmap.put("introduction","简介");
        hashmap.put("b_remarks","备注");
        hashmap.put("book_id","图书编号");
        hashmap.put("b_date","借阅日期");
        hashmap.put("r_date","还书日期");
        hashmap.put("overdue","是否逾期");
        //*/

        //jf.setLayout(new GridLayout(2,1,10,10));


        //设置JFrame为全屏
        //jf.setUndecorated(true);
        //jf.getGraphicsConfiguration().getDevice().setFullScreenWindow(jf);
        //jb.setBounds(100,100,100,100);

        //jf.setLocationRelativeTo(null);

        //jpanel.setOpaque(true);
        jf.setSize(1000, 700);
        jf.setResizable(false);
        card = new CardLayout();
        card1 = new CardLayout();
        card2 = new CardLayout();
        JPanel jp_jsp = new JPanel();
        jp.setLayout(card);
        jp_jsp.setLayout(card2);

        /*
        jp_jsp.add("selectUser",jsp);
        jp_jsp.add("selectBook",jsp1);
        jp_jsp.add("selectBorrow",jsp2);
        jp_jsp.add("person",jsp_pi);
        jp_jsp.add("book",jsp_bci);
        */


        jf.setJMenuBar(menuBar);
        menuBar.add(select);
        menuBar.add(manager);
        menuBar.add(system);

        select.add(selectReader);
        select.add(selectBook);
        select.add(selectBorrow);

        //manager.add(managerUser);
        //manager.add(managerBook);
        //manager.add(managerBorrow);
        manager.add(userupdate);
        manager.add(useradd);
        manager.add(userdel);
        manager.add(bookadd);
        manager.add(bookaddtype);
        manager.add(bookdel);
        manager.add(bookupdate);

        //最开始显示的界面
        JPanel jp_start = new JPanel();
        JPanel jp_all = new JPanel();
        //TODO
        JPanel jp_next = new JPanel();
        JPanel jp_update_user = new JPanel();
        jp_next.setLayout(new BorderLayout());
        //jp_start.add(GImage);
        jp_all.setLayout(card1);
        //jp_all.add("start",jp_start);
        //jp_all.add("start",GImage);
        jp_all.add("jp_update_user",jp_update_user);
        jf.add(GImage);
        //jp_next.setLayout(new GridLayout(2,1,10,10));



        //个人信息，增加删除，修改配置
        JPanel person = new JPanel();
        JPanel person_ch = new JPanel();
        person_ch.setLayout(new GridLayout(6,4,10,10));
        person_ch.add(piidlabel);
        person_ch.add(piid);
        person_ch.add(piacnamelabel);
        person_ch.add(piacname);
        person_ch.add(pipasswdlabel);
        person_ch.add(pipasswd);
        person_ch.add(pinamelabel);
        person_ch.add(piname);
        person_ch.add(pirolelabel);
        person_ch.add(pirole);
        person_ch.add(pibdnumberlabel);
        person_ch.add(pibdnumber);
        person_ch.add(pisexlabel);
        person_ch.add(pisex);
        person_ch.add(pimajorlabel);
        person_ch.add(pimajor);
        person_ch.add(pitellabel);
        person_ch.add(pitel);
        person_ch.add(pivinumberlabel);
        person_ch.add(pivinumber);
        person_ch.add(pipremarkslabel);
        person_ch.add(pipremarks);
        person.add(piaddButton,BorderLayout.SOUTH);
        person.add(pidelButton,BorderLayout.SOUTH);
        person.add(piupgButton,BorderLayout.SOUTH);
        person.add(person_ch,BorderLayout.NORTH);
        //jp.add("person",person);
        //个人信息增加，显示全部


        selectUser su_change = new selectUser();
        String selectusername_change[] = new String[su_change.colnum];
        int a;
        for( a= 0;a < su_change.colnum;a++)
            selectusername_change[a] = hashmap.get(su_change.names[a]);
        DefaultTableModel model_user_change = new DefaultTableModel(su_change.result_all,selectusername_change);
        table_pi = new JTable(model_user_change);
        jsp_pi = new JScrollPane(table_pi);


        //书籍增加，删除，修改配置
        JPanel book = new JPanel();
        JPanel book_ch = new JPanel();
        book_ch.setLayout(new GridLayout(5,4,10,10));
        book_ch.add(bcititlelabel);
        book_ch.add(bcititle);
        book_ch.add(bciauthorlabel);
        book_ch.add(bciauthor);
        book_ch.add(bcipubhouselabel);
        book_ch.add(bcipubhouse);
        book_ch.add(bcipubdatelabel);
        book_ch.add(bcipubdate);
        book_ch.add(bcicategorylabel);
        book_ch.add(bcicategory);
        book_ch.add(bcitotallabel);
        book_ch.add(bcitotal);
        book_ch.add(bcistocklabel);
        book_ch.add(bcistock);
        book_ch.add(bciintroductionlabel);
        book_ch.add(bciintroduction);
        book_ch.add(bcibremarkslabel);
        book_ch.add(bcibremarks);
        book.add(bciaddButton,BorderLayout.SOUTH);
        book.add(bcidelButton,BorderLayout.SOUTH);
        book.add(bciupgButton,BorderLayout.SOUTH);
        book.add(book_ch,BorderLayout.NORTH);
        //jp.add("book",book);

        //书籍增加，显示全部
        selectBook sb_change = new selectBook();
        String selectbookname_change[] = new String[sb_change.colnum];
        for(a = 0;a < sb_change.colnum;a++)
            selectbookname_change[a] = hashmap.get(sb_change.names[a]);

        DefaultTableModel model_bci = new DefaultTableModel(sb_change.result_all,selectbookname_change);
        table_bci = new JTable(model_bci);
        jsp_bci = new JScrollPane(table_bci);


        //读者查询
        //未布局
        JPanel jp2 = new JPanel();
        JPanel readText = new JPanel();
        //readText.setLayout(new BorderLayout());
        readText.setLayout(new GridLayout(2,2));
        jp2.setLayout(new BorderLayout());
        //readText.add(selectreaderLabel1,BorderLayout.EAST);
        //jp2.setLayout(new GridLayout(1,2));

        readText.add(selectreaderLabel1);
        //readText.add(selectContent1,BorderLayout.WEST);
        readText.add(selectContent1);
        //readText.add(selectreaderLabel2,BorderLayout.EAST);
        readText.add(selectreaderLabel2);
        //readText.add(selectContent2,BorderLayout.WEST);
        readText.add(selectContent2);
        jp2.add(selectreaderButton,BorderLayout.SOUTH);
        jp2.add(readText,BorderLayout.NORTH);
        jp.add("selectreader",jp2);

        //这里显示读者查询全部
        //
        selectUser su = new selectUser();
        selectusername = new String[su.colnum];
        int n;
        for(n = 0;n < su.colnum;n++)
            selectusername[n] = hashmap.get(su.names[n]);
        DefaultTableModel model = new DefaultTableModel(su.result_all,selectusername);
        table = new JTable(model);
        jsp = new JScrollPane(table);

        //书籍查询
        //未布局
        JPanel jp3 = new JPanel();
        JPanel bookText = new JPanel();
        bookText.setLayout(new GridLayout(3,2));
        jp3.setLayout(new BorderLayout());

        bookText.add(selectbooktitleLabel);
        bookText.add(selectbooktitleContent);
        //bookText.add(selectbookidLabel);
        //bookText.add(selectbookidContent);
        bookText.add(selectbookauthorLabel);
        bookText.add(selectbookauthorContent);
        bookText.add(selectbookpubLabel);
        bookText.add(selectbookpubContent);
        jp3.add(bookText,BorderLayout.NORTH);
        jp3.add(selectbookButton,BorderLayout.SOUTH);
        jp.add("selectbook",jp3);
        //这里显示书籍查询全部
        //
        selectBook sb = new selectBook();
        selectbookname = new String[sb.colnum];
        for(n = 0;n < sb.colnum;n++)
            selectbookname[n] = hashmap.get(sb.names[n]);

        DefaultTableModel model1 = new DefaultTableModel(sb.result_all,selectbookname);
        table1 = new JTable(model1);
        jsp1 = new JScrollPane(table1);

        //借阅查询，未布局
        //
        JPanel jp4 = new JPanel();
        JPanel borrowText = new JPanel();
        borrowText.setLayout(new GridLayout(2,2));
        jp4.setLayout(new BorderLayout());

        borrowText.add(selectborrowidLabel);
        borrowText.add(selectborrowidContent);
        borrowText.add(selectborrowacnameLabel);
        borrowText.add(selectborrowacnameContent);
        jp4.add(borrowText,BorderLayout.NORTH);
        jp4.add(selectborrowButton,BorderLayout.SOUTH);
        jp.add("selectborrow",jp4);
        //这里显示全部
        selectBorrow sbr = new selectBorrow();
        selectborrowname = new String[sbr.colnum];
        for(n = 0;n < sbr.colnum;n++)
            selectborrowname[n] = hashmap.get(sbr.names[n]);

        DefaultTableModel model2 = new DefaultTableModel(sbr.result_all,selectborrowname);
        table2 = new JTable(model2);
        jsp2 = new JScrollPane(table2);
        //========================全部显示完成================

        //====================================功能分割线=============================

        //system.add(Return);
        system.add(logOut);
        //
        //table.setPreferredScrollableViewportSize(new Dimension(950,250));
        //下面是添加表格
        jp_jsp.add("selectUser",jsp);
        jp_jsp.add("selectBook",jsp1);
        jp_jsp.add("selectBorrow",jsp2);
        //TODO
        //table_pi = new JTable();
        //jsp_pi = new JScrollPane(table_pi);
        //table_bci = new JTable();
        //jsp_bci = new JScrollPane(table_bci);
        jp_jsp.add("person",jsp_pi);
        jp_jsp.add("book",jsp_bci);

        jp_next.add(jp,BorderLayout.NORTH);
        //TODO 是jp_jsp吗
        jp_next.add(jp_jsp,BorderLayout.SOUTH);
        jp_all.add("next",jp_next);
        //jf.add(GImage);
        jf.add(jp_all);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        //con.setLayout(new GridLayout(2,1,10,10));
        //con.add(jpanel);
        jf.setVisible(true);
        //===============================事件分割线================================
        //下面是事件响应
        //读者查询事件
        selectReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card2.show(jp_jsp,"selectUser");

                card1.show(jp_all,"next");
                card.show(jp,"selectreader");

                //查询事件按钮
                selectreaderButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ee) {
                        String s_acname = selectContent1.getText();
                        String s_id = selectContent2.getText();

                        selectUser su1 = new selectUser(s_acname,s_id);
                        DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
                        tableModel.setRowCount(0);
                        int j=0;
                        int i=0;
                        while(j!=su1.row)
                        {
                            String[] arr=new String[su1.colnum];
                            for(i=1;i<su1.colnum;i++)
                            {
                                arr[i-1] = String.valueOf(su1.result_select[j][i]);
                            }
                            j++;
                            tableModel.addRow(arr);
                        }
                        table.invalidate();

                    }
                });
            }
        });

        //图书查询
        selectBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card1.show(jp_all,"next");
                card2.show(jp_jsp,"selectBook");
                card.show(jp,"selectbook");
                //图书查询按钮事件
                selectbookButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ee) {
                        selectBook sb1 = new selectBook
                            (selectbooktitleContent.getText(),selectbookidContent.getText(),selectbookauthorContent.getText(),selectbookpubContent.getText());

                        DefaultTableModel tableModel= (DefaultTableModel)table1.getModel();
                        tableModel.setRowCount(0);
                        int j=0;
                        int i=0;
                        while(j!=sb1.row)
                        {
                            String[] arr=new String[sb1.colnum];
                            for(i=1;i<sb1.colnum;i++)
                            {
                                arr[i-1] = String.valueOf(sb1.result_select[j][i]);
                            }
                            j++;
                            tableModel.addRow(arr);
                        }
                        table1.invalidate();

                    }
                });

            }
        });

        //借阅查询
        selectBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card1.show(jp_all,"next");
                card2.show(jp_jsp,"selectBorrow");
                card.show(jp,"selectborrow");
                //借阅查询按钮事件
                selectborrowButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ee) {
                        selectBorrow sbr1 = new selectBorrow
                            (selectborrowacnameContent.getText(),selectborrowidContent.getText());

                        DefaultTableModel tableModel= (DefaultTableModel)table2.getModel();
                        tableModel.setRowCount(0);
                        int j=0;
                        int i=0;
                        while(j!=sbr1.row)
                        {
                            String[] arr=new String[sbr1.colnum];
                            for(i=1;i<sbr1.colnum;i++)
                            {
                                arr[i-1] = String.valueOf(sbr1.result_select[j][i]);
                            }
                            j++;
                            tableModel.addRow(arr);
                        }
                        table2.invalidate();

                    }
                });

            }
        });
        //个人信息表，增加删除，修改事件
        /*
        managerUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card1.show(jp_all,"next");
                card2.show(jp_jsp,"person");
                card.show(jp,"person");
                //TODO
                //增加事件
                piaddButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        add_user adduser = new add_user(
                                jf,
                                piid.getText(),
                                piacname.getText(),
                                pipasswd.getText(),
                                piname.getText(),
                                pirole.getText(),
                                pisex.getText(),
                                pimajor.getText(),
                                pitel.getText()
                                );


                        selectUser su11 = new selectUser();
                        DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
                        tableModel.setRowCount(0);
                        int j=0;
                        int i=0;
                        while(j!=su11.rownum)
                        {
                            String[] arr=new String[su11.colnum];
                            for(i=1;i<su11.colnum;i++)
                            {
                                arr[i-1] = String.valueOf(su11.result_all[j][i]);
                            }
                            j++;
                            tableModel.addRow(arr);
                        }
                        System.out.println("执行到显示了吗?");
                        table_pi.invalidate();


                        show_all_student();
                    }
                });

                //删除事件

                pidelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ee) {

                        //delete_user deleteuser = new delete_user();

                        //selectUser su1 = new selectUser(s_acname,s_id);
                        DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
                        tableModel.setRowCount(0);
                        int j=0;
                        int i=0;
                        while(j!=su1.row)
                        {
                            String[] arr=new String[su1.colnum];
                            for(i=1;i<su1.colnum;i++)
                            {
                                arr[i-1] = String.valueOf(su1.result_select[j][i]);
                            }
                            j++;
                            tableModel.addRow(arr);
                        }
                        table.invalidate();

                    }
                });
            }
        });
    */



        //用户更新
        userupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update_user updateuser = new update_user();
                //card1.show(jp_all,"start");
            }
        });

        //书籍更新

        bookupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update_book updatebook = new update_book();
                //card1.show(jp_all,"start");
            }
        });
        //用户删除
        userdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new delete_user();
                //card1.show(jp_all,"start");
            }
        });

        //书籍删除
        bookdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new delete_book();
                //card1.show(jp_all,"start");
            }
        });

        //增加Store
        bookadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_store addstore = new add_store();
                //card1.show(jp_all,"start");
            }
        });

        //增加book type
        bookaddtype.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_booktype addbooktype = new add_booktype();
                //card1.show(jp_all,"start");
            }
        });
        //增加user
        useradd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_user adduser = new add_user();
                //card1.show(jp_all,"start");
            }
        });


        //书籍信息表，增加，删除，修改事件
        managerBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("执行到书籍显示");
                card1.show(jp_all,"next");
                card2.show(jp_jsp,"book");
                card.show(jp,"book");
                //TODO
                //下面要修改
                /*
                String s_borrowid = selectborrowidContent.getText();
                String s_borrowacname = selectborrowacnameContent.getText();
                */
            }
        });


        //退出事件
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow(f);
            }
        });
        //回到主界面事件
        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //card1.show(jp_all,"start");
            }
        });



    }
    private void closeWindow(JFrame f)
    {
        int result = JOptionPane.showConfirmDialog(null, "是否要退出？", "退出确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION)
        {
			jf.dispose();
            f.setVisible(true);
        }
    }
    private void show_all_student()
    {
        /*
        try{
            Thread thread = Thread.currentThread();
            thread.sleep(1500);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        selectUser su11 = new selectUser();
        DefaultTableModel tableModel1= (DefaultTableModel)table.getModel();
        tableModel1.setRowCount(0);
        int j=0;
        int i=0;
        while(j!=su11.rownum)
        {
            String[] arr=new String[su11.colnum];
            for(i=0;i<su11.colnum;i++)
            {
                arr[i] = String.valueOf(su11.result_all[j][i]);
            }
            j++;
            tableModel1.addRow(arr);
        }
        table_pi.invalidate();
    }

    /*
    private void processRs()
    {
        aaa
    }
    */
}
