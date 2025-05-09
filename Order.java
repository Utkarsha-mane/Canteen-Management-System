package mini;

import java.util.ArrayList;
import java.util.Scanner;

public class Order {
	 Scanner input = new Scanner(System.in);
	 ArrayList<MenuCard> userOrders = new ArrayList<>();
	 ArrayList<MenuCard> menu = new ArrayList<>();
	 MenuItems menu_function = new MenuItems();
	 Register R;
	 double total = 0;
	 public Order() {};
	 public Order(Register R) {
	 this.R = R;
	 }
	 public void takeOrder() {
	 char more;
	 do {
	 menu_function.showMenu(menu);
	 System.out.print("Enter item number to order: ");
	 int choice = input.nextInt();
	 if (choice >= 1 && choice <= menu.size()) {
	 MenuCard selected = menu.get(choice-1);
	 userOrders.add(selected);
	 System.out.println("Added to order: " + selected.name);
	 } else {
	 System.out.println("Invalid item number.");
	 }
	 System.out.print("Do you want to order more? (y/n): ");
	 more = input.next().toLowerCase().charAt(0);
	 } while (more == 'y');
	 }
	 public void finalBill() {
	 System.out.println("\n----- Your Order -----");
	 for (MenuCard item : userOrders) {
	 item.display();
	 total += item.price;
	 }
	 System.out.println("Total amount: ₹" + total);
	 payment();
	 }
	 public void payment() {
	 System.out.println("\nChoose payment method: 1. UPI 2. Card 3. Cash");
	 int choice = input.nextInt();
	 if (choice == 1) {
	 System.out.println("1. PayTM 2. Google Pay 3. Phone Pay");
	 input.nextInt();
	 System.out.print("Enter your account pin to confirm payment: ");
	 int pin = input.nextInt();
	 if (pin == R.getPin()) {
	 System.out.println("Processing payment of ₹" + total + "...");
	 System.out.println("Payment Successful!");
	 System.out.println("Please collect your order. Thank you!");
	 } else {
	 System.out.println("Invalid pin! Payment failed.");
	 }
	 }
	
	 else {
	 System.out.println("Please pay ₹" + total + " at the counter.");
	 }
	 }
}
