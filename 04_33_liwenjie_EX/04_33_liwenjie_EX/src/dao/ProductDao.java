package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import bean.Product;
import utils.DBUtil;

public class ProductDao {
	ResultSet rs=null;
	Statement stmt=null;
	Connection conn=null;
	PreparedStatement pstmt=null;
	public List<Product> selectTop12() {
		List<Product> pList=new ArrayList<Product>();
		try {
			conn=DBUtil.getConnection();
			stmt=conn.createStatement();
			String sql="select * from product where is_hot=1 LIMIT 12";
			rs=stmt.executeQuery(sql);
			pList=new ArrayList<Product>();
			while(rs.next()) {
				int pid=rs.getInt("pid");
				String pname=rs.getString("pname");
				double shop_price=rs.getDouble("shop_price");
				String pimage=rs.getString("pimage");
				Date pdate=rs.getDate("pdate");
				int is_hot=rs.getInt("is_hot");
				int cid=rs.getInt("cid");
				Product p=new Product();
				p.setPid(pid);
				p.setPname(pname);
				p.setShop_price(shop_price);
				p.setPimage(pimage);
				p.setPdate(pdate);
				p.setIs_hot(is_hot);
				p.setCid(cid);
				pList.add(p);
			}
			System.out.println("pList:"+pList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.close(rs, stmt, conn);
		}
		return pList;
		}
	
	public List<Product> selectAllProdByPage(int pageNo,int pageSize) {
		List<Product> pList=new ArrayList<Product>();
		try {
			conn=DBUtil.getConnection();
			String sql="select * from product Limit ?,?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,pageNo);
			pstmt.setInt(2,pageSize);
			rs=pstmt.executeQuery();
			pList=new ArrayList<Product>();
			while(rs.next()) {
				int pid=rs.getInt("pid");
				String pname=rs.getString("pname");
				double shop_price=rs.getDouble("shop_price");
				String pimage=rs.getString("pimage");
				Date pdate=rs.getDate("pdate");
				int is_hot=rs.getInt("is_hot");
				int cid=rs.getInt("cid");
				Product p=new Product();
				p.setPid(pid);
				p.setPname(pname);
				p.setShop_price(shop_price);
				p.setPimage(pimage);
				p.setPdate(pdate);
				p.setIs_hot(is_hot);
				p.setCid(cid);
				pList.add(p);
			}
			System.out.println("分页pList:"+pList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.close(rs, stmt, conn);
		}
		return pList;
	}

	public int selectAllProdCount() {
		int count=0;
		try {
			conn=DBUtil.getConnection();
			String sql="select count(*) as t from product";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				count=rs.getInt("t");
				
			}
			System.out.println("count:"+count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.close(rs, stmt, conn);
		}
		return count;
	}

	public Product selectProdInfoById(int prodid) {
		Product p=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from product where pid=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,prodid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				int pid=rs.getInt("pid");
				String pname=rs.getString("pname");
				double shop_price=rs.getDouble("shop_price");
				double market_price=rs.getDouble("market_price");
				String pimage=rs.getString("pimage");
				Date pdate=rs.getDate("pdate");
				int is_hot=rs.getInt("is_hot");
				int cid=rs.getInt("cid");
				p=new Product();
				p.setPid(pid);
				p.setPname(pname);
				p.setShop_price(shop_price);
				p.setMarket_price(market_price);
				p.setPimage(pimage);
				p.setPdate(pdate);
				p.setIs_hot(is_hot);
				p.setCid(cid);
			}
			System.out.println("pinfo:"+p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.close(rs, stmt, conn);
		}
		return p;
	}
}
