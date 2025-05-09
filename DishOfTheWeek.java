package canteen_management_system;

import java.util.ArrayList;

public class DishOfTheWeek {
	ArrayList<DishOfTheWeek> dowList = new ArrayList<DishOfTheWeek>();
	ArrayList<MenuCard> mList = new ArrayList<MenuCard>();
	void Timer() throws InterruptedException
	 {
		System.out.println("Dish Of The Week is ...");

	int i;
		for(i=5;i>0;i--)
		{
			System.out.println(i);
			Thread.sleep(1000);
		 }
		

}
	


}
