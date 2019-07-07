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
public class selectUser
{
    //private String ex;
    //public ResultSet rs;
    private  String ac_name;
    private String id;//传入的参数
    public int rownum;
    public int colnum;
    public int row;
    public Object result_all[][];
    public Object result_select[][];
    String names[];

    public selectUser()
    {
        try{
            //装载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //创建连接
            Connection con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("select count(*) from PI_Sheet");
            rs.next();
            rownum = rs.getInt(1);
            result_all = new Object[rownum][];
            result_select = new Object[rownum][];
            rs=stmt.executeQuery("select * from PI_Sheet");
            ResultSetMetaData rsmd=rs.getMetaData();
            colnum=rsmd.getColumnCount();
            names=new String[colnum];
            int i,j;
            for (i=1;i<=colnum;i++) names[i-1]=rsmd.getColumnName(i);
            result_all=new Object[rownum][];
            result_select=new Object[rownum][];
            i= 0;
            while (rs.next())
            {
                result_all[i]=new Object[colnum];
                for (j=1;j<=colnum;j++)
                    result_all[i][j-1]=rs.getObject(j);
                i++;
            }
            //已经获取了总的信息
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception eex)
        {
            System.out.println(eex.getMessage());
        }
    }

    public selectUser(String ac_name,String id)
    {
        this.ac_name = ac_name;
        this.id = id;
        try{
            //装载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //创建连接
            Connection con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=class","sa","ljh123.L");
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("select count(*) from PI_Sheet");
            rs.next();
            rownum = rs.getInt(1);
            result_all = new Object[rownum][];
            result_select = new Object[rownum][];
            rs=stmt.executeQuery("select * from PI_Sheet");
            ResultSetMetaData rsmd=rs.getMetaData();
            colnum=rsmd.getColumnCount();
            names=new String[colnum];
            int i,j;
            for (i=1;i<=colnum;i++) names[i-1]=rsmd.getColumnName(i);
            result_all=new Object[rownum][];
            result_select=new Object[rownum][];
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
            rs=stmt.executeQuery("select * from PI_Sheet where id=N'"+id+"' OR ac_name=N'"+ac_name+"' OR name=N'"+ac_name+"'");
            //添加查询到的记录集
            i = 0;

            while(rs.next())
            {
                result_select[i] = new Object[colnum];
                for(j = 1;j<colnum;j++)
                {
                    result_select[i][j] = rs.getObject(j);
                }
                i++;
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
