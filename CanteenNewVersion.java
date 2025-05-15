package canteen_management_system;
import java.util.*;

public class Canteen {
	public static void main(String[] args) throws InterruptedException {
		Scanner input = new Scanner(System.in);
		
		// All required ArrayLists 
		ArrayList<MenuCard> mList = new ArrayList<MenuCard>();      // For dishes
		ArrayList<Staff> staff_list = new ArrayList<Staff>();       // For staff members
		ArrayList<Profile> customer = new ArrayList<>(); 
				
		// Object creation for all used classes
		MenuItems m = new MenuItems(); //Used for displaying and adding items to menu
		Profile R = new Profile(); 
		Order order = new Order(R); 
		Recommendations pd = new Recommendations();
		Feedback fd = new Feedback();
		Prize_coupons pc = new Prize_coupons(R);
		DishOfTheWeek dw = new DishOfTheWeek();
		Staff admin = new Staff("admin.cumminscanteen.in", "admin123"); //To prevent out of bounds exception
		Random_dish Rd = new Random_dish(R, mList);
		
		//Required variable declaration
		int choice, Menu = 0, Staffmenu = 0;
		String continue_ = "yes";
		char Logout = 'N', cont, cont2;
		
		m.to_add_itemlist(mList); // Method call to add elements to the ArrayList mList(dishes)
		// Main menu
		do {
			System.out.println("Welcome to the Canteen!");
			System.out.println("1. For staff\n2. For customers\n3. Explore");
			try {
				Menu = input.nextInt();
				}
				catch(InputMismatchException e)
				{
					System.out.println("Select the options given in the menu!");
					input.nextLine();

				}

			switch (Menu) {
			case 1:
				// For Staff (Canteen authorities)
				Recommendations St = new Recommendations();
				do {
					boolean Access = RegisterOrLogin.access(staff_list);
					if (Access) {
						do {
							System.out.println("1. View feedback\n2. Set recommendations\n3. Remove recommendations\n4. Logout");
							Staffmenu = input.nextInt();
							switch (Staffmenu) {
								case 1:
									Staff.showFeedback();
									break;
								case 2:
									St.set_recommendations(mList);
									break;
								case 3:
									St.remove_recommendations();
									break;
								case 4:
									Access = false;
									break;
							}
						} while (Access == true);
					}
				} while (Logout == 'Y');
				break;

				
			case 2:
				// For Customers with set profiles (primarily for students, professors and other authorities)
				Profile loggedInUser = null;
				while (loggedInUser == null) {
					System.out.println("1. Login\n2. Register\n3. Back");
					int authChoice = input.nextInt();
					switch (authChoice) {
						case 1:
							loggedInUser = R.login(customer, input);
							break;
						case 2:
							loggedInUser = R.register(customer, input);
							break;
						case 3:
							break;
						default:
							System.out.println("Invalid choice");
					}
					if (authChoice == 3) break;
				}

				if (loggedInUser != null) {
					Order userOrder = new Order(loggedInUser);
					Prize_coupons userCoupons = new Prize_coupons(loggedInUser);
					Random_dish randomDish = new Random_dish(loggedInUser, mList);
					do {
						System.out.println("Enter your choice:\n1. Menucard\n2. Offers\n3. Dish of the week\n4. Get Recommendation\n5. Random Dish\n6. Redeem Dish\n7. Feedback\n8. Logout");
						choice = input.nextInt();
						switch (choice) {
							case 1:
								userOrder.takeOrder(loggedInUser, mList, false);
								break;
							case 2:
								userOrder.comboOrder();
								break;
							case 3:
//								dw.menu = mList;
								dw.Timer(mList);
								break;
							case 4:
								pd.get_recommendations(loggedInUser);
								break;
							case 5:
								randomDish.random_dish(false);
								break;
							case 6:
								userCoupons.redeem_points(loggedInUser, mList, pd);
								break;
							case 7:
								Staff.addFeedback();
								break;
							case 8:
								System.out.println("Logged out successfully.");
								loggedInUser = null;
								break;
							default:
								System.out.println("Invalid choice!");
						}
						if (choice == 8) break;
						System.out.println("Show menu again? (yes/no)");
						continue_ = input.next();
					} while (continue_.equalsIgnoreCase("yes"));
				}
				break;
				
			case 3 :
				// For a new person to explore without creating an account
				System.out.println("You can pay only in cash!");
				do
				{
					System.out.println("Enter your choice\n1. Menucard\n2. Offers\n3. Dish of the week\n4. Get Recommendation\n5. I'm so confused..Get me a random dish\n6. Redeem free dish\n7. Feedback\n8. Back");
					choice = input.nextInt();
					switch (choice) {
						case 1:
							order.takeOrder(R, mList, false);
							break;
						case 2: 
							order.comboOrder();
							break;
						case 3:
//							dw.menu = mList;
							dw.Timer(mList);	
							break;
						case 4:
							pd.get_recommendations(R);
							break;
						case 5:
							Rd.random_dish(false);
							break;
						case 6:
//							pc.redeem_points();        // This feature will not work as the person doesn't have an user account
							System.out.println("Sorry, you can't use this feature as you don't have an user account.");
							break;
						case 7:
							Staff.addFeedback();
							break;
						case 8:
							break;
						default:
							System.out.println("Invalid choice!");
					}
					System.out.println("Show menu?(yes/no)");
					continue_ = input.next();
				}while(continue_.toLowerCase().equals("yes"));
				break;
		}
			System.out.println("Continue? (Y/N)");
			cont2 = input.next().charAt(0);
		}while(cont2 == 'Y');
		
		input.close();
	}
}

