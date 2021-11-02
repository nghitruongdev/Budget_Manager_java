package budget;

import budget.Account;
import budget.Menu;
import budget.MyInput;
import budget.Purchase;

import java.util.*;

public class PurchaseSorter {

    private Account account;
    private SortingMethod method;

    public PurchaseSorter(Account account) {
        this.account = account;
    }

    public void setMethod(SortingMethod method) {
        this.method = method;
    }

    public void invoke() {
        method.sort(account);
    }

    interface SortingMethod {
        void sort(Account account);

        default double getTotal(List<Purchase> list, boolean printPurchase) {
            double total = 0;

            for (Purchase purchase : list) {
                total += purchase.getPrice();
                if (printPurchase) System.out.println(purchase);
            }

            return total;
        }
    }

    static class SortAllPurchase implements SortingMethod {

        @Override
        public void sort(Account account) {
            Map<Purchase.PurchaseType, List<Purchase>> map = account.getMap();
            if (map.isEmpty()) {
                System.out.println("The purchase list is empty!");
                return;
            }

            List<Purchase> listTotal = new ArrayList<>();
            for (List<Purchase> list : map.values()) {
                listTotal.addAll(list);
            }

            listTotal.sort((pc, pc1) -> pc1.getPrice().compareTo(pc.getPrice()));

            System.out.println("All:");
            System.out.printf("Total: $%.2f\n", getTotal(listTotal, true));
        }
    }

    static class SortAllType implements SortingMethod {

        @Override
        public void sort(Account account) {
            Map<Purchase.PurchaseType, List<Purchase>> map = account.getMap();
            Map<Purchase.PurchaseType, Double> balanceMap = new HashMap<>();

            for (Purchase.PurchaseType type : Purchase.PurchaseType.values()) {
                balanceMap.put(type, getTotal(map.getOrDefault(type, new ArrayList<>()), false));
            }

            List<Map.Entry<Purchase.PurchaseType, Double>> listEntry = new ArrayList<>(balanceMap.entrySet());
            listEntry.sort((entry, entry1) -> (int) (entry1.getValue() - entry.getValue()));

            double total = 0;
            for (Map.Entry<Purchase.PurchaseType, Double> entry : listEntry) {
                total += entry.getValue();
                System.out.printf("%s - $%.2f\n", entry.getKey().name(), entry.getValue());
            }
            System.out.printf("Total: $%.2f\n", total);
        }
    }

    static class SortByType implements SortingMethod {

        @Override
        public void sort(Account account) {
            Map<Purchase.PurchaseType, List<Purchase>> map = account.getMap();

            int select = MyInput.readInt(Menu.SORT_TYPE.getMenu());
            System.out.println();

            Purchase.PurchaseType type = Purchase.PurchaseType.values()[select - 1];

            List<Purchase> list = map.get(type);
            if (list == null || list.isEmpty()) {
                System.out.println("The purchase list is empty!");
                return;
            }

            list.sort((pc, pc1) -> pc1.getPrice().compareTo(pc.getPrice()));

            System.out.println(type.name() + ":");
            System.out.printf("Total: $%.2f\n", getTotal(list, true));
        }
    }
}
