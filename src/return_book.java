import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class return_book implements ActionListener{
	JFrame f=new JFrame("还书");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    TextField edit0=new TextField(10);
    Label label0=new Label("所要还的书籍编号：");
    JButton b=new JButton("还书");
    String sql;
    String ID_now;//假设现在的用户ID是201601
    /*
	public static void main(String[] args) {

		new return_book();
	}
    */
	public void actionPerformed(ActionEvent e)
	{
		try{
	      	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
			String book_id=edit0.getText();
            sql="select title from BI_Sheet where book_id=N'"+book_id+"'";
            ResultSet rs = stmt.executeQuery(sql);
			rs.next();
            String title=rs.getString(1);
			//修改借还信息表
        	int result = JOptionPane.showConfirmDialog(f, "你确定要归还编号为："+book_id+"的《"+title+"》的吗?");
            if(result==JOptionPane.OK_OPTION)
            {

	            sql="select b_date from BR_Sheet where book_id=N'"+book_id+"' and r_date='1900-01-01 00:00:00.0'";
	            //System.out.print(sql);
	            rs=stmt.executeQuery(sql);
	            rs.next();
				String b_date=rs.getString(1);

				sql="update BR_Sheet set r_date=getdate() where book_id=N'"+book_id+"' and r_date=''";
	        	stmt.execute(sql);
	        	sql="update BR_Sheet set overdue=1 where DATEDIFF(day,b_date,getdate())>31 and book_id=N'"+book_id+"' and b_date='"+b_date+"'";
	            System.out.print("aaaa");
	        	stmt.execute(sql);

	            sql="select overdue from BR_Sheet where book_id=N'"+book_id+"' and b_date='"+b_date+"'";
	            rs = stmt.executeQuery(sql);
				rs.next();
				int overdue=rs.getInt(1);
	        	sql="update PI_Sheet set bd_number=bd_number-1 where id='"+ID_now+"'";
	        	stmt.execute(sql);
	            System.out.print("aaaaaaa");
            	sql="update BCI_Sheet set stock=stock+1 where title=N'"+title+"'";
	        	stmt.execute(sql);
				if(overdue==1) {
		    		JOptionPane.showMessageDialog(f, "注意：本次借书已逾期!!逾期三次将不允许再借书", "注意!!", JOptionPane.ERROR_MESSAGE);
	            	sql="update PI_Sheet set vi_number=vi_number+1 where id='"+ID_now+"'";
		        	stmt.execute(sql);
		            System.out.print("aaaaaaaaaaaaa");

				}
            }
            rs=stmt.executeQuery("select * from BR_Sheet where id='"+ID_now+"'");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0);
            while(rs.next())
            {
            String[] arr=new String[5];
            arr[0]=rs.getString("book_id");
            arr[1]=rs.getString("id");
            arr[2]=rs.getString("b_date");
            arr[3]=rs.getString("r_date");
            arr[4]=rs.getString("overdue");
            tableModel.addRow(arr);
            }
            table.invalidate();
            rs.close();
            stmt.close();
            con.close();
		}
		catch(Exception ex)
	    {
    		System.out.println(ex.getMessage());
    		JOptionPane.showMessageDialog(f, "还书失败!请输入正确的信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public return_book(String id){
        ID_now = id;
		try{
	      	  	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
						("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
				 ResultSet rs=stmt.executeQuery("select count(*) from BR_Sheet where id='"+ID_now+"'");
		        rs.next();
		        RowNum=rs.getInt(1);
		        rs=stmt.executeQuery("select * from BR_Sheet where id='"+ID_now+"'");
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
	    		Panel panel=new Panel();

	            table.setPreferredScrollableViewportSize(new Dimension(400,50));
	            b.addActionListener(this);
	            panel.add(label0);  panel.add(edit0);
	            f.add(b,BorderLayout.SOUTH);
	            f.add(panel,BorderLayout.NORTH);
	            f.getContentPane().add(new JScrollPane(table));
	            f.addWindowListener(new WindowAdapter()
	            	{
	            		public void windowClosiing(WindowEvent ew)
	            	    {System.exit(0);}
	            		});
	            f.pack();
                f.setSize(600,400);
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