// Class for customer profiles (user accounts)
class Profile
{
	String name, email;
	private String password;
	int user_pin, dish_count = 0, user_points = 0;
	
	// To store the status of points redemption history of user --> used in 'Prize_coupons' class
	ArrayList<String> reward_history = new ArrayList<>();
	
	// Constructors to initialize the attributes of Profile object (information attributes linked to a customer profile)
	Profile(){};
	Profile(String name, String email, String password, int pin)
	{
		this.name = name;
		this.email = email;
		this.password = password;
		user_pin = pin;
	}
	
	// Registration
	public Profile register(ArrayList<Profile> customerList, Scanner input)
	{
		System.out.println("Enter Name:");
		String name = input.next();
		System.out.println("Enter Email:");
		String email = input.next();

		// Check if already exists
		for (Profile p : customerList) {
			if (p.email.equalsIgnoreCase(email)) {
				System.out.println("Email already registered! Try logging in.");
				return null;
			}
		}

		System.out.println("Create Password:");
		String password = input.next();
		
		int pin;
		while (true) {
		    try {
		        System.out.println("Create a 4-digit PIN:");
		        pin = input.nextInt();
		        if (String.valueOf(pin).length() != 4) {
		            throw new InvalidPinException("PIN must be exactly 4 digits.");
		        }
		        break;
		    } catch (InputMismatchException e) {
		        System.out.println("Please enter numeric value.");
		        input.nextLine(); 
		    } catch (InvalidPinException e) {
		        System.out.println(e.getMessage());
		    }
		}

		Profile newProfile = new Profile(name, email, password, pin);
		customerList.add(newProfile);
		System.out.println("Registration successful!");
		return newProfile;
	}


