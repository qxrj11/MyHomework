package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Page;
import bean.User;
import service.OrderService;

/**
 * Servlet implementation class SelectOrderByUidServlet
 */
@WebServlet("/SelectOrderByUidServlet")
public class SelectOrderByUidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectOrderByUidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		User user=(User)request.getSession().getAttribute("loginUser");
		OrderService os=new OrderService();
		int pageSize=3;
		Page page =os.selectOrdersByUid(user,pageNo,pageSize);
		request.setAttribute("page", page);
		request.setAttribute("orderlist", page.getList());
		request.getRequestDispatcher("jsp/order_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
