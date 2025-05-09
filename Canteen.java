package canteen_management_system;
import java.util.*;
public class Canteen {
public static void main(String[] args) throws InterruptedException {
	 Scanner input = new Scanner(System.in);
	 MenuItems m = new MenuItems();
ArrayList<MenuCard> mList = new ArrayList<MenuCard>();
ArrayList<Staff> staff_list = new ArrayList<Staff>();
DishOfTheWeek d=new DishOfTheWeek();
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
System.out.println("Enter your choice\n1.Menucard\n2.Popular Dishes\n3.Feedback\n4.Dish of the week");
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
case 4:
	d.Timer();	
	break;
		
default:
System.out.println("Invalid choice!");
}
}
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