	// Login
	public Profile login(ArrayList<Profile> customerList, Scanner input) {
	    try {
	        System.out.print("Enter Email: ");
	        String email = input.next();
	        System.out.print("Enter Pin: ");
	        int pin = input.nextInt();

	        for (Profile p : customerList) {
	            if (p.email.equalsIgnoreCase(email) && p.getPin(p) == pin) {
	                System.out.println("Login Successful!");
	                return p;
	            }
	        }

	        throw new InvalidCredentialsException("Invalid email or pin. Please try again.");

	    } catch (InvalidCredentialsException e) {
	        System.out.println(e.getMessage());
	    } catch (InputMismatchException e) {
	        System.out.println("Invalid input. Please enter numeric pin.");
	        input.nextLine();
	    }
	    return null;
	}

	
	// Logout 
	
	
	// 
	int getPin(Profile r)
	{
		return r.user_pin;
	}
}

class InvalidCredentialsException extends Exception
{
	public InvalidCredentialsException(String message)
	{
		super(message);
	}
}

// Class for the implementation of ordering items from menucard and payment
class Order {
	Scanner input = new Scanner(System.in);
	
	// ArrayList of ordered items --> used in bill
	ArrayList<MenuCard> userOrders = new ArrayList<>();
	
	// Object of MenuItems class to access Menucard
	MenuItems menu_function = new MenuItems();
	
	Profile R;
	
	// Initial payable amount is 0.
	double total = 0;

	// default constructor  
	public Order() {
	};

	// parameterized constructor to access certain profile of user and add order on that account
	public Order(Profile R) {
		this.R = R;
	}

	// Method to take order by displaying the menucard
	public void takeOrder(Profile R, ArrayList<MenuCard> menu, boolean isfree) {
		char more;
		
		if(isfree)
		{
			menu_function.showMenu(menu);
			System.out.print("Enter item number to order: ");
			int choice = input.nextInt();
			if (choice >= 1 && choice <= menu.size()) {
				MenuCard selected = menu.get(choice - 1);
				userOrders.add(selected);
				System.out.println("Enjoy your dish!");
			}
			else {
				System.out.println("Invalid item number.");
		}
			
			
		}
			else
			{
				do {
					
//					menu_function.to_add_itemlist(menu);
					menu_function.showMenu(menu);
					
					System.out.print("Enter item number to order: ");
					int choice = input.nextInt();
					if (choice >= 1 && choice <= menu.size()) {
						MenuCard selected = menu.get(choice - 1);
						userOrders.add(selected);
						R.user_points++;
						System.out.println("User points = " + R.user_points);
						menu.get(choice - 1).order_count++;
						System.out.println("Dish count = " + selected.order_count);
						
						System.out.println("Added to order: " + selected.name);
					} else {
						System.out.println("Invalid item number.");
					}
					System.out.print("Do you want to order more? (y/n): ");
					more = input.next().toLowerCase().charAt(0);
				} while (more == 'y');
				finalBill();
			}
		
		userOrders.clear();
	}

	
	public void comboOrder() {
		ArrayList<MenuCard> combos = new ArrayList<>();
		Offers off = new Offers();
		Combos com = new Combos();
		off.addOffers(combos);
		com.display(combos);
		char more;
		do {
		System.out.print("Enter item number to order: ");
		int choice = input.nextInt();
		if (choice >= 1 && choice <= combos.size()) {
			MenuCard selected = combos.get(choice - 1);
			userOrders.add(selected);
			System.out.println("Added to order: " + selected.name);
		} else {
			System.out.println("Invalid item number.");
		}
		System.out.print("Do you want to order more? (y/n): ");
		more = input.next().toLowerCase().charAt(0);
	} while (more == 'y');
	finalBill();
	userOrders.clear();
	}


	
	public void finalBill() {
		System.out.println("\n----- Your Order -----");
		for (MenuCard item : userOrders) {
			item.display();
			total += item.price;
		}
		System.out.println("Total amount: ₹" + total);
		payment();
		total = 0;
	}

