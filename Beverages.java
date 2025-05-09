package mini;

public class Beverages extends MenuCard {
	public Beverages(String name, double price) {
		super(name, price);
		}
		public void display() {
		System.out.println("Beverage | " + name + " - ₹" + price);
		System.out.println("------------------------------------");
		}
		}
