package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Cart;
import bean.CartItem;
import bean.Product;
import service.ProductService;


/**
 * Servlet implementation class AddCartServlet
 */
@WebServlet("/AddCartServlet")
public class AddCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pid=Integer.parseInt(request.getParameter("pid"));
		int quantity=Integer.parseInt(request.getParameter("quantity"));
		ProductService ps=new ProductService();
		Product p=ps.selectProdInfoById(pid);
		CartItem cartItem=new CartItem();
		cartItem.setProduct(p);
		cartItem.setCount(quantity);
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		if(cart==null) {
			cart=new Cart();
			request.getSession().setAttribute("cart", cart);
			
		}
		cart.addProdToCart(cartItem);
		request.setAttribute("box", cart);
		request.getRequestDispatcher("jsp/cart.jsp").forward(request, response);

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