	public void payment() {
		boolean pay_confirm = false;
		do {
			System.out.println("\nChoose payment method: 1. UPI 2. Card 3. Cash");
			int choice = input.nextInt();
			if (choice == 1) {
				int choice2;
				System.out.println("1. PayTM 2. Google Pay 3. Phone Pay");
				choice2 = input.nextInt();
				if (choice2 <= 3 && choice2 >= 1) {
					System.out.print("Enter your pin to confirm payment: ");
					int pin = input.nextInt();
					if (pin == R.getPin(R)) {
						System.out.println("Processing payment of ₹" + total + "...");
						total = 0;
						System.out.println("Payment Successful!");
						System.out.println("Please collect your order. Thank you!");
						pay_confirm = true;
					} else {
						System.out.println("Invalid pin! Payment failed.");
					}
				}
				else {
					System.out.println("Please enter valid index!");
				}
			}

			else if (choice == 2 || choice == 3){
				System.out.println("Please pay ₹" + total + " at the counter.");
				pay_confirm = true;
			}
			else {
				System.out.print("Please enter valid index!");
			}
		}while(pay_confirm == false);

	}
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
	static ArrayList<Feedback> fdlist = new ArrayList<>();

	public Staff(String email, String pwd) {
		this.email_id = email;
		this.password = pwd;
	}

	public int verifyPassword(String pwd) {
		return this.password.equals(pwd) ? 1 : 0;
	}
	
	public static void addFeedback() {
		Feedback f = new Feedback();
		f.feedback();
		fdlist.add(f);
	}
	
	public static void showFeedback() {
		if (fdlist.isEmpty()) {
			System.out.println("No feedback available.");
		} else {
			for (Feedback f : fdlist) {
				System.out.println("---------------------------------");
				System.out.println("Food quality: " + f.foodquality);
				System.out.println("Cleanliness: " + f.cleanliness);
				System.out.println("Service: " + f.service);
				System.out.println("Were the options sufficient: " + f.options);
				System.out.println("Suggestion to add: " + f.foodsuggestion);
				System.out.println("Overall experience: " + f.experience);
				System.out.println("---------------------------------\n");

			}
		}
	}

}


abstract class MenuCard {
	String name;
	double price;
	int order_count;
	double savings;
	int buycount = 0;


    public MenuCard() {
		//Default constructor
	}

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
	public void showMenu(ArrayList<MenuCard> mList) {
		System.out.println("------ Menu ------");
		for (int i = 0; i < mList.size(); i++) {
			System.out.print((i + 1) + ". ");
			mList.get(i).display();
		}
		System.out.println("------------------");
	}

