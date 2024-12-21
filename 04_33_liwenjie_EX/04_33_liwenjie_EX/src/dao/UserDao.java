 package dao;

 import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 import java.sql.SQLException;
 
 import java.sql.Statement;

import bean.User;
import utils.DBUtil;
 
public class UserDao {
	ResultSet rs;
 	Statement stmt;
 	Connection conn;

public User selectUserByName(User u) {
	User findu=null;
	try {
		conn=DBUtil.getConnection();
		stmt=conn.createStatement();
		String sql="select * from user where username='"+u.getUsername()+"'";
		rs=stmt.executeQuery(sql);

		if(rs.next()) {
			findu=new User();
			
			String username=rs.getString("username");
			String password=rs.getString("password");
			String sex=rs.getString("sex");
			String tel=rs.getString("telephone");
			
			findu.setUid(rs.getString("uid"));
			findu.setUsername(username);
			findu.setPassword(password);
			findu.setSex(sex);
			findu.setTelephone(tel);
			
		}
		System.out.println("findu:"+findu);
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();

	}
	return findu;
	}

     public int insertUser(User u)
    {
    	int n=0;
    	
    	try {
    		conn=DBUtil.getConnection();
    		String sql="update user set password=?,sex=?,telephone=? where username=?";
    		PreparedStatement pstmt=(PreparedStatement) conn.prepareStatement(sql);
    		
    		pstmt.setString(4, u.getUsername());
    		pstmt.setString(1, u.getPassword());
    		
    		
    		pstmt.setString(3, u.getTelephone());
    		
    		pstmt.setString(2, u.getSex());
    		
    		n=pstmt.executeUpdate();
    		System.out.println("n:"+n);
    	}
    		catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return n;
    	
    }

	public int updateUser(User u) {
		// TODO Auto-generated method stub
		return 0;
	}

    }




