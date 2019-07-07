import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.*;

public class GuiSwing implements ActionListener{

    JFrame f=new JFrame("图书管理系统");
    JFrame ff;;
    JMenuBar mb = new JMenuBar();
    String[] names1={"类别","书名","作者","出版社","出版日期","简介"};
    String[] names2={"编号","账户名","密码","姓名","性别","专业","联系方式"};
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    JComboBox choose_category=new JComboBox();
    TextField edit1=new TextField(10);
    TextField edit2=new TextField(10);
    TextField edit3=new TextField(10);
    TextField edit4=new TextField(10);
    TextField edit5=new TextField(10);

    Label label0=new Label("类别：");
    Label label1=new Label("书名：");
    Label label2=new Label("作者：");
    Label label3=new Label("出版社：");
    Label label4=new Label("出版日期：");
    Label label5=new Label("简介：");
    JButton b=new JButton("图书列表");
    JButton c=new JButton("图书查询");
    JButton d=new JButton("借书");
    JButton e=new JButton("还书");
    private String id;
    /*
	public static void main(String args[])
	{
		new GuiSwing();
	}
    */
    private void closeWindow()
        {
            int result = JOptionPane.showConfirmDialog(null, "是否要退出？", "退出确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION)
            {
                f.dispose();
                ff.setVisible(true);
            }
        }

//响应按钮的事件，完成查询功能
	public void actionPerformed(ActionEvent e)
	{
          try{

        	//装载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            //创建连接
            Connection con=DriverManager.getConnection
                ("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");

            Statement stmt=con.createStatement();
            String category=choose_category.getSelectedItem().toString();
            String title=edit1.getText();
            String author=edit2.getText();
            String pub_house=edit3.getText();
            String pub_date=edit4.getText();
            //String introduction=edit5.getText();
            String ex="select category,title,author,pub_house,pub_date,introduction "
            		+ "from BCI_Sheet where category=N'"+category+"' or title=N'"+title+"' or author=N'"
            		+author+"' or pub_house = N'"+pub_house+"' or pub_date = N'"+pub_date+"'";
            System.out.println(ex);
            ResultSet rs=stmt.executeQuery(ex);
            //ResultSet rs=stmt.executeQuery("select category,title,author,pub_house,pub_date,introduction from BCI_Sheet where category="+"'"+category+"'and title="+"'"+title+"'and author="+"'"+author+"'and pub_house="+"'"+pub_house+"'and pub_date="+"'"+pub_date+"'and introduction="+"'"+introduction+"'");
            // 清除原有行
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0);
            //添加查询到的记录集
            while (rs.next())
            {
                 String[] arr=new String[6];
                 arr[0]=rs.getString("category");
                 arr[1]=rs.getString("title");
                 arr[2]=rs.getString("author");
                 arr[3]=rs.getString("pub_house");
                 arr[4]=rs.getString("pub_date");
                 arr[5]=rs.getString("introduction");
                 tableModel.addRow(arr);
            }
            //刷新表格，即重新绘制
            table.invalidate();

            rs.close();
            stmt.close();
            con.close();

		}
		catch(Exception ex)
	    {
	    	System.out.println(ex.getMessage());
	    }
	}

