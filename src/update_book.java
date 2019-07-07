import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
public class update_book  extends MouseAdapter implements ActionListener {
	JFrame f=new JFrame("DB Test");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    TextField edit0=new TextField(10);
    TextField edit1=new TextField(12);
    TextField edit2=new TextField(10);
    TextField edit3=new TextField(10);
    TextField edit4=new TextField(10);
    TextField edit5=new TextField(10);
    TextField edit6=new TextField(10);
    Label label0=new Label("书名：");//设置书名，库存和总数不可更改
    Label label1=new Label("作者：");
    Label label2=new Label("出版社：");
    Label label3=new Label("出版日期：");
    Label label4=new Label("类别：");
    Label label5=new Label("简介：");
    Label label6=new Label("备注：");
    JButton c=new JButton("修改");
    /*
	public static void main(String[] args) {
		new update_book();
	}
    */
	public void mouseClicked(MouseEvent e)//JTable响应鼠标单击事件，让用户选择的记录信息填写到上面响应的文本框中。
	{
		int i=table.getSelectedRow();
		String help=table.getValueAt(i,0).toString();
		edit0.setText(help);
		help=table.getValueAt(i,1).toString();
		edit1.setText(help);
		help=table.getValueAt(i,2).toString();
		edit2.setText(help);
		help=table.getValueAt(i,3).toString();
		edit3.setText(help);
		help=table.getValueAt(i,4).toString();
		edit4.setText(help);
		help=table.getValueAt(i,7).toString();
		edit5.setText(help);
		help=table.getValueAt(i,8).toString();
		edit6.setText(help);
	}
	public void actionPerformed(ActionEvent e) {
		try{
			 	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
				String title=edit0.getText();
				String author=edit1.getText();
	            String pub_house=edit2.getText();
	            String pub_date=edit3.getText();
	            String category=edit4.getText();
	            String introduction=edit5.getText();
	            String b_remarks=edit6.getText();

	            int result = JOptionPane.showConfirmDialog(f, "你确定要修改"+author+"的《"+title+"》的信息吗");
	            if(result==JOptionPane.OK_OPTION)
	            {
	            //System.out.print("update BCI_Sheet set title=N'"+title+"',author=N'"+author+"',pub_house=N'"+pub_house+"',pub_date='"+pub_date+"',category=N'"+category+"',introduction=N'"+introduction+"',b_remarks=N'"+b_remarks+"' where title=N'"+title+"'");
	            stmt.execute("update BCI_Sheet set title=N'"+title+"',author=N'"+author+"',pub_house=N'"+pub_house+"',pub_date='"+pub_date+"',category=N'"+category+"',introduction=N'"+introduction+"',b_remarks=N'"+b_remarks+"' where title=N'"+title+"'");//
	            //sql="update BI_Sheet set title=N'"+title+"'";
	            //stmt.execute(sql);
	            ResultSet rs=stmt.executeQuery("select * from BCI_Sheet where title=N'"+title+"'");
	            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
	            tableModel.setRowCount(0);
	            while(rs.next())
	            {
	            String[] arr=new String[9];
	            arr[0]=rs.getString("title");
	            arr[1]=rs.getString("author");
	            arr[2]=rs.getString("pub_house");
	            arr[3]=rs.getString("pub_date");
	            arr[4]=rs.getString("category");
	            arr[5]=rs.getString("total");
	            arr[6]=rs.getString("stock");
	            arr[7]=rs.getString("introduction");
	            arr[8]=rs.getString("b_remarks");

	            tableModel.addRow(arr);
	            }
	            rs.close();
	            }

	            table.invalidate();
	            //结束

	            stmt.close();
	            con.close();
		}
	    		catch(Exception ex)
	    	    {
	    	    		System.out.println(ex.getMessage());
	    	    		JOptionPane.showMessageDialog(f, "更改信息失败!请输入合理的修改信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);//更改
	    	    }
		}

public update_book() {
	try{
      	  	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
			Statement stmt=con.createStatement();
			 ResultSet rs=stmt.executeQuery("select count(*) from BCI_Sheet");
	        rs.next();
	        RowNum=rs.getInt(1);
	        rs=stmt.executeQuery("select * from BCI_Sheet");

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
            c.addActionListener(this);
            Panel panel=new Panel();
            edit0.setEditable(false);
            panel.add(label0);  panel.add(edit0);
            panel.add(label1);  panel.add(edit1);
            panel.add(label2);  panel.add(edit2);
            panel.add(label3);  panel.add(edit3);
            panel.add(label4);  panel.add(edit4);
            panel.add(label5);  panel.add(edit5);
            panel.add(label6);  panel.add(edit6);
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
            f.setSize(1000,600);
            f.setVisible(true);
            rs.close();
            stmt.close();
            con.close();



	}
	catch(Exception ex)
    {
    		System.out.println(ex.getMessage());
    		JOptionPane.showMessageDialog(f, "更改信息失败!请输入合理的修改信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
    }


}
}
