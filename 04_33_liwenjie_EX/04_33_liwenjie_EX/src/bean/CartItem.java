package bean;

public class CartItem {
	private Product product;
	private int count;
	private double subtotal;//不提供set方法，因为是计算获得
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		this.subtotal=product.getShop_price()*count;
		return subtotal;
	}
	@Override
	public String toString() {
		return "CartItem [product=" + product + ", count=" + count + ", subtotal=" + subtotal + "]";
	}	

}
