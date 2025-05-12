package mini;

public class Staff {
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
	
	public void showFeedback() {
		for(Feedback f : fdlist) {
			System.out.println("Food quality: " + f.foodquality);
			System.out.println("Service: " + f.service);
			System.out.println("Choices: " + f.choice);
			if(f.choice != 1) {
				System.out.println("Menu suggestions: " + f.add);
			}
			System.out.println("Text feedback: " + f.Text_feedback);
		}
	}
}
