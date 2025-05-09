package mini;

import java.util.ArrayList;
import java.util.Scanner;

public class Recommendations {
	char add_dish = 'Y';
	ArrayList<MenuCard> popDishes = new ArrayList<>();
	 Scanner input = new Scanner(System.in);
	
	 public void set_recommendations(ArrayList<MenuCard> Dishes) {
	 for (MenuCard M : Dishes) {
	 	 M.display();
	 }
	
	 do {
		 System.out.println("Enter the index numbers of the popular dishes:");
		 int index = input.nextInt();
		 if (index >= 1 && index <= Dishes.size()) {
		 popDishes.add(Dishes.get(index - 1));
		 } else {
		 System.out.println("Invalid index! Skipping.");
		 }
		 System.out.println("Continue adding popular dishes? (Y/N)");
		 add_dish = input.next().charAt(0);
	 }while(add_dish == 'Y');
	
	 
	 }
	 public void display_recommendations() {
	 System.out.println("\n--- Popular Dishes ---");
	 for (int i = 0; i < popDishes.size(); i++) {
	 System.out.print((i + 1) + ". ");
	 popDishes.get(i).display();
	 }
	 }
	 public void get_recommendations(Register R) {
	 display_recommendations();
	 System.out.print("\nWould you like to order from these? (y/n): ");
	 char ch = input.next().toLowerCase().charAt(0);
	 if (ch == 'y') {
	 Order order = new Order(R);
	 char more;
	 do {
	 display_recommendations();
	 System.out.print("Enter popular dish number to order: ");
	 int dishIndex = input.nextInt();
	 if (dishIndex >= 1 && dishIndex <= popDishes.size()) {
	 order.userOrders.add(popDishes.get(dishIndex - 1));
	 System.out.println("Added: " + popDishes.get(dishIndex - 1).name);
	 } else {
	 System.out.println("Invalid choice.");
	 }
	 System.out.print("Do you want to order more? (y/n): ");
	 more = input.next().toLowerCase().charAt(0);
	 } while (more == 'y');
	 order.finalBill();
	 }
	 }
}