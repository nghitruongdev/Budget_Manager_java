package budget;

public class Purchase {
    private String name;
    private Double price;
    private PurchaseType type;

    public Purchase(String name, Double price, PurchaseType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, price);
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public PurchaseType getType() {
        return type;
    }

    public enum PurchaseType {
        Food, Clothes, Entertainment, Other
    }

}
