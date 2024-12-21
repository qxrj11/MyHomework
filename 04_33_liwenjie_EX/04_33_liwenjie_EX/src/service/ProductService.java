package service;

import java.util.List;

import bean.Page;
import bean.Product;
import dao.ProductDao;
public class ProductService {
	ProductDao productDao;
	public ProductService() {
		productDao=new ProductDao();
	}

	public List<Product> selectTop12() {
	List<Product> pList=productDao.selectTop12();
		return pList;
	}

	public Page selectAllProdByPage(int pageNo,int pageSize) {
		Page page=new Page();
		List <Product> pList=productDao.selectAllProdByPage(pageNo,pageSize);
		page.setList(pList);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(productDao.selectAllProdCount());
		return page;
	}

	public Product selectProdInfoById(int pid) {
		return productDao.selectProdInfoById(pid);
	}

	

}
