package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Order;
import service.OrderService;

/**
 * Servlet implementation class SaveOrderServlet
 */
@WebServlet("/SaveOrderServlet")
public class SaveOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address=request.getParameter("address");
		String receptor=request.getParameter("receptor");
		String tel=request.getParameter("tel");
		String oid=request.getParameter("oid");
		OrderService os=new OrderService();
		Order order =os.selectOrderByOid(oid);
		order.setAddress(address);
		order.setName(receptor);
		order.setTelephone(tel);
		int n = os.updateOrder(order);
		if(n==1) {
			request.getRequestDispatcher("SelectOrderByUidServlet?pageNo=1").forward(request, response);
		}else {
			request.setAttribute("infomation", "确认失败");
			request.setAttribute("url", "jsp/tip.jsp");
			request.setAttribute("second", 5);
			request.getRequestDispatcher("jsp/tip.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
