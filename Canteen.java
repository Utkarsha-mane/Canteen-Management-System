package canteen_management_system;

import java.util.*;

public class Canteen {
	public static void main(String[] args) throws InterruptedException {
		Scanner input = new Scanner(System.in);
		MenuItems m = new MenuItems();
		ArrayList<MenuCard> mList = new ArrayList<MenuCard>();
		ArrayList<Staff> staff_list = new ArrayList<Staff>();
		ArrayList<Profile> customer = new ArrayList<>();
		Profile R = new Profile();
		Order order = new Order(R);
		Recommendations pd = new Recommendations();
		Feedback fd = new Feedback();
		Random_dish Rd = new Random_dish(R, mList);
		Prize_coupons pc = new Prize_coupons(R);
		DishOfTheWeek dw = new DishOfTheWeek();
		int choice;
		String continue_ = "yes";
		Staff admin = new Staff("admin.cumminscanteen.in", "admin123");
		char Logout = 'N', cont, cont2;
		int Menu = 0;
		int Staffmenu = 0;
		m.to_add_itemlist(mList);
		do {
			System.out.println("Welcome to the Canteen!");
			System.out.println("1. For staff\n2. For customers\n3. Explore");
			Menu = input.nextInt();
			switch (Menu) {
			case 1:
				do {
					boolean Access = RegisterOrLogin.access(staff_list);
					if (Access) {
						do {
							System.out.println("1. View reports\n2. View feedback\n3. Logout");
							Staffmenu = input.nextInt();
							switch (Staffmenu) {
								case 1:
									// Reports code
									break;
								case 2:
									Staff.showFeedback();
									break;
								case 3:
									Logout = input.next().charAt(0);
									input.nextLine();
									break;
							}
							System.out.println("View options menu? (Y/N)");
							cont = input.next().charAt(0);
						} while (cont == 'Y');

					} else if (!Access) {

					}
				} while (Logout == 'Y');
				break;
			case 2:
				R.detail(customer);
				do
				{
					System.out.println("Enter your choice\n1. Menucard\n2. Offers\n3. Popular Dishes\n4. Get Recommendation\n5. I'm so confused..Get me a random dish\n6. Redeem free dish\n7. Feedback");
					choice = input.nextInt();
					switch (choice) {
						case 1:
							order.takeOrder();
							break;
						case 2: 
							// offers
							break;
						case 3:
							dw.menu = mList;
							dw.Timer();	
							break;
						case 4:
							pd.get_recommendations(R);
							break;
						case 5:
							Rd.random_dish();
							break;
						case 6:
							pc.redeem_points();
							break;
						case 7:
							Staff.addFeedback();
							break;
						default:
							System.out.println("Invalid choice!");
					}
					System.out.println("Show menu?");
					continue_ = input.next();
				}while(continue_.toLowerCase().equals("yes"));
				break;
				
			case 3 :
				System.out.println("You can pay only in cash!");
				do
				{
					System.out.println("Enter your choice\n1. Menucard\n2. Offers\n3. Popular Dishes\n4. Get Recommendation\n5. I'm so confused..Get me a random dish\n6. Redeem free dish\n7. Feedback");
					choice = input.nextInt();
					switch (choice) {
						case 1:
							order.takeOrder();
							break;
						case 2: 
							// offers
							break;
						case 3:
							dw.menu = mList;
							dw.Timer();	
							break;
						case 4:
							pd.get_recommendations(R);
							break;
						case 5:
							Rd.random_dish();
							break;
						case 6:
							pc.redeem_points();
							break;
						case 7:
							Staff.addFeedback();
							break;
						default:
							System.out.println("Invalid choice!");
					}
					System.out.println("Show menu?");
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
//class Register 
//class Recommendations

//class Feedback 
//class Prize_coupons

//class Profile

//class RegisterOrLogin 
//class Staff 
//abstract class MenuCard 

//class Breakfast extends MenuCard 
//class Snacks 
//class Beverages 
//class MenuItems 
//class Order 
//class Random_dish extends MenuItems

//class Payment

//class Offers

//class Popular_dishes
