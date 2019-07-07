import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
public class delete_book extends MouseAdapter implements ActionListener{
	JFrame f=new JFrame("图书删除");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    JButton b=new JButton("删除");
    String sql;
    String clickrow;
    /*
	public static void main(String[] args) {
		new delete_book();

	}
    */
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("选中鼠标了");
        int i=table.getSelectedRow();
        clickrow=table.getValueAt(i,0).toString();
    }
	public void actionPerformed(ActionEvent e)
	{
		try{
	      	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
	            //删除记录
	            int i=table.getSelectedRow();
	            String book_id=table.getValueAt(i,0).toString();
	            String title=table.getValueAt(i,1).toString();
	            sql="select count(*) from BCI_Sheet where title=N'"+title+"'";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
	            int Num=rs.getInt(1);
				if (Num!=0)
				{
		            int result = JOptionPane.showConfirmDialog(f, "你确定要删除编号为："+book_id+"的 《"+title+"》吗");

		            if(result==JOptionPane.OK_OPTION)
		            {
		            	 sql="delete BI_Sheet where book_id=N'"+book_id+"'";
		                 stmt.executeUpdate(sql);
		                 sql="update BCI_Sheet set stock=stock-1,total=total-1 where title=N'"+title+"'";
			     	     stmt.execute(sql);
		            }
		            rs=stmt.executeQuery("select * from BI_Sheet");
		            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
		            tableModel.setRowCount(0);
		            while(rs.next())
		            {
		            String[] arr=new String[2];
		            arr[0]=rs.getString("book_id");
		            arr[1]=rs.getString("title");

		            tableModel.addRow(arr);
		            }
		             table.invalidate();
		             rs.close();
		             stmt.close();
		             con.close();
					}

	 		}
	 		catch(Exception ex)
	 	    {
	 	    	System.out.println(ex.getMessage());
	 	    	JOptionPane.showMessageDialog(f, "删除失败!", "出错啦!!", JOptionPane.ERROR_MESSAGE);//更改
	 	    }
	 	}

	public delete_book(){
		try{
	      	  	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
						("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
				 ResultSet rs=stmt.executeQuery("select count(*) from BI_Sheet");
		        rs.next();
		        RowNum=rs.getInt(1);
		        rs=stmt.executeQuery("select * from BI_Sheet");
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
	            //b.addActionListener(this);
	            f.add(b,BorderLayout.SOUTH);
	            //table.addMouseListener(this);
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


        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k=table.getSelectedRow();
                clickrow=table.getValueAt(k,0).toString();

                try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con=DriverManager.getConnection
                    ("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
                Statement stmt=con.createStatement();

                int result = JOptionPane.showConfirmDialog(f, "你确定要删除"+clickrow+"的信息吗");
                if(result==JOptionPane.OK_OPTION)
                {
                    System.out.println("执行到这里出错？"+clickrow);
                    stmt.executeUpdate("delete BI_Sheet where book_id=N'"+clickrow+"'");
                }
                ResultSet rs=stmt.executeQuery("select * from BI_Sheet");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0);
            while (rs.next())
            {

                 String arr[]=new String[2];

                 arr[0]=rs.getString("book_id");

                 arr[1]=rs.getString("title");
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
                    JOptionPane.showMessageDialog(f, "删除失败!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

	}

}

