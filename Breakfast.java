package mini;

public class Breakfast extends MenuCard{
	public Breakfast(String name, double price) {
		super(name, price);
		}
		public void display() {
		System.out.println("Breakfast| " + name + " - ₹" + price);
		System.out.println("------------------------------------");
		}
		}