package canteen_management_system;
import java.util.*;
public class Canteen {
public static void main(String[] args) {
	 Scanner input = new Scanner(System.in);
	 MenuItems m = new MenuItems();
ArrayList<MenuCard> mList = new ArrayList<MenuCard>();
ArrayList<Staff> staff_list = new ArrayList<Staff>();
Staff admin = new Staff("admin.cumminscanteen.in" , "admin123");
	Scanner Sc = new Scanner(System.in);
	char Logout='N', cont;
	int Menu=0;
	int Staffmenu=0;
m.to_add_itemlist(mList);
System.out.println("Welcome to the Canteen!");
	System.out.println("1. For staff\n2. For customers");
	Menu = Sc.nextInt();
	switch (Menu) {
	case 1:
		do {
			boolean Access = RegisterOrLogin.access(staff_list);
			if(Access) {
				do {	
				System.out.println("1. View reports\n2. View feedback\n3. Logout");
				Staffmenu = Sc.nextInt();
				switch(Staffmenu) {
				case 1:
					//Reports code
					break;
				case 2:
					//Feedback viewing code
					break;
				case 3:
					Logout = Sc.next().charAt(0);
					Sc.nextLine();
					break;
				}
				System.out.println("View menu? (Y/N)");
				cont = Sc.next().charAt(0);
				}while(cont == 'Y');
			
				
		}
			else if(!Access) {
				
			}
		}while(Logout=='Y');
		break;
	case 2:	
Register R = new Register();
R.detail();
Order order = new Order(R);
order.menu = mList;
Recommendations pd = new Recommendations();
Feedback fd = new Feedback();
int choice;
System.out.println("Enter your choice\n1.Menucard\n2.Popular Dishes");
choice = input.nextInt();
switch(choice)
{
case 1 :
order.takeOrder();
order.finalBill();
break;
case 2 :
pd.set_recommendations(mList);
pd.get_recommendations(R);
break;
case 3 :
	fd.feedback();
	break;
default:
System.out.println("Invalid choice!");
}
}
}
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
class Recommendations
{
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
class Feedback {
	String add, Text_feedback;
	int choice;
	int foodquality, service;
	void feedback()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Thanks for taking out your time");
		System.out.println("Are the options sufficient?\n1. Yes\n2. No");
		choice = input.nextInt();
		input.nextLine();
	
		if(choice == 2)
		{
		
			System.out.println("What would you like to add?");
			add = input.nextLine();
		}
		System.out.print("\n");
		System.out.println("How's the food quality?\n1. Good\n2. Decent\n3. Bad");
		foodquality = input.nextInt();
		System.out.print("\n");
		System.out.println("How's the service?\n1. Good\n2. Decent\n3. Bad");
		service = input.nextInt();
		System.out.print("\n");
		System.out.println("Anything more you would like to share!");
		input.nextLine();
		Text_feedback = input.nextLine();
		System.out.print("\n");
	}
}
class Prize_coupons
{
}
class Profile
{
}
class RegisterOrLogin {
static boolean access(ArrayList<Staff> staff_list) {
boolean Access = false;
Scanner Sc = new Scanner(System.in);
char dispmenu = 'N';
int menu;
String email, pwd;
do {
System.out.println("1. Register\n2. Login");
menu = Sc.nextInt();
Sc.nextLine(); // Clear input buffer
switch (menu) {
case 1:
System.out.println("Enter email ID: ");
email = Sc.nextLine();
if (email.contains("@cumminscanteen.in")) {
System.out.println("Enter password: ");
pwd = Sc.nextLine();
staff_list.add(new Staff(email, pwd));
System.out.println("Successfully registered!");
} else {
System.out.println("Email does not belong to a staff member. Please use student login.");
break;
}
break;
case 2:
System.out.println("Enter email ID: ");
email = Sc.nextLine();
boolean emailFound = false;
for (Staff staff : staff_list) {
if (staff.email_id.equals(email)) {
emailFound = true;
for (int i = 0; i < 5; i++) {
System.out.println("Enter password: ");
pwd = Sc.nextLine();
if (staff.verifyPassword(pwd) == 1) {
System.out.println("Login successful.");
Access = true;
break;
} else {
System.out.println("Incorrect password. Attempts left: " + (4 - i));
}
}
if (!Access) {
System.out.println("Too many incorrect attempts. Try again later.");
}
break;
}
}
if (!emailFound) {
System.out.println("Email not found. Please register first.");
}
break;
default:
System.out.println("Invalid option. Please choose 1 or 2.");
}
if (!Access) {
System.out.println("Display menu again? (Y/N):");
dispmenu = Sc.next().charAt(0);
Sc.nextLine();
} else {
dispmenu = 'N';
}
} while (dispmenu == 'Y');
return Access;
}
}
class Staff {
String email_id;
private String password;
public Staff(String email, String pwd) {
this.email_id = email;
this.password = pwd;
}
public int verifyPassword(String pwd) {
return this.password.equals(pwd) ? 1 : 0;
}
}
abstract class MenuCard {
String name;
double price;
int order_count;
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
public void display() {
System.out.println("Breakfast| " + name + " - ₹" + price);
System.out.println("------------------------------------");
}
}
class Snacks extends MenuCard {
public Snacks(String name, double price) {
super(name, price);
}
public void display() {
System.out.println("Snack | " + name + " - ₹" + price);
System.out.println("------------------------------------");
}
}
class Beverages extends MenuCard {
public Beverages(String name, double price) {
super(name, price);
}
public void display() {
System.out.println("Beverage | " + name + " - ₹" + price);
System.out.println("------------------------------------");
}
}
class MenuItems {
//	 public void addItem(MenuCard item) {
//	 mList.add(item);
//	 }
	 public void showMenu(ArrayList<MenuCard> mList) {
	 System.out.println("------ Menu ------");
	 for (int i = 0; i < mList.size(); i++) {
	 System.out.print((i + 1) + ". ");
	 mList.get(i).display();
	 }
	 System.out.println("------------------");
	 }
//	 public MenuCard getItem(int i) {
//	 if (i >= 0 && i < mList.size())
//	 {
//	 return mList.get(i);
//	 }
//	 else
//	 {
//	 return null;
//	 }
//	 }
//	 public int getMenuSize() {
//	 return mList.size();
//	 }
	 public void to_add_itemlist(ArrayList<MenuCard> mList)
	 {
		// Breakfast
	 mList.add(new Breakfast("Poha", 30));
	 mList.add(new Breakfast("Idli", 25));
	 mList.add(new Breakfast("Upma", 20));
	 mList.add(new Breakfast("Masala Dosa", 35));
	 mList.add(new Breakfast("Uppe", 30));
	 // Snacks
	 mList.add(new Snacks("Vada Pav", 20));
	 mList.add(new Snacks("Chocolate Sandwich", 30));
	 mList.add(new Snacks("Veg Toast", 30));
	 mList.add(new Snacks("Chutney Sandwich", 25));
	 mList.add(new Snacks("Cheese Garlic Toast", 50));
	 mList.add(new Snacks("Pattice", 20));
	 // Beverages
	 mList.add(new Beverages("Cold Coffee", 30));
	 mList.add(new Beverages("Pineapple Juice", 30));
	 mList.add(new Beverages("Fresh Lime Juice", 30));
	 mList.add(new Beverages("Mausambi Juice", 30));
	 mList.add(new Beverages("Watermelon Juice", 30));
	 mList.add(new Beverages("Mango Shake", 40));
	 }
	}
class Order {
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
class Random_dish extends MenuItems
{
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
class Payment
{
}
class Offers
{
}
class Popular_dishes
{
}
