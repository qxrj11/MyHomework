package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Page;
import service.ProductService;

/**
 * Servlet implementation class SelectAllProdByPageServlet
 */
@WebServlet("/SelectAllProdByPageServlet")
public class SelectAllProdByPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectAllProdByPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		ProductService pService=new ProductService();
		int pageSize=12;
		Page page=pService.selectAllProdByPage((pageNo-1)*pageSize,pageSize);
		request.setAttribute("page",page);
		request.setAttribute("pList",page.getList());
		request.getRequestDispatcher("jsp/product_list.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