	public GuiSwing(String id,JFrame ff)
	{
        this.id = id;
        this.ff = ff;
         try{

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con=DriverManager.getConnection

("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");

            Statement stmt=con.createStatement();
		    //构造JTable
            ResultSet rs=stmt.executeQuery("select count(*) from BCI_Sheet");
            rs.next();
            RowNum=rs.getInt(1);

            rs=stmt.executeQuery("select category,title,author,pub_house,pub_date,introduction from BCI_Sheet");
            ResultSetMetaData rsmd=rs.getMetaData();


            ColNum=rsmd.getColumnCount();

             //names=new String[ColNum];
             //for (i=1;i<=ColNum;i++) names[i-1]=rsmd.getColumnName(i);
             //names={"类别","书名","作者","出版社","出版日期","简介"};

            info=new Object[RowNum][];
            i=0;
            while (rs.next())
            {
                info[i]=new Object[ColNum];
                for (j=1;j<=ColNum;j++)
                {
                   info[i][j-1]=rs.getObject(j);
                }
                i++;
            }

            DefaultTableModel model = new DefaultTableModel(info,names1);

            table=new JTable(model);

            table.setPreferredScrollableViewportSize(new Dimension(1150,200));

            b.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		try {
            		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection con=DriverManager.getConnection

        ("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");

                    Statement stmt=con.createStatement();
        		    //构造JTable
                    ResultSet rs=stmt.executeQuery("select count(*) from BCI_Sheet");
                    rs.next();
                    RowNum=rs.getInt(1);

                    rs=stmt.executeQuery("select category,title,author,pub_house,pub_date,introduction from BCI_Sheet");
                    ResultSetMetaData rsmd=rs.getMetaData();

                    ColNum=rsmd.getColumnCount();

                     names1=new String[ColNum];
                     for (i=1;i<=ColNum;i++) names1[i-1]=rsmd.getColumnName(i);

                    info=new Object[RowNum][];
                    i=0;
                    while (rs.next())
                    {
                        info[i]=new Object[ColNum];
                        for (j=1;j<=ColNum;j++)
                        {
                           info[i][j-1]=rs.getObject(j);
                        }
                        i++;
                    }
                    int j=0;
                    DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
                    tableModel.setRowCount(0);

                    while(j!=i)
                    {
                        tableModel.addRow(info[j]);
                        j++;
                    }
                    table.invalidate();
                    rs.close();
                    stmt.close();
                    con.close();
                    //DefaultTableModel model = new DefaultTableModel(info,names);

                    //table=new JTable(model);

                    //table.setPreferredScrollableViewportSize(new Dimension(1600,200));
            		}
            		catch(Exception ee)
            	    {
            	    	System.out.println(ee.getMessage());
            	    }
            	}
            });

            c.addActionListener(this);

            Panel panel=new Panel();
            Panel panel1=new Panel();

            panel.add(b);
            panel.add(label0);  panel.add(choose_category);
            panel.add(label1);  panel.add(edit1);
            panel.add(label2);  panel.add(edit2);
            panel.add(label3);  panel.add(edit3);
            panel.add(label4);  panel.add(edit4);
            panel.add(label5);  panel.add(edit5);
            panel.add(c);
            panel1.add(d);
            panel1.add(e);

            choose_category.addItem("请选择");
            choose_category.addItem("哲学");
            choose_category.addItem("政治、法律");
            choose_category.addItem("经济");
            choose_category.addItem("文学");
            choose_category.addItem("历史");
            choose_category.addItem("数理科学");
            choose_category.addItem("社会科学");

            f.add(panel,BorderLayout.NORTH);
            f.add(panel1,BorderLayout.SOUTH);

            f.getContentPane().add(new JScrollPane(table));
            //响应窗口的关闭事件
            f.addWindowListener(new WindowAdapter()
            {
            		public void windowClosiing(WindowEvent ew)
            	    {System.exit(0);}
            		});

            f.setJMenuBar(mb);
            JMenu m1 = new JMenu("个人中心");

            mb.add(m1);
        /*
            mb.add(m11);
            mb.add(m12);
            mb.add(m13);
            */
            //mb.add(m2);
            //mb.setHelpMenu(m3);

            //public void setHelpMenu(m3) {
            //    throw new Error("setHelpMenu() not yet implemented.");
            //}

            /*
            JMenu m11 = new JMenu("个人信息");
            JMenu m12 = new JMenu("借阅信息");
            JMenu m13 = new JMenu("注销");
            */
            JMenuItem m11 = new JMenuItem("个人信息");
            JMenuItem m12 = new JMenuItem("借阅信息");
            JMenuItem m13 = new JMenuItem("注销");
            /*
            mb.add(m11);
            mb.add(m12);
            mb.add(m13);

            */

            m1.add(m11);
            m1.add(m12);
            m1.addSeparator();
            m1.add(m13);

            m11.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent e) {
            		JFrame f1 = new JFrame("个人信息");
            	    TextField edit01=new TextField(10);
            		TextField edit11=new TextField(10);
            	    TextField edit21=new TextField(10);
            	    TextField edit31=new TextField(10);
            	    //TextField edit41=new TextField(10);
            		JComboBox choose_sex=new JComboBox();
            	    TextField edit51=new TextField(10);
            	    TextField edit61=new TextField(10);

            	    Label label01=new Label("编号：");//不可编辑
            	    Label label11=new Label("账户名：");
            	    Label label21=new Label("密码：");
            	    Label label31=new Label("姓名：");
            	    Label label41=new Label("性别：");
            	    Label label51=new Label("专业：");
            	    Label label61=new Label("联系方式：");
            	    JButton bb=new JButton("修改");
            	    JButton bc=new JButton("退出");

            	    try {
                		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection con=DriverManager.getConnection
            ("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");

                        Statement stmt=con.createStatement();
            		    //构造JTable
                        ResultSet rs=stmt.executeQuery
                        		//("1");//
                        		("select count(*) from PI_Sheet");
                        rs.next();
                        RowNum=rs.getInt(1);
                        System.out.println("select id,ac_name,passwd,name,sex,major,tel from PI_Sheet "
                        		+ "where ac_name=N's65457'");
                        rs=stmt.executeQuery("select id,ac_name,passwd,name,sex,major,tel from PI_Sheet "
                        		+ "where id=N'"+id+"'");
                        ResultSetMetaData rsmd=rs.getMetaData();

                        names2=new String[ColNum];
                        for (i=1;i<=ColNum;i++) names2[i-1]=rsmd.getColumnName(i);
                        //添加查询到的记录集
                        //while (rs.next())
                        //{
                        choose_sex.addItem("请选择");
                        choose_sex.addItem("男");
                        choose_sex.addItem("女");

                        rs.next();
                             //String[] arr=new String[7];
                             edit01.setText(rs.getString("id"));
                             edit11.setText(rs.getString("ac_name"));
                             edit21.setText(rs.getString("passwd"));
                             edit31.setText(rs.getString("name"));
                             //String sex=choose_sex.setSelectedIndex(0);

                             //edit41.setText(rs.getString("sex"));
                             edit51.setText(rs.getString("major"));
                             edit61.setText(rs.getString("tel"));
                        //}
                        //刷新表格，即重新绘制
                        table.invalidate();

                        rs.close();
                        stmt.close();
                        con.close();
                        //f1.setLayout(new GridLayout(8,2,10,10));

                        Panel panel10=new Panel();
                        Panel panel11=new Panel();

                        panel10.add(label01);
                        panel10.add(edit01);
                        edit01.setEditable(false);
                        panel10.add(label11);
                        panel10.add(edit11);
                        panel10.add(label21);
                        panel10.add(edit21);
                        panel10.add(label31);
                        panel10.add(edit31);
                        panel10.add(label41);
                        panel10.add(choose_sex);
                        panel10.add(label51);
                        panel10.add(edit51);
                        panel10.add(label61);
                        panel10.add(edit61);

                        panel11.add(bb);
                        panel11.add(bc);

                        f1.add(panel10,BorderLayout.NORTH);
                        f1.add(panel11,BorderLayout.SOUTH);

                        f1.setSize(1000,200);
                        f1.setLocation(100,100);
                        f1.setVisible(true);
                    }
                    catch(Exception e1)
                    {
                        System.out.println(e1.getMessage());
                    }
                    bb.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                Connection con=DriverManager.getConnection
                                    ("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");

                                Statement stmt=con.createStatement();
                                int result = JOptionPane.showConfirmDialog(f, "你确定要修改"+id+"的信息吗");
                                //int result = JOptionPane.showConfirmDialog(f, "你确定要修改的信息吗");
                                if(result==JOptionPane.OK_OPTION)
                                {
                                    System.out.println(edit01.getText());
                                    System.out.println(edit11.getText());
                                    System.out.println(edit21.getText());
                                    System.out.println(edit31.getText());
                                    System.out.println(choose_sex.getSelectedItem().toString());
                                    System.out.println(edit51.getText());
                                    System.out.println(edit61.getText());
                                    stmt.execute("update PI_Sheet set ac_name=N'"+edit11.getText()+"',passwd=N'"+edit21.getText()+"',name=N'"+edit31.getText()+"',sex=N'"+choose_sex.getSelectedItem().toString()+"',major=N'"+edit51.getText()+"',tel=N'"+edit61.getText()+"' where id=N'"+edit01.getText()+"'");
                                    JOptionPane.showMessageDialog(f1, "修改成功!", "修改完成", JOptionPane.ERROR_MESSAGE);
                                }
                                stmt.close();
                                con.close();

                            }
                            catch(Exception e1)
                            {
                                System.out.println(e1.getMessage());
                                JOptionPane.showMessageDialog(f1, "更改信息失败!请输入合理的修改信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    bc.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                            f1.setVisible(false);
                        }
                    });

                }

            });


            m13.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent e) {
                    closeWindow();
            	}
            });

            m12.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent e) {
                    System.out.println("执行这里了吗?");
                    JFrame fj=new JFrame("借还信息");
                    String[] names;
                    int i,j,RowNum,ColNum;
                    Object[][] info_borrow;
                    JTable table_borrow;
                    try{
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");

                        Statement stmt=con.createStatement();
                        ResultSet rs=stmt.executeQuery("select count(*) from BR_Sheet where id=N'"+id+"'");
                    System.out.println("执行这里了吗?6");

                        rs.next();
                        RowNum=rs.getInt(1);
                        rs=stmt.executeQuery("select * from BR_Sheet where id=N'"+id+"'");
                        ResultSetMetaData rsmd=rs.getMetaData();
                    System.out.println("执行这里了吗?7");
                        ColNum=rsmd.getColumnCount();
                        names=new String[ColNum];     //构造JTable
                        for (i=1;i<=ColNum;i++) names[i-1]=rsmd.getColumnName(i);
                        info_borrow=new Object[RowNum][];
                    System.out.println(RowNum);
                    System.out.println(ColNum);
                        i=0;
                        while (rs.next())
                        {
                    System.out.println("执行这里了吗?object");
                            info_borrow[i]=new Object[ColNum];
                    System.out.println("执行这里了吗?object out");
                            for (j=1;j<=ColNum;j++)
                            {
                                info_borrow[i][j-1]=rs.getObject(j);
                    System.out.println(info_borrow[i][j-1]);
                            }
                            i++;
                        }
                    System.out.println("执行这里了吗?4");
                        DefaultTableModel model = new DefaultTableModel(info_borrow,names);
                        table=new JTable(model);
                        table.setPreferredScrollableViewportSize(new Dimension(400,50));
                    System.out.println("执行这里了吗?5");

                        //b.addActionListener(this);

                        fj.getContentPane().add(new JScrollPane(table));

                        fj.addWindowListener(new WindowAdapter()
                                {
                                    public void windowClosiing(WindowEvent ew)
                                    {System.exit(0);}
                                });

                    System.out.println("执行这里了吗?2");
                        fj.pack();
                        fj.setVisible(true);
                        rs.close();
                        stmt.close();
                        con.close();
                    }
                    catch(Exception eee)
                    {
                        System.out.println(eee.getMessage());
                    }
                }
            });


            d.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //借书信息
                    new borrow(id);
                }
            });
            e.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //还书信息
                    new return_book(id);
                }
            });


            f.pack();
            f.setVisible(true);
            rs.close();
            stmt.close();
            con.close();

         }
         catch(Exception e)
         {
             System.out.println(e.getMessage());
         }
    }
}
