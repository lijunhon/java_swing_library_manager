
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;
//import java.awt.event.*;
import javax.swing.table.*;

public class add_user implements ActionListener{
	JFrame f=new JFrame("用户管理-增加用户");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    JComboBox choose_role=new JComboBox();
    TextField edit00=new TextField(10);
    TextField edit0=new TextField(10);
    JPasswordField edit1 = new JPasswordField(12);
    TextField edit2=new TextField(10);
    //TextField edit3=new TextField(10);
    TextField edit4=new TextField(10);
    TextField edit5=new TextField(10);
    TextField edit6=new TextField(10);
    Label labe00=new Label("ID：");
    Label label0=new Label("账户名：");//ac_name
    Label label1=new Label("密码：");//password 需要用*来显示
    Label label2=new Label("姓名：");//name
    Label label3=new Label("身份：");//role 下拉菜单选择
    Label label4=new Label("性别：");//sex
    Label label5=new Label("专业：");//major
    Label label6=new Label("联系方式：");//tel
    JButton b=new JButton("增加");//id自动分配
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new add_user();
	}
	public void actionPerformed(ActionEvent e)
	{
		try{
        	//增加记录
			//默认添加到本地keshe库的PI_Sheet表中
	      	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
			String id=edit00.getText();
			String ac_name=edit0.getText();
			char[] password=edit1.getPassword();
			String n = String.valueOf(password);
			String name=edit2.getText();
			String role=choose_role.getSelectedItem().toString();
			String sex=edit4.getText();
			String major=edit5.getText();
			String tel=edit6.getText();

            int result = JOptionPane.showConfirmDialog(f, "你确定要增加姓名为："+name+"的"+role+"吗");
            //System.out.println("insert into PI_Sheet values(N'"+ac_name+"',N'"+n+"',N'"+name+"',N'"+role+"',0,N'"+sex+"',N'"+major+"',N'"+tel+"',0,N'')");
            if(result==JOptionPane.OK_OPTION)
            {

                 String sql="insert into PI_Sheet values('"+id+"',N'"+ac_name+"',N'"+n+"',N'"+name+"',N'"+role+"',0,N'"+sex+"',N'"+major+"',N'"+tel+"',0,N'')";
     	        stmt.execute(sql);
            }

	        //显示增加的记录
            ResultSet rs=stmt.executeQuery("select id,ac_name,name,role,sex,major,tel from PI_Sheet where name=N'"+name+"'and ac_name=N'"+ac_name+"'");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0);
            while(rs.next())
            {
            String[] arr=new String[11];
            arr[0]=rs.getString("id");
            arr[1]=rs.getString("ac_name");

            arr[3]=rs.getString("name");
            arr[4]=rs.getString("role");

            arr[6]=rs.getString("sex");
            arr[7]=rs.getString("major");
            arr[8]=rs.getString("tel");


            tableModel.addRow(arr);
            }
            table.invalidate();
            //结束
            rs.close();
            stmt.close();
            con.close();
		}
		catch(Exception ex)
	    {
	    	System.out.println(ex.getMessage());
	    }

	}
	public add_user() {
		try{
        	//增加记录
	      	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
			 ResultSet rs=stmt.executeQuery("select count(*) from PI_Sheet");
	            rs.next();
	            RowNum=rs.getInt(1);

	            rs=stmt.executeQuery("select * from PI_Sheet");
	            ResultSetMetaData rsmd=rs.getMetaData();
	            ColNum=rsmd.getColumnCount();

	             names=new String[ColNum];     //构造JTable
	             for (i=1;i<=ColNum;i++) names[i-1]=rsmd.getColumnName(i);

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

	            DefaultTableModel model = new DefaultTableModel(info,names);
	            table=new JTable(model);
	            table.setPreferredScrollableViewportSize(new Dimension(400,50));

	            b.addActionListener(this);
	            Panel panel=new Panel();
	            choose_role.addItem("学生");
	            choose_role.addItem("管理员");
	            panel.add(labe00);  panel.add(edit00);
	            panel.add(label0);  panel.add(edit0);
	            panel.add(label1);  panel.add(edit1);
	            panel.add(label2);  panel.add(edit2);
	            panel.add(label3);  panel.add(choose_role);
	            panel.add(label4);  panel.add(edit4);
	            panel.add(label5);  panel.add(edit5);
	            panel.add(label6);  panel.add(edit6);
	            panel.add(b);
	            f.add(panel,BorderLayout.NORTH);
	            f.getContentPane().add(new JScrollPane(table));
	            f.addWindowListener(new WindowAdapter()
	            	{
	            		public void windowClosiing(WindowEvent ew)
	            	    {System.exit(0);}
	            		});
	            f.pack();
                f.setSize(1500,1200);
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


