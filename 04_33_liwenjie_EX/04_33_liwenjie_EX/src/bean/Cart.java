package bean;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<Integer,CartItem> map=new LinkedHashMap<Integer,CartItem>();
	private double total;
	
	public Map<Integer, CartItem> getMap() {
		return map;
	}

	public double getTotal() {
		total=0;
		for(Map.Entry<Integer, CartItem> entry:map.entrySet()) {
			CartItem cartItem=entry.getValue();
			total+=cartItem.getSubtotal();
		}
		return total;
	}
	
	public Collection<CartItem> getCartItems(){
		System.out.println("000"+map.values());
		return map.values();
	}
	
	//添加商品到购物车方法
	public void addProdToCart(CartItem item) {
		//获取商品的id
		int pid = item.getProduct().getPid();
		
		if(map.containsKey(pid)) {//说明购物车中没有这个商品
			CartItem oldItem=map.get(pid);
			oldItem.setCount(oldItem.getCount()+item.getCount());
		}else {
			map.put(pid, item);
		}
		//更改购物车中总得商品总价
		total+=item.getSubtotal();
	}

	//从购物车中移除商品项
	public void removeProdFromCart(int id) {
		CartItem cartItem=map.remove(id);
		total-=cartItem.getSubtotal();
	}
	
	//清空购物车
	public void clearCart() {
		map.clear();
		total=0;
	}

	@Override
	public String toString() {
		return "Cart [map=" + map + ", total=" + total + "]";
	}	

}
