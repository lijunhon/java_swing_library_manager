import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
public class add_booktype implements ActionListener{
	JFrame f=new JFrame("图书管理-增加图书种类");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    TextField edit0=new TextField(10);
    TextField edit1 = new TextField(10);
    TextField edit2=new TextField(10);
    TextField edit3=new TextField(10);
    TextField edit4=new TextField(10);
    //TextField edit5=new TextField(10);
   // TextField edit6=new TextField(10);
    TextField edit7=new TextField(10);
    TextField edit8=new TextField(10);
    Label labe00=new Label("书名：");//title
    Label labe01=new Label("作者：");//author
    Label labe02=new Label("出版社：");//pub_house
    Label labe03=new Label("出版日期：");//pub_date
    Label labe04=new Label("类别：");
    //Label labe05=new Label("总数：");
    //Label labe06=new Label("库存：");
    Label labe07=new Label("简介：");//introduction
    Label labe08=new Label("备注：");//b_remarks
    JButton b=new JButton("增加");
    /*
	public static void main(String[] args) {
		new add_booktype();

	}
    */
	public void actionPerformed(ActionEvent e)
	{
		try{
        	//增加记录到class库的BCI_Sheet表中
	      	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con=DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
				Statement stmt=con.createStatement();
			String title=edit0.getText();
			String author=edit1.getText();
			String pub_house=edit2.getText();
			String pub_date=edit3.getText();
			String category=edit4.getText();
			//String total=edit5.getText();默认新建的图书的库存都是0
			//String stock=edit6.getText();
			String introduction=edit7.getText();
			String b_remarks=edit8.getText();
            int result = JOptionPane.showConfirmDialog(f, "你确定要增加图书：《"+title+"》吗");
            if(result==JOptionPane.OK_OPTION)
            {

                 String sql="insert into BCI_Sheet values(N'"+title+"',N'"+author+"',N'"+pub_house+"','"+pub_date+"',N'"+category+"',0,0,N'"+introduction+"',N'"+b_remarks+"')";
     	        stmt.execute(sql);
            }

	        //显示增加的记录
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
            table.invalidate();
            //结束
            rs.close();
            stmt.close();
            con.close();
		}
		catch(Exception ex)
	    {
	    	System.out.println(ex.getMessage());
	    	JOptionPane.showMessageDialog(f, "添加失败!请输入正确的信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);//更改
	    }

	}


public add_booktype() {
	try{
    	//增加记录
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

            b.addActionListener(this);
            Panel panel=new Panel();
            panel.add(labe00);  panel.add(edit0);
            panel.add(labe01);  panel.add(edit1);
            panel.add(labe02);  panel.add(edit2);
            panel.add(labe03);  panel.add(edit3);
            panel.add(labe04);  panel.add(edit4);
            panel.add(labe07);  panel.add(edit7);
            panel.add(labe08);  panel.add(edit8);

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
