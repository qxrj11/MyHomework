package action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import service.UserService;
import utils.UUIDUtils;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uid=UUIDUtils.getId();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String name=request.getParameter("name");
		String sex=request.getParameter("sex");
		String telephone=request.getParameter("telephone");
		if(sex=="1")
		{
			sex="男";
		}
		else {
			sex="女";
		}
		String d=request.getParameter("birthday");
		SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd");
		
		Date birthday=null;
		try {
			birthday=format.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	User u=new User();
	u.setUid(uid);
	u.setUsername(username);
	u.setPassword(password);
	u.setEmail(email);
	u.setName(name);
	u.setSex(sex);
	u.setTelephone(telephone);
	u.setBirthday((java.sql.Date) birthday);
	
	UserService userService=new UserService();
	int n=userService.insertUser(u);
	if(n==1) {
		
		request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
		
	}else if(n==-1) {
		
		request.setAttribute("infomation", "用户名已存在");
		request.setAttribute("url", "jsp/register.jsp");
		request.setAttribute("second", 5);
		request.getRequestDispatcher("jsp/tip.jsp").forward(request, response);
		
	}else {
		
		request.setAttribute("infomation", "注册失败");
		request.setAttribute("url", "jsp/login.jsp");
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
