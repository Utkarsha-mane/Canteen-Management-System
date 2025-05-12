package mini;

class Prize_coupons {
	Profile user;
	Recommendations R_free = new Recommendations();
	Order O_free = new Order();
	Popular_dishes P_free = new Popular_dishes();
	Random_dish S_free = new Random_dish();
	Scanner sc = new Scanner(System.in);
	String continue_, recom;

	Prize_coupons(Profile user) {
		this.user = user;
	}

	void redeem_points() {
		System.out.println("🎁 Reward Zone: Hello " + user.name + "!");

		if (user.dish_count >= 3) {
			System.out.println("You currently have " + user.dish_count + " reward points!");
			System.out.println("✨ You can redeem a FREE dish now! 🎉");

			System.out.println("\nHow would you like to choose your free dish?");
			System.out.println("1. Based on Recommendations 📊");
			System.out.println("2. From Popular Dishes 🍽️");
			System.out.println("3. Surprise Me! 🎲");
			System.out.print("Enter your choice (1/2/3): ");
			int choice = sc.nextInt();
			sc.nextLine(); // consume newline

			switch (choice) {
				case 1:
					R_free.get_recommendations(user);
					break;
				case 2:
					P_free.get_popular_dishes();
					P_free.select_popular_dish();
					break;
				case 3:
					S_free.random_dish();
					break;
				default:
					System.out.println("⚠️ Invalid choice. Please try again next time.");
					return;
			}

			System.out.print("\nConfirm redemption of 3 points for this dish? (yes/no): ");
			String confirm = sc.nextLine();
			if (confirm.equalsIgnoreCase("yes")) {
				user.dish_count -= 3;
				System.out.println("✅ Redemption successful! Enjoy your free dish, " + user.name + "!");
				user.reward_history.add("Redeemed 1 free dish using 3 points.");
			} else {
				System.out.println("❌ Redemption cancelled. Points not deducted.");
			}
		} else {
			System.out.println("🚫 Sorry! You need at least 3 reward points to get a free dish.");
			System.out.println("💡 Tip: Order more dishes to earn rewards faster!");
			System.out.println("Would you like to order ?(yes/no)");
			continue_ = sc.nextLine();
			System.out.println("Do you need any recommendations?");
			recom = sc.nextLine();
			if (continue_.toLowerCase().equals("yes")) {
				if (recom.toLowerCase().equals("yes")) {
					R_free.get_recommendations(user);
				} else {
					O_free.takeOrder();
				}
			} else {
				System.out.println("Thank you !");
			}
		}
		System.out.println();
	}
}
