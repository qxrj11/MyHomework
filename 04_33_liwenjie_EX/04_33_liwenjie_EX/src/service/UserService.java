package service;

import bean.User;
import dao.UserDao;
                      
public class UserService {
	
	UserDao userDao;
	public UserService() {
		userDao=new UserDao();
	}

	public int selectUserByName(User u) {
		User findu=userDao.selectUserByName(u);
		if(findu==null)
			return -1;
		else if(findu.getPassword().equals(u.getPassword()))
			return 1;
		else
			return 0;
		
	}

	public int insertUser(User u) {
		
		User findu=userDao.selectUserByName(u);
		if(findu!=null)
			return -1;
		else
			return userDao.insertUser(u);

	}
	public User selectUserByUsername(User u) {
		return userDao.selectUserByName(u);
		
	}
	
	
	public int updateUser(User u) {
		return userDao.updateUser(u);
	}

	
}
