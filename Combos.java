public class Combos {
    public Combos(String name, double price, double savings) {
        super(name, price, savings);
    }
    public void display() {
        System.out.println("Combo | " + name + " - ₹" + price);
        System.out.println("------------------------------------");
    }

   
    
}
