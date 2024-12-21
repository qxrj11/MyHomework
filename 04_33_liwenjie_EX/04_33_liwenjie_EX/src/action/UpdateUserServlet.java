package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import service.UserService;



/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String username=request.getParameter("username");
		String pwd=request.getParameter("password");
		String sex=request.getParameter("sex");
		String telephone=request.getParameter("telephone");
		User u=new User();
		u.setUsername(username);
		u.setPassword(pwd);
		u.setSex(sex);
		u.setTelephone(telephone);
		UserService us=new UserService();
		int n=us.updateUser(u);
		if(n==1) {
			request.setAttribute("information", "修改成功");
		}else {
			request.setAttribute("information", "修改失败");
		}
		request.setAttribute("url", "SelectTop12Servlet");
		request.setAttribute("second", 5);
		request.getRequestDispatcher("jsp/tip.jsp").forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
