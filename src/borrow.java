import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

//需要通过查表 知道所要书的编号
public class borrow implements ActionListener{
	JFrame f=new JFrame("借书");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    TextField edit0=new TextField(10);
    Label label0=new Label("所要借的书籍名字：");
    JButton b=new JButton("借阅");
    String sql;
    String ID_now;//假设现在的用户ID是201601
    /*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new borrow();
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
			sql="select vi_number from PI_Sheet where id='"+ID_now+"'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
            int vi_num=rs.getInt(1);
            sql="select bd_number from PI_Sheet where id='"+ID_now+"'";
            rs = stmt.executeQuery(sql);
			rs.next();
            int bd_num=rs.getInt(1);
            //sql="select title from BI_Sheet where book_id=N'"+book_id+"'";
            //rs = stmt.executeQuery(sql);
			//rs.next();
            //String title=rs.getString(1);
            sql="select stock from BCI_Sheet where title=N'"+title+"'";
            rs = stmt.executeQuery(sql);
			rs.next();
            int stock=rs.getInt(1);

            sql="select BI_Sheet.book_id from BI_Sheet,BR_Sheet where BI_Sheet.book_id != BR_Sheet.book_id and title=N'"+title+"'";
            rs = stmt.executeQuery(sql);
			rs.next();
            String book_id = rs.getString(1);

            //判断是否有资格借书 如果借满5本书或者已经违章3次则不允许再借 如库存不足也不借
            if(vi_num<3 && bd_num<5 &&stock>0) {
            	int result = JOptionPane.showConfirmDialog(f, "你确定要借阅编号为："+book_id+"的《"+title+"》的吗?");
	            if(result==JOptionPane.OK_OPTION)
	            {
	            	sql="insert into BR_Sheet values(N'"+book_id+"',N'"+ID_now+"',getdate(),'',0)";
	            	stmt.execute(sql);
	            	sql="update PI_Sheet set bd_number=bd_number+1 where id='"+ID_now+"'";
	            	stmt.execute(sql);
	            	sql="update BCI_Sheet set stock=stock-1 where title=N'"+title+"'";
	            	stmt.execute(sql);
                    JOptionPane.showMessageDialog(f, "您借书成功啦!", "成功啦!!", JOptionPane.ERROR_MESSAGE);
	            }
            }
            else if(vi_num>=3){
	    		JOptionPane.showMessageDialog(f, "您逾期次数太多，所以不能够借这本书!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
            }
            else if(stock==0){
	    		JOptionPane.showMessageDialog(f, "抱歉!库存不足，借书失败!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
            }
            else if(bd_num>=5){
	    		JOptionPane.showMessageDialog(f, "抱歉!您已经借了太多的书，请先归还后再借阅!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
            }
            else {
	    		JOptionPane.showMessageDialog(f, "借书失败!", "出错啦!!", JOptionPane.ERROR_MESSAGE);

            }

            rs.close();
            stmt.close();
            con.close();
		}
		catch(Exception ex)
	    {
	    		System.out.println(ex.getMessage());
	    		JOptionPane.showMessageDialog(f, "借书失败!请输入正确的信息!", "出错啦!!", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public borrow(String id) {
        ID_now = id;
		Panel panel=new Panel();
        b.addActionListener(this);
        panel.add(label0);  panel.add(edit0);
        panel.add(b);
        f.add(panel,BorderLayout.NORTH);
        f.addWindowListener(new WindowAdapter()
        	{
        		public void windowClosiing(WindowEvent ew)
        	    {System.exit(0);}
        		});
        f.pack();
        f.setSize(400,300);
        f.setVisible(true);

	}
}
