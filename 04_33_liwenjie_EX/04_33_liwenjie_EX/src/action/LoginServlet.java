package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import service.UserService;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		String remember=request.getParameter("remember");
		
		if(remember!=null) {
			
			Cookie rememberCookie=new Cookie("rememberCookie",username);
			rememberCookie.setPath("/");
			rememberCookie.setMaxAge(60*60*24*7);
			response.addCookie(rememberCookie);
			
		}else {
			Cookie[] cookies=request.getCookies();
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("rememberCookie")) {
					
					Cookie rememberCookie=new Cookie("rememberCookie","");
					rememberCookie.setPath("/");
					rememberCookie.setMaxAge(0);
					response.addCookie(rememberCookie);
					
				}
			}
		}
		
		User u=new User();
		u.setUsername(username);
		u.setPassword(password);
		
		
		
		
		String autoLogin=request.getParameter("autoLogin");
		if(autoLogin!=null) {
			Cookie autoLoginCookie=new Cookie("autoLoginCookie", username+"@"+password);
			autoLoginCookie.setPath("/");
			autoLoginCookie.setMaxAge(60*60*24*7);
			response.addCookie(autoLoginCookie);
		}else {
			Cookie[] cookies=request.getCookies();
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("autoLoginCookie")) {
					Cookie autoLoginCookie=new Cookie("autoLoginCookie","");
					autoLoginCookie.setPath("/");
					autoLoginCookie.setMaxAge(0);
					response.addCookie(autoLoginCookie);
					
				}
			}
		}
		
		
		
		
		UserService userService=new UserService();
		int n=userService.selectUserByName(u);
		System.out.println("servlet:"+n);
		
		if(n==-1)
		{
			request.setAttribute("infomation", "无此用户");
			request.setAttribute("url", "jsp/index.jsp");
			request.setAttribute("second", "5");
			request.getRequestDispatcher("jsp/tip.jsp").forward(request, response);
			
		}else if(n==0) {
			
			request.setAttribute("infomation", "密码错误");
			request.setAttribute("url", "jsp/index.jsp");
			request.setAttribute("second", "5");
			request.getRequestDispatcher("jsp/tip.jsp").forward(request, response);
		
		}else {
			u.setUid(userService.selectUserByUsername(u).getUid());
			request.getSession().setAttribute("loginUser", u);
			request.getSession().setMaxInactiveInterval(60*30);
			request.getRequestDispatcher("SelectTop12Servlet").forward(request, response);
			
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
