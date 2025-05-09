package mini;

public class Snacks extends MenuCard {
	public Snacks(String name, double price) {
		super(name, price);
		}
		public void display() {
		System.out.println("Snack | " + name + " - ₹" + price);
		System.out.println("------------------------------------");
		}
		}
