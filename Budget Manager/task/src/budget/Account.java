package budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private double balance;
    private Map<Purchase.PurchaseType, List<Purchase>> map = new HashMap<>();

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public Map<Purchase.PurchaseType, List<Purchase>> getMap() {
        return map;
    }

    public void addPurchase(Purchase purchase) {
        map.putIfAbsent(purchase.getType(), new ArrayList<>());
        map.get(purchase.getType()).add(purchase);
    }

    public void addBalance(double income) {
        this.balance += income;
    }

    public void deductBalance(double expense) {
        if (expense > balance) {
            throw new RuntimeException("Don't have enough money!");
        }

        this.balance -= expense;
    }

}
