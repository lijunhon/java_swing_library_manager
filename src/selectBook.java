/* name: selectUser.java
 * function:实现actionListenner接口，管理员查询借书用户的信息
 * time: 2019.6.20
 * author: lijunhon
 *
 */

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.*;
//public class selectUser implements ActionListener
public class selectBook
{
    //private String ex;
    //public ResultSet rs;
    private  String title;
    private  String book_id;
    private  String author;
    private String pub_house;//传入的参数
    public int rownum;
    public int colnum;
    public int row;
    public Object result_all[][];
    public Object result_select[][];
    String names[];
    public selectBook()
    {
        try{
            //装载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //创建连接
            Connection con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("select count(*) from BCI_Sheet");
            rs.next();
            rownum = rs.getInt(1);
            result_all = new Object[rownum][];
            result_select = new Object[rownum][];
            rs=stmt.executeQuery("select * from BCI_Sheet");
            ResultSetMetaData rsmd=rs.getMetaData();
            colnum=rsmd.getColumnCount();
            names=new String[colnum];
            int i,j;
            for (i=1;i<=colnum;i++) names[i-1]=rsmd.getColumnName(i);
            result_all=new Object[rownum][];
            result_select=new Object[rownum][];

            i=0;
            while (rs.next())
            {
                result_all[i]=new Object[colnum];
                for(j=1;j<=colnum;j++)
                    result_all[i][j-1]=rs.getObject(j);
                i++;
                //result_all[0]=rs.getString("id");
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception eex)
        {
            System.out.println(eex.getMessage());
        }

    }

        public selectBook(String title,String book_id,String author,String pub_house)
        {
            this.title = title;
            this.book_id = book_id;
            this.author = author;
            this.pub_house = pub_house;
            try{
                //装载驱动程序
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //创建连接
                Connection con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
                Statement stmt=con.createStatement();

                ResultSet rs=stmt.executeQuery("select count(*) from BCI_Sheet");
                rs.next();
                rownum = rs.getInt(1);
                result_all = new Object[rownum][];
                result_select = new Object[rownum][];
                rs=stmt.executeQuery("select * from BCI_Sheet");
                ResultSetMetaData rsmd=rs.getMetaData();
                colnum=rsmd.getColumnCount();
                names=new String[colnum];
                System.out.println(colnum);
                int i,j;
                for (i=1;i<=colnum;i++) names[i-1]=rsmd.getColumnName(i);
                result_all=new Object[rownum][];
                result_select=new Object[rownum+1][];
                i= 0;
                while (rs.next())
                {
                    result_all[i]=new Object[colnum];
                    for (j=1;j<=colnum;j++)
                        result_all[i][j-1]=rs.getObject(j);
                    i++;
                }
                //已经获取了总的信息
                //下面是查询信息
                //rs=stmt.executeQuery("select BCI_Sheet.title,author,pub_house,pub_date,category,total,stock,introduction,b_remarks from BCI_Sheet,BI_Sheet where BCI_Sheet.title=BI_Sheet.title and (BCI_Sheet.title=N'"+title+"' OR book_id=N'"+book_id+"' OR author=N'%"+author+"' OR pub_house=N'"+pub_house+"')");
                String ex="select *"
            		+ "from BCI_Sheet where title=N'"+title+"' or author=N'"
            		+author+"' or pub_house = N'"+pub_house+"'";
                rs = stmt.executeQuery(ex);

                //rs=stmt.executeQuery("select title,author,pub_house,pub_date,category,total,stock,introduction,b_remarks from BCI_Sheet where BCI_Sheet.title=N'"+title+"' OR author=N'"+author+"' OR pub_house=N'"+pub_house+"'");
                //添加查询到的记录集
                i = 0;
                while(rs.next())
                {
                    result_select[i] = new Object[colnum];
                    for(j = 1;j<colnum;j++)
                        result_select[i][j] = rs.getObject(j);
                    i++;
                    /*
                    System.out.println("==================i,j================");
                    System.out.println(i);
                    System.out.println(j);
                    */
                }
                row = i;//检查出来的行数

                //刷新表格，即重新绘制
                rs.close();
                stmt.close();
                con.close();
            }
            catch(Exception eex)
            {
                System.out.println(eex.getMessage());
            }
        }
    }