	public void to_add_itemlist(ArrayList<MenuCard> mList) {
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

//
class Prize_coupons extends DishOfTheWeek{
    Profile user;
    MenuItems m = new MenuItems();
    Order O_free = new Order();
    Scanner sc = new Scanner(System.in);
    ArrayList<MenuCard> free_menu = new ArrayList<MenuCard>(); 
    Recommendations R_free = new Recommendations();
    Random_dish Rn_free = new Random_dish(user, free_menu);
    String continue_ , recom;
    Prize_coupons(Profile user) {
        this.user = user;
    }
    

    void redeem_points(Profile R, ArrayList<MenuCard> menu, Recommendations pd) throws InterruptedException {
    	free_menu.add(menu.get(1));
    	free_menu.add(menu.get(2));
    	free_menu.add(menu.get(5));
    	free_menu.add(menu.get(8));
    	free_menu.add(menu.get(10));
    	free_menu.add(menu.get(15));
        System.out.println("🎁 Reward Zone: Hello " + user.name + "!");

        if (R.user_points >= 3) {
            System.out.println("You currently have " + R.user_points + " reward points!");
            System.out.println("✨ You can redeem a FREE dish now! 🎉");

            System.out.println("\nHow would you like to choose your free dish?");
            System.out.println("1. From the menu 🍽");
            System.out.println("2. Surprise Me! 🎲");
            System.out.print("Enter your choice (1/2): ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    O_free.takeOrder(user, free_menu, true);
                    break;
                case 2:
                	Rn_free.random_dish(true);
                    break;
                default:
                    System.out.println("⚠ Invalid choice. Please try again next time.");
                    return;
            }

            System.out.print("\nConfirm redemption of 3 points for this dish? (yes/no): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                R.user_points -= 3;
                System.out.println("✅ Redemption successful! Enjoy your free dish, " + user.name + "!");
                R.reward_history.add("Redeemed 1 free dish using 3 points.");
            } else {
                System.out.println("* Redemption cancelled. Points not deducted.");
            }
        } else {
            System.out.println("Sorry! You need at least 3 reward points to get a free dish.");
            System.out.println("Tip: Order more dishes to earn rewards faster!");
            System.out.println("Would you like to order ?(yes/no)");
            continue_ = sc.nextLine();
           
            if(continue_.toLowerCase().equals("yes"))
            {
            	 System.out.println("Do you need any recommendations?");
                 recom = sc.nextLine();
            	if(recom.toLowerCase().equals("yes"))
            	{
            		R_free.get_recommendations(user);
            	}
            	else
            	{
            		O_free.takeOrder(user, menu, false);
            	}
            }
            else
            {
            	System.out.println("Thank you !");
            }
        }
        System.out.println();
    }
}

class Random_dish {
    Scanner input = new Scanner(System.in);
    Random rand = new Random();
    Profile R;
    int order_or_not;
    Order Order_random;
    ArrayList<MenuCard> mList;

    // Constructor to pass Profile and Menu list
    Random_dish(Profile R, ArrayList<MenuCard> menuList) {
        this.R = R;
        this.Order_random = new Order(R);
        this.mList = menuList;
    }

    void Timer() {
        System.out.print("Choosing your surprise dish");
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(700);
                System.out.print(" ...");
            }
            System.out.println();
        } catch (InterruptedException e) {
            System.out.println("Oops! Something went wrong during the timer");
        }
    }

    void random_dish(boolean isfree) {
        if (mList == null || mList.isEmpty()) {
            System.out.println("* No items available to select a surprise dish!");
            return;
        }

        Timer();

        int randomIndex = rand.nextInt(mList.size());
        MenuCard surpriseDish = mList.get(randomIndex);

        System.out.println("\n🎁 Surprise Dish Just for You");
        System.out.println("👉 " + surpriseDish.name + " - ₹" + surpriseDish.price);
        System.out.println("😋 Enjoy the food vibes!");
        
        
        System.out.print("Would you like to order this dish? Enter 1 for YES: ");
        try {
            order_or_not = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter an *Integer*.");
            input.nextLine(); 
            order_or_not = 0;
        }

        
        if (order_or_not == 1) {
            Order_random.userOrders.add(surpriseDish);
            System.out.println("✅ Dish added to your order! Finalizing bill...");
            if(!isfree)
            Order_random.finalBill();
        } else {
            System.out.println("No worries! Maybe next time. 😊");
        }
    }
}


class Recommendations {
	char add_dish = 'Y';
	static ArrayList<MenuCard> popDishes = new ArrayList<>();
	Scanner input = new Scanner(System.in);
	

	public void set_recommendations(ArrayList<MenuCard> Dishes) {
		int count=0;
		for (MenuCard M : Dishes) {
			System.out.print((count+1) + ". ");
			M.display();
			count++;
		}

		do {
			System.out.println("Enter the index numbers of the Recommendations:");
			int index = input.nextInt();
			if (index >= 1 && index <= Dishes.size()) {
				popDishes.add(Dishes.get(index - 1));
			} else {
				System.out.println("Invalid index! Skipping.");
			}
			System.out.println("Continue adding recommendations? (Y/N)");
			add_dish = input.next().charAt(0);
		} while (add_dish == 'Y');

	}
	
