package pack;

public class FirstClass {
	
	private int total;
	private int subtotal;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	public FirstClass(int total, int subtotal) {
		super();
		this.total = total;
		this.subtotal = subtotal;
	}
	@Override
	public String toString() {
		return "FirstClass [total=" + total + ", subtotal=" + subtotal + "]";
	}
	
	

}
