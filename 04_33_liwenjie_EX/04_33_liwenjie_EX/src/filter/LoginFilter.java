package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.User;
import service.UserService;

public class LoginFilter implements Filter {
	private String excludedPaths; 

	private String [] excludedPathArray;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("doFilter销毁了");		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
        System.out.println("doFilter执行了");
        HttpServletRequest req =  (HttpServletRequest) arg0;
        if (isFilterExcludeRequest(req)) {
        	arg2.doFilter(arg0, arg1); 
        	return;
        }
        
     
        Cookie autoCookie=null;
        Cookie[] cookies=req.getCookies();
        for (Cookie cookie : cookies) {
     	   if(cookie.getName().equals("autoLoginCookie")) {
     		   autoCookie=cookie;
     	   }
        }
        
        if(autoCookie!=null) {
     	   String[] s=autoCookie.getValue().split("@");
     	   User u=new User();
     	   u.setUsername(s[0]);
     	   u.setPassword(s[1]);
     	   UserService us=new UserService();
     	   int n=us.selectUserByName(u);
     	   if(n==1) {
     		  u.setUid(us.selectUserByUsername(u).getUid());
     		   req.getSession().setAttribute("loginUser", u);
     		   req.getSession().setMaxInactiveInterval(30*60);
     		   
     	   }
     	   
        }
        
        HttpSession session = req.getSession();
        User loginUser= (User) session.getAttribute("loginUser");
        //判读是否登录成功
        if (loginUser== null) {
			arg1.setContentType("text/html;charset=UTF-8");
        	arg1.getWriter().write("你没有登录，请登录！<a href="+"\"javascript:top.location.href='jsp/login.jsp';\">登录</a>");		
		}else {
			 arg2.doFilter(arg0, arg1);    //放行
		}       
        System.out.println("执行完返回到客户端");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("登录过滤器初始化");
        excludedPaths = arg0.getInitParameter("excludedPaths");
        if(excludedPaths!=null){
            excludedPathArray = excludedPaths.split(",");
        }
	}
private boolean isFilterExcludeRequest(HttpServletRequest request) {
		        if(null != excludedPathArray && excludedPathArray.length > 0) {
		            String url = request.getRequestURI();
		            for (String ecludedUrl : excludedPathArray) {
		            	System.out.println("ecludedUrl"+ecludedUrl);
		                if (ecludedUrl.startsWith("*.")) {
		                    // 如果配置的是后缀匹配, 则把前面的*号干掉，然后用endWith来判断
		                    if(url.endsWith(ecludedUrl.substring(1))){
		                        return true;
		                    }
		                } else if (ecludedUrl.endsWith("/*")) {
		                    if(!ecludedUrl.startsWith("/")) {
		                        // 前缀匹配，必须要是/开头
		                        ecludedUrl = "/" + ecludedUrl;
		                    }
		                    // 如果配置是前缀匹配, 则把最后的*号干掉，然后startWith来判断
		                    String prffixStr = request.getContextPath() + ecludedUrl.substring(0, ecludedUrl.length() - 1);
		                    if(url.startsWith(prffixStr)) {
		                        return true;
		                    }
		                } else {
		                    // 如果不是前缀匹配也不是后缀匹配,那就是全路径匹配
		                    if(!ecludedUrl.startsWith("/")) {
		                        // 全路径匹配，也必须要是/开头
		                        ecludedUrl = "/" + ecludedUrl;
		                    }
		                    String targetUrl = request.getContextPath() + ecludedUrl;
		                    if(url.equals(targetUrl)) {
		                        return true;
		                    }
		                }
		            }
		        }
		        return false;
		    }


}
