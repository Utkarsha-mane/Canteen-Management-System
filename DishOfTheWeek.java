class DishOfTheWeek {
		    ArrayList<MenuCard> menu;
		    public DishOfTheWeek(ArrayList<MenuCard> menu) {
		        this.menu = menu;
		    }

		    public void Timer() throws InterruptedException {
		        System.out.println("\nChecking for the most trending dish of the week...");
		        for (int i = 5; i > 0; i--) {
		            System.out.print(i + " ");
		            Thread.sleep(1000);
		        }
		        System.out.println();

		        MenuCard TrendingDish = null;
		        for (MenuCard item : menu) {
		            if (item.order_count >= 3) {
		                if (TrendingDish == null || item.order_count > TrendingDish.order_count) {
		                    TrendingDish = item;
		                }
		            }
		        }

		        if (TrendingDish != null) {
		            System.out.println("🔥 Dish of the Week: " + TrendingDish.name + " - ₹" + TrendingDish.price + " 🔥");
		        } else {
		            System.out.println("No dish has been ordered enough times to qualify as Dish of the Week.");
		        }
		    }

		    }
		
