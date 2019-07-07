import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
//该功能可以将和修改功能放到一起
public class delete_user extends MouseAdapter implements ActionListener{
	JFrame f=new JFrame("用户删除");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    JButton b=new JButton("删除");
    String clickrow;

    /*
	public static void main(String[] args) {
		new delete_user();
	}
    */

	public void actionPerformed(ActionEvent e)
	{

		try{
        	//增加记录
			//默认添加到本地keshe库的PI_Sheet表中
	      	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
	            //删除记录
	            int i=table.getSelectedRow();
	            String id=table.getValueAt(i,0).toString();
	            String role=table.getValueAt(i,4).toString();
	            String name=table.getValueAt(i,3).toString();
	            int result = JOptionPane.showConfirmDialog(f, "你确定要删除编号为："+id+"的"+role+name+"吗");

	            if(result==JOptionPane.OK_OPTION)
	            {
	            	 String sql="delete PI_Sheet where id="+"'"+id+"'";
	                 stmt.executeUpdate(sql);
	            }
	            ResultSet rs=stmt.executeQuery("select * from PI_Sheet");
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
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("选中鼠标了");
        int i=table.getSelectedRow();
        clickrow=table.getValueAt(i,0).toString();
    }
	public delete_user(){
		try{
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
                    stmt.executeUpdate("delete PI_Sheet where id=N'"+clickrow+"'");
                }
                ResultSet rs=stmt.executeQuery("select * from PI_Sheet");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0);
            while (rs.next())
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
