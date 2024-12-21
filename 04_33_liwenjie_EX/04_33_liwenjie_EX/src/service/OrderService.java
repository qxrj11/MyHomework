package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bean.Order;
import bean.OrderItem;
import bean.Page;
import bean.User;
import dao.OrderDao;
import utils.DBUtil;

public class OrderService {
	OrderDao orderDao;
	
	public OrderService() {
		orderDao=new OrderDao();
	}
	public void saveOrder(Order order) {
		Connection conn=DBUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			orderDao.saveOrder(conn,order);
			for (OrderItem oItem : order.getList()) {
				orderDao.saveOrderItem(conn,oItem);
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public Order selectOrderByOid(String oid) {
		return orderDao.selectOrderByOid(oid);
	}
	
	public int updateOrder(Order order) {
		return orderDao.updateOrder(order);
	}
	
	public Page selectOrdersByUid(User user, int pageNo, int pageSize) {
		List<Order> orderList=orderDao.selectOrdersByUid(user,pageNo,pageSize);
		Page page=new Page();
		page.setList(orderList);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(orderDao.selectOrderCount(user.getUid()));
		return page;
	}

}
