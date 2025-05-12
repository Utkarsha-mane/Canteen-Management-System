package mini;

class Profile {
	String name, email;
	private String password;
	int user_pin;
	int dish_count = 0;
	ArrayList<String> reward_history = new ArrayList<>();
	Scanner input = new Scanner(System.in);

	Profile() {
	};

	Profile(String name, String email, String password, int pin) {
		this.name = name;
		this.email = email;
		this.password = password;
		user_pin = pin;
	}

	void detail(ArrayList customers) {
		System.out.print("Enter your name: ");
		name = input.nextLine();
		System.out.print("Enter your email: ");
		email = input.nextLine();
		System.out.print("Enter your password: ");
		password = input.nextLine();
		System.out.print("Enter your account pin: ");
		user_pin = input.nextInt();
		customers.add(new Profile(name, email, password, user_pin));
	}

	int getPin() {
		return user_pin;
	}
}
