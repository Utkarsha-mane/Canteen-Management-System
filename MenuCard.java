package mini;

public abstract class MenuCard {
	String name;
	double price;
	int order_count;
	public MenuCard(String name, double price) {
	this.name = name;
	this.price = price;
	}
	public abstract void display();
	}
