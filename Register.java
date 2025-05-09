package mini;

import java.util.Scanner;

public class Register {
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