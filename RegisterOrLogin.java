package mini;

import java.util.ArrayList;
import java.util.Scanner;

public class RegisterOrLogin {
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
