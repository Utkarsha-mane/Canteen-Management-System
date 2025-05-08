package canteen_management_system;
import java.util.*;
public class Canteen {
   public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       MenuItems items = new MenuItems();
       
       items.to_add_itemlist();
       System.out.println("Welcome to the Canteen!");
       System.out.println("Register to continue:");
       Register R = new Register();
       R.detail();
       Order order = new Order(R);
       order.menu = items;
       Recommendations pd = new Recommendations(items);
       Feedback fd = new Feedback();
       Random_dish rd = new Random_dish(items.mList, R, order); 
       int choice;
       System.out.println("Enter your choice\n1. Menucard\n2. Popular Dishes\n3. Feedback\n4. Get Surprise Dish");
       choice = input.nextInt();
       switch(choice)
       {
       case 1 :
           order.takeOrder();
           order.finalBill();
           break;
       case 2 :
           pd.set_recommendations();
           pd.get_recommendations(R);
           break;
       case 3 :
    	   fd.feedback();
           break;
       case 4 : 
    	   System.out.println("Are you confused in choosing a dish? Let's see what surprise you get here :) please enter [1234] for getting a surprise");
    	    input.nextLine();  
    	    int surprise = input.nextInt();
    	    if (surprise == 1234) {
    	        rd.random_dish();  
    	    }
    	    break;
       default:
           System.out.println("Invalid choice!");
       }      
   }
}
class Login {
}
class Register {
   Scanner input = new Scanner(System.in);
   int user_pin;
   void detail() {
       System.out.print("Enter your account pin: ");
       user_pin = input.nextInt();
   }
   int getPin() {
       return user_pin;
   }
}
abstract class MenuCard {
   String name;
   double price;
   int count = 0;
   public MenuCard(String name, double price) {
       this.name = name;
       this.price = price;
   }
   public abstract void display();
}
class Breakfast extends MenuCard {
   public Breakfast(String name, double price) {
       super(name, price);
   }
   @Override
   public void display() {
       System.out.println("Breakfast | " + name + " - ₹" + price);
   }
}
class Snacks extends MenuCard {
   public Snacks(String name, double price) {
       super(name, price);
   }
   @Override
   public void display() {
       System.out.println("Snack | " + name + " - ₹" + price);
   }
}
class Beverages extends MenuCard {
   public Beverages(String name, double price) {
       super(name, price);
   }
   @Override
   public void display() {
       System.out.println("Beverage | " + name + " - ₹" + price);
   }
}
class MenuItems {
   ArrayList<MenuCard> mList = new ArrayList<>();
   public void addItem(MenuCard item) {
       mList.add(item);
   }
   
   public void to_add_itemlist()
   {
	// Breakfast
       addItem(new Breakfast("Poha", 30));
       addItem(new Breakfast("Idli", 25));
       addItem(new Breakfast("Upma", 20));
       addItem(new Breakfast("Masala Dosa", 35));
       addItem(new Breakfast("Uppe", 30));
       // Snacks
       addItem(new Snacks("Vada Pav", 20));
       addItem(new Snacks("Chocolate Sandwich", 30));
       addItem(new Snacks("Veg Toast", 30));
       addItem(new Snacks("Chutney Sandwich", 25));
       addItem(new Snacks("Cheese Garlic Toast", 50));
       addItem(new Snacks("Pattice", 20));
       // Beverages
       addItem(new Beverages("Cold Coffee", 30));
       addItem(new Beverages("Pineapple Juice", 30));
       addItem(new Beverages("Fresh Lime Juice", 30));
       addItem(new Beverages("Mausambi Juice", 30));
       addItem(new Beverages("Watermelon Juice", 30));
       addItem(new Beverages("Mango Shake", 40));
   }
   public void showMenu() {
       System.out.println("------ Menu ------");
       for (int i = 0; i < mList.size(); i++) {
           System.out.print((i + 1) + ". ");
           mList.get(i).display();
       }
       System.out.println("------------------");
   }
   public MenuCard getItem(int i) {
       if (i >= 0 && i < mList.size())
       {
           return mList.get(i);
       }
       else
       {
           return null;
       }
   }
   public int getMenuSize() {
       return mList.size();
   }
}
class Order {
   Scanner input = new Scanner(System.in);
   ArrayList<MenuCard> userOrders = new ArrayList<>();
   MenuItems menu = new MenuItems();
   Register R;
   double total = 0;
   public Order() {};
   public Order(Register R) {
       this.R = R;
   }
   public void takeOrder() {
       char more;
       do {
           menu.showMenu();
           System.out.print("Enter item number to order: ");
           int choice = input.nextInt();
           if (choice >= 1 && choice <= menu.getMenuSize()) {
               MenuCard selected = menu.getItem(choice - 1);
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
           System.out.print("Enter your pin to confirm payment: ");
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

class Recommendations {
	ArrayList<MenuCard> popDishes = new ArrayList<>();
   Scanner input = new Scanner(System.in);
   MenuItems sharedMenu;
   public Recommendations(MenuItems menu) {
       this.sharedMenu = menu;
   }
   public void set_recommendations() {
       sharedMenu.showMenu();
       System.out.print("Enter the number of popular dishes: ");
       int p_dish_count = input.nextInt();
       System.out.println("Enter the index numbers of the popular dishes:");
       for (int i = 0; i < p_dish_count; i++) {
           int index = input.nextInt();
           if (index >= 1 && index <= sharedMenu.getMenuSize()) {
               popDishes.add(sharedMenu.getItem(index - 1));
           } else {
               System.out.println("Invalid index! Skipping.");
           }
       }
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
class Feedback {
	String choice, add, Text_feedback;
	int foodquality, service;
	void feedback()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Thanks for taking out your time");
		System.out.println("Are the options sufficient?\n1. Yes\n2. No");
		choice = input.nextLine();
		if(choice.toLowerCase().equals("no"))
		{
			System.out.println("What would you like to add?");
			add = input.nextLine();
		}
		System.out.print("\n");
		System.out.println("How's the food quality?\n1. Good\n2. Decent\n 3. Bad");
		foodquality = input.nextInt();
		System.out.print("\n");
		System.out.println("How's the service?\\n1. Good\\n2. Decent\\n 3. Bad");
		service = input.nextInt();
		System.out.print("\n");
		System.out.println("Anything more you would like to share!");
		input.nextLine();
		Text_feedback = input.nextLine();
		System.out.print("\n");
	}
}
class Random_dish extends MenuItems
{
	Scanner input = new Scanner(System.in);
	Random rand = new Random();
	Register R;
	int order_or_not;
	Order Order_random;

	public Random_dish(ArrayList<MenuCard> menuList, Register R, Order order) {
        this.mList = menuList;
        this.R = R;
        this.Order_random = order; 
    }

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
//            Order_random.total += surpriseDish.price;
        	Order_random.finalBill();
//        	Order_random.payment();
        	
        }
    }

}
class Offers {
	  
}
class popular_dishes {
  
}
class Prize_coupons {
}
class Profile {
}