	public void remove_recommendations() {
		int count1=0;
		for (MenuCard M : popDishes) {
			System.out.print((count1+1) + ". ");
			M.display();
			count1++;
		}

		do {
			System.out.println("Enter the index numbers of the Recommendations:");
			int index = input.nextInt();
			if (index >= 1 && index <= popDishes.size()) {
				popDishes.remove(popDishes.get(index - 1));
			} else {
				System.out.println("Invalid index! Skipping.");
			}
			System.out.println("Continue removing recommendations? (Y/N)");
			add_dish = input.next().charAt(0);
		} while (add_dish == 'Y');

	}
	

	public void display_recommendations() {
		System.out.println("\n--- Recommendations ---");
		for (int i = 0; i < popDishes.size(); i++) {
			System.out.print((i + 1) + ". ");
			popDishes.get(i).display();
		}
	}

	public void get_recommendations(Profile R) {
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
	
	Scanner input = new Scanner(System.in);
	String foodquality, cleanliness, service, experience, options, foodsuggestion="None";
	
	public void feedback() {
		System.out.println("Rate Food Quality (Good/Average/Poor): ");
		foodquality = input.nextLine();
		System.out.println("Rate Cleanliness (Good/Average/Poor): ");
		cleanliness = input.nextLine();
		System.out.println("Rate Service (Good/Average/Poor): ");
		service = input.nextLine();
		System.out.println("Were the options sufficient? (Yes/No): ");
		options = input.nextLine();
		if(options.equals("No")) {
			System.out.println("What would you like to add? ");
			foodsuggestion = input.nextLine();
		}
		System.out.println("How was your overall experience?: ");
		experience = input.nextLine();
		System.out.println("Thanks for your feedback!");
	}
}






class Offers {
	
void addOffers(ArrayList<MenuCard> offers) {
	offers.add(new Combos("South Indian Sunrise - Idli + Upma + Hot Coffee", 60, 5 ));
	offers.add(new Combos("Mumbai Snack Box - Vada Paav + Chutney Sandwhich", 40, 5));
	offers.add(new Combos("Summer Juices Combo - Watermelon juice + Pineapple juice", 50, 10));
  
}



}

class Combos extends MenuCard{
	public Combos() {
		super();
		//Default constructor
	}
    public Combos(String name, double price, double savings) {
        super(name, price);
        this.savings = savings;
    }
      public void display(ArrayList<MenuCard> offers) {
    	  int count=0;
    	  for(MenuCard M : offers) {
    		  System.out.println((count+1) + ". Combo | " + M.name + " - ₹" + M.price + " You save: ₹" + M.savings);
    	        System.out.println("------------------------------------"); 
    	        count++;
    	  }
       
    }
      
      public void display() {
    	  System.out.println("Combo | " + name + " - ₹" + price + " Amount saved: ₹" + savings);
  		System.out.println("------------------------------------");
      }

    
}

class DishOfTheWeek extends MenuItems {
//    ArrayList<MenuCard> menu;
//    public DishOfTheWeek(ArrayList<MenuCard> menu) {
//        this.menu = menu;
//    }
	MenuItems menu_function = new MenuItems();
//	ArrayList<MenuCard> menu = new ArrayList<>();
	

    public void Timer(ArrayList<MenuCard> menu) throws InterruptedException {
        System.out.println("\nChecking for the most trending dish of the week...");
        for (int i = 3; i > 0; i--) {
            System.out.print(i + " ");
            Thread.sleep(700);
        }
        System.out.println();

        MenuCard TrendingDish = null;
        for (MenuCard item : menu) {
            if (item.order_count >= 3) {
                if (TrendingDish == null || item.order_count > TrendingDish.order_count) {
                    TrendingDish = item;
                }
            }
        }

        if (TrendingDish != null) {
            System.out.println("🔥 Dish of the Week: " + TrendingDish.name + " - ₹" + TrendingDish.price + " 🔥");
        } else {
            System.out.println("No dish has been ordered enough times to qualify as Dish of the Week.");
        }
    }

    }


class InvalidPinException extends Exception {
	public InvalidPinException(String message) {
    	super(message);
	}
}
