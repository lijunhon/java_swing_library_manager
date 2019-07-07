import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
// 需要查询功能的协助 将查询到的相应用户id显示到GUI表中
// 通过点击表中的记录对记录修改
public class update_user  extends MouseAdapter implements ActionListener {
	JFrame f=new JFrame("用户增加");
    //JPanel f;
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
   // TextField edit=new TextField(10);
   // Label label=new Label("输入需要修改的用户id：");
   // JButton b=new JButton("查询");

    JComboBox choose_role=new JComboBox();
    TextField edit00=new TextField(10);
    TextField edit0=new TextField(10);
    JPasswordField edit1 = new JPasswordField(12);
    TextField edit2=new TextField(10);
    //TextField edit3=new TextField(10);
    TextField edit4=new TextField(10);
    TextField edit5=new TextField(10);
    TextField edit6=new TextField(10);
    TextField edit7=new TextField(10);
    TextField edit8=new TextField(10);
    TextField edit9=new TextField(10);
    Label labe00=new Label("ID：");
    Label label0=new Label("账户名：");
    Label label1=new Label("密码：");//password 需要用*来显示
    Label label2=new Label("姓名：");//name
    Label label3=new Label("身份：");
    Label label4=new Label("已借阅数：");
    Label label5=new Label("性别：");
    Label label6=new Label("专业：");
    Label label7=new Label("联系方式：");
    Label label8=new Label("违章次数：");
    Label label9=new Label("备注：");
    JButton c=new JButton("修改");

    /*
	public static void main(String args[])
	{
		new update_user();
	}
    */
	public void mouseClicked(MouseEvent e)//JTable响应鼠标单击事件，让用户选择的记录信息填写到上面响应的文本框中。
	{
		int i=table.getSelectedRow();
		String help=table.getValueAt(i,0).toString();
		edit00.setText(help);
		help=table.getValueAt(i,1).toString();
		edit0.setText(help);
		help=table.getValueAt(i,2).toString();
		edit1.setText(help);
		help=table.getValueAt(i,3).toString();
		edit2.setText(help);
		//help=table.getValueAt(i,4).toString();
		//edit3.setText(help);
		help=table.getValueAt(i,5).toString();
		edit4.setText(help);
		help=table.getValueAt(i,6).toString();
		edit5.setText(help);
		help=table.getValueAt(i,7).toString();
		edit6.setText(help);
		help=table.getValueAt(i,8).toString();
		edit7.setText(help);
		help=table.getValueAt(i,9).toString();
		edit8.setText(help);
		help=table.getValueAt(i,10).toString();
		edit9.setText(help);
	}
	public void actionPerformed(ActionEvent e) {
		try{
			 	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
				String id=edit00.getText();
				String ac_name=edit0.getText();
				char[] pass=edit1.getPassword();
				String password = String.valueOf(pass);
	            String name=edit2.getText();
	            String role=choose_role.getSelectedItem().toString();
	            String bd_number=edit4.getText();
	            int i = Integer.parseInt(bd_number);
	            String sex=edit5.getText();
	            String major=edit6.getText();
	            String tel=edit7.getText();
	            String vi_number=edit8.getText();//ID和违章次数不能随意改  设置成了不可选
	            String p_remarks=edit9.getText();
	            //合法性判断
	            if(ac_name.length()<=12 && password.length()<=20 && name.length()<=12 && role.length()<=6 && i<=5 && sex.length()==2 && major.length()<=12 && tel.length()<=11 && p_remarks.length()<=100 )
	            {
		            int result = JOptionPane.showConfirmDialog(f, "你确定要修改ID为："+id+"的"+role+name+"的信息吗");
		            if(result==JOptionPane.OK_OPTION)
		            {
		            String sql="update PI_Sheet set ac_name=N'"+ac_name+"',passwd=N'"+password+"',name=N'"+name+"',role=N'"+role+"',bd_number="+bd_number+",sex=N'"+sex+"',major=N'"+major+"',tel=N'"+tel+"',vi_number="+vi_number+",p_remarks=N'"+p_remarks+"' where id='"+id+"'";
		            stmt.execute(sql);

		            ResultSet rs=stmt.executeQuery("select id,ac_name,passwd,name,role,bd_number,sex,major,tel,vi_number,p_remarks from PI_Sheet where name=N'"+name+"'and ac_name=N'"+ac_name+"'");
		            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
		            tableModel.setRowCount(0);
		            while(rs.next())
		            {
		            String[] arr=new String[11];
		            arr[0]=rs.getString("id");
		            arr[1]=rs.getString("ac_name");
		            arr[2]=rs.getString("passwd");
		            arr[3]=rs.getString("name");
		            arr[4]=rs.getString("role");
		            arr[5]=rs.getString("bd_number");
		            arr[6]=rs.getString("sex");
		            arr[7]=rs.getString("major");
		            arr[8]=rs.getString("tel");
		            arr[9]=rs.getString("vi_number");
		            arr[10]=rs.getString("p_remarks");

		            tableModel.addRow(arr);
		            }
		            rs.close();
		            }

		            table.invalidate();
		            //结束

		            stmt.close();
		            con.close();
		        }
	            else {
	            	JOptionPane.showMessageDialog(null, "请输入合理的修改信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
		            stmt.close();
		            con.close();
	            }
			}


		catch(Exception ex)
	    {
	    		System.out.println(ex.getMessage());
	    		JOptionPane.showMessageDialog(f, "更改信息失败!请输入合理的修改信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);//更改
	    }
	}

	public update_user() {
		try{
        	//更新记录的简易GUI设计 框稍微有点bug 输入框没有全部显示出来
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
	            edit00.setEditable(false);
	            edit8.setEditable(false);
	            c.addActionListener(this);
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
	            panel.add(label7);  panel.add(edit7);
	            panel.add(label8);  panel.add(edit8);
	            panel.add(label9);  panel.add(edit9);
	            panel.add(c);
	            f.add(panel,BorderLayout.NORTH);
	            f.add(c,BorderLayout.SOUTH);
	            table.addMouseListener(this);
	            f.getContentPane().add(new JScrollPane(table));
	            f.addWindowListener(new WindowAdapter()
	            	{
	            		public void windowClosiing(WindowEvent ew)
	            	    {System.exit(0);}
	            		});
	            f.pack();
                f.setSize(1300,600);
	            f.setVisible(true);
	            rs.close();
	            stmt.close();
	            con.close();



		}
		catch(Exception ex)
	    {
	    		System.out.println(ex.getMessage());
	    }


	}
}
