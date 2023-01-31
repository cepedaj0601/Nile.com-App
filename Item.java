package AppPackage;

public class Item {
    public String id;
    public int quantity;
    public String details;
    public String stock;
    public String price;
    public double discount;
    public double finalPrice;

    public Item(String id, String quantity, String[] input){

        this.id = id;
        this.quantity = Integer.parseInt(quantity);

        details = input[1].replace("\"", "");
        stock = input[2].replace(" ", "");
        price = input[3].replace(" ", "");

        discount = this.quantity >= 1 && this.quantity <= 4 ? 0 : this.quantity >= 5 && this.quantity <= 9 ? 10 :
                this.quantity >= 10 && this.quantity <= 14 ? 15 : 20;


        finalPrice = ((Integer.parseInt(quantity)) * (Double.parseDouble(price))) -
                (((Integer.parseInt(quantity)) * (Double.parseDouble(price))) * (discount / 100));
    }
}
