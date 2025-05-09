package mini;

import java.util.ArrayList;

public class MenuItems {
//	 public void addItem(MenuCard item) {
//	 mList.add(item);
//	 }
	 public void showMenu(ArrayList<MenuCard> mList) {
	 System.out.println("------ Menu ------");
	 for (int i = 0; i < mList.size(); i++) {
	 System.out.print((i + 1) + ". ");
	 mList.get(i).display();
	 }
	 System.out.println("------------------");
	 }
//	 public MenuCard getItem(int i) {
//	 if (i >= 0 && i < mList.size())
//	 {
//	 return mList.get(i);
//	 }
//	 else
//	 {
//	 return null;
//	 }
//	 }
//	 public int getMenuSize() {
//	 return mList.size();
//	 }
	 public void to_add_itemlist(ArrayList<MenuCard> mList)
	 {
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
