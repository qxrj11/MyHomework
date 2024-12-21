package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Order;
import bean.OrderItem;
import bean.Product;
import bean.User;
import utils.DBUtil;

public class OrderDao {
	ResultSet rs;
	Statement stmt;
	Connection conn;
	PreparedStatement pstmt;

	public void saveOrder(Connection conn, Order order) {
		try {
	        String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
	        pstmt=conn.prepareStatement(sql);
	        pstmt.setString(1, order.getOid());
	        pstmt.setDate(2, new java.sql.Date(order.getOrdertime().getTime()));
	        pstmt.setDouble(3, order.getTotal());
	        pstmt.setInt(4, order.getState());
	        pstmt.setString(5, order.getAddress());
	        pstmt.setString(6, order.getName());
	        pstmt.setString(7, order.getTelephone());
	        pstmt.setString(8, order.getUid());
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }		
	}

	public void saveOrderItem(Connection conn, OrderItem oItem) {
		try {
	        String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
	        pstmt=conn.prepareStatement(sql);
	        pstmt.setString(1, oItem.getItemid());
	        pstmt.setInt(2, oItem.getCount());
	        pstmt.setDouble(3, oItem.getSubtotal());
	        pstmt.setInt(4, oItem.getProduct().getPid());
	        pstmt.setString(5, oItem.getOid());
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public Order selectOrderByOid(String oid) {
		Order order=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT * FROM orders WHERE oid=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, oid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				order=new Order();
				order.setOid(rs.getString("oid"));
				order.setOrdertime(rs.getDate("ordertime"));
				order.setTotal(rs.getDouble("total"));
				order.setState(rs.getInt("state"));
				order.setAddress(rs.getString("address"));
				order.setName(rs.getString("name"));
				order.setTelephone(rs.getString("telephone"));
				order.setUid(rs.getString("uid"));
			}
			System.out.println("order:"+order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}

	public int updateOrder(Order order) {
		int n=0;
		try {
	        conn = DBUtil.getConnection();
	        String sql = "update orders set address=?,name=?,telephone=? where oid=?";
	        pstmt=conn.prepareStatement(sql);
	        pstmt.setString(1, order.getAddress());
	        pstmt.setString(2, order.getName());
	        pstmt.setString(3, order.getTelephone());
	        pstmt.setString(4, order.getOid());
	        n=pstmt.executeUpdate();
	        System.out.println("n:" + n);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return n;
	}

	public List<Order> selectOrdersByUid(User user, int pageNo, int pageSize) {
		List<Order> orderList=new ArrayList<Order>();
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT * FROM orders WHERE uid=? LIMIT ?,?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getUid());
			pstmt.setInt(2, (pageNo-1)*pageSize);
			pstmt.setInt(3, pageSize);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Order order=new Order();
				order.setOid(rs.getString("oid"));
				order.setOrdertime(rs.getDate("ordertime"));
				order.setTotal(rs.getDouble("total"));
				order.setState(rs.getInt("state"));
				order.setAddress(rs.getString("address"));
				order.setName(rs.getString("name"));
				order.setTelephone(rs.getString("telephone"));
				order.setUid(rs.getString("uid"));
				orderList.add(order);
			}
			for (Order order : orderList) {
				sql="SELECT * FROM orderitem o ,product p WHERE o.pid=p.pid and oid=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, order.getOid());
				rs=pstmt.executeQuery();
				while(rs.next()) {
					OrderItem oi=new OrderItem();
					oi.setItemid(rs.getString("itemid"));
					oi.setCount(rs.getInt("count"));
					oi.setSubtotal(rs.getDouble("subtotal"));
					oi.setOid(rs.getString("oid"));
					Product p =new Product();
					p.setPid(rs.getInt("pid"));
					p.setPimage(rs.getString("pimage"));
					p.setPname(rs.getString("pname"));
					p.setShop_price(rs.getDouble("shop_price"));
					oi.setProduct(p);
					order.getList().add(oi);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}

	public int selectOrderCount(String uid) {
		int count =0;
		 try {
		        conn = DBUtil.getConnection();
		        String sql = "SELECT COUNT(*) AS t FROM orders where uid=?";
		        PreparedStatement pstmt=conn.prepareStatement(sql);
		        pstmt.setString(1, uid);
		        rs=pstmt.executeQuery();
		        if(rs.next()) {
		        	count=rs.getInt("t");
		        }
		        System.out.println("count:" + count);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return count;
	}
}
