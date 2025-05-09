package mini;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Random_dish {
	Scanner input = new Scanner(System.in);
	Random rand = new Random();
	Register R;
	int order_or_not;
	Order Order_random = new Order(R);
	ArrayList<MenuCard> mList = new ArrayList<MenuCard>();
	
	void random_dish() {
if (mList.isEmpty()) {
System.out.println("No items available to select a surprise dish!");
return;
}
int randomIndex = rand.nextInt(mList.size());
MenuCard surpriseDish = mList.get(randomIndex);
System.out.println("Surprise Dish for you: " + surpriseDish.name + " - ₹" + surpriseDish.price + "\n" + "Enjoy the food. To order this dish please enter 1 :");
order_or_not = input.nextInt();
if(order_or_not == 1)
{
	Order_random.userOrders.add(surpriseDish);
// Order_random.total += surpriseDish.price;
	Order_random.finalBill();
// 	Order_random.payment();
	
}
}
}
