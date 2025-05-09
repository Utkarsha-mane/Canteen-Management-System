package mini;

import java.util.Scanner;

public class Feedback {
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