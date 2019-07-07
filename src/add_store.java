import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
//改变图书编号表 和图书信息表中的库存项
public class add_store implements ActionListener{
	JFrame f=new JFrame("图书管理-增加库存");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    TextField edit0=new TextField(10);
    TextField edit1=new TextField(10);
    Label labe0=new Label("图书ID：");
    Label labe1=new Label("书名：");
    JButton b=new JButton("增加");
    String sql;
    /*
	public static void main(String[] args) {
		new add_store();

	}
    */
	public void actionPerformed(ActionEvent e)
	{
		try{
        	//增加记录到class库的BI_Sheet表中
	      	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
				String book_id=edit0.getText();
				String title=edit1.getText();
				sql="select count(*) from BCI_Sheet where title=N'"+title+"'";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
	            int Num=rs.getInt(1);
				if (Num!=0)
				{
					int result = JOptionPane.showConfirmDialog(f, "你确定要增加编号为："+book_id+"的《"+title+"》吗");
		            if(result==JOptionPane.OK_OPTION)
		            {

		                sql="insert into BI_Sheet values(N'"+book_id+"',N'"+title+"')";
		     	        stmt.execute(sql);
		     	        sql="update BCI_Sheet set stock=stock+1,total=total+1 where title=N'"+title+"'";
		     	        stmt.execute(sql);
		            }
		            rs=stmt.executeQuery("select * from BI_Sheet where title=N'"+title+"'");
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
				else
				{
					//这里可设置为直接跳转到增添新图书类别的界面
					JOptionPane.showMessageDialog(f, "增加库存失败!请确认所输入信息是否正确 或增添新的图书信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
				}
		}

				catch(Exception ex)
			    {
			    	System.out.println(ex.getMessage());
			    	JOptionPane.showMessageDialog(f, "添加失败!请输入正确的信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
			    }
	}


		public add_store() {
			try{
	        	//增加记录
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

		            b.addActionListener(this);
		            Panel panel=new Panel();

		            panel.add(labe0);  panel.add(edit0);
		            panel.add(labe1);  panel.add(edit1);
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
