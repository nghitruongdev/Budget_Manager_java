package budget;

import java.io.*;
import java.util.*;

import static budget.MyInput.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Account account = new Account(0);
        File fileData = new File("purchases.txt");
        while (true) {
            int select = readInt(Menu.MAIN.getMenu());
            System.out.println();

            switch (select) {
                case 1:
                    addIncome(account);
                    break;
                case 2:
                    makePurchase(account);
                    break;
                case 3:
                    showPurchaseList(account);
                    break;
                case 4:
                    System.out.printf("Balance: $%.2f\n", account.getBalance());
                    break;
                case 5:
                    saveData(fileData, account);
                    break;
                case 6:
                    loadData(fileData, account);
                    break;
                case 7:
                    analyze(account);
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
            }
        }
    }

    private static void analyze(Account account) {
        PurchaseSorter sorter = new PurchaseSorter(account);

        while (true) {
            int select = readInt(Menu.SORT.getMenu());
            System.out.println();

            switch (select) {
                case 1:
                    sorter.setMethod(new PurchaseSorter.SortAllPurchase());
                    break;
                case 2:
                    sorter.setMethod(new PurchaseSorter.SortAllType());
                    break;
                case 3:
                    sorter.setMethod(new PurchaseSorter.SortByType());
                    break;
                case 4:
                    return;
                default:
                    continue;
            }

            sorter.invoke();
        }
    }

    private static void loadData(File fileData, Account account) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileData)))) {
            String balance = reader.readLine();
            account.addBalance(Double.parseDouble(balance.substring(balance.indexOf("$") + 1)));

            while (reader.ready()) {
                String[] item = reader.readLine().split(", ");
                account.addPurchase(new Purchase(item[1], Double.parseDouble(item[2]), Purchase.PurchaseType.valueOf(item[0])));
            }

            System.out.println("Purchases were loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveData(File fileData, Account account) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileData))) {
            writer.printf("Balance: $%f\n", account.getBalance());

            account.getMap().values().forEach((list) -> list.forEach((item) -> writer.printf("%s, %s, %f\n", item.getType().name(), item.getName(), item.getPrice())));

            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addIncome(Account account) {
        int income = readInt("Enter income:");
        account.addBalance(income);
        System.out.println("Income was added!");
    }

    private static void makePurchase(Account account) {
        while (true) {
            int select = readInt(Menu.MAKE_PURCHASE.getMenu());
            System.out.println();

            if (select == 5) break;

            if (select < 1 || select > 4) continue;

            Purchase.PurchaseType type = Purchase.PurchaseType.values()[select - 1];

            String name = readLine("Enter purchase name:");
            double price = readDouble("Enter its price:");

            try {
                account.deductBalance(price);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                continue;
            }

            account.addPurchase(new Purchase(name, price, type));

            System.out.println("Purchase was added!");
        }
    }

    private static void showPurchaseList(Account account) {
        Map<Purchase.PurchaseType, List<Purchase>> map = account.getMap();
        if (map.isEmpty()) {
            System.out.println("The purchase list is empty");
            return;
        }

        while (true) {
            int select = readInt(Menu.SHOW_LIST.getMenu());
            System.out.println();

            final List<Purchase> list;
            switch (select) {
                case 1:
                case 2:
                case 3:
                case 4:
                    Purchase.PurchaseType type = Purchase.PurchaseType.values()[select - 1];
                    System.out.println(type.name() + ":");
                    list = map.get(type);
                    break;
                case 5:
                    System.out.println("All:");
                    list = new ArrayList<>();
                    map.values().forEach(list::addAll);
                    break;
                case 6:
                    return;
                default:
                    continue;
            }

            if (list.isEmpty()) {
                System.out.println("The purchase list is empty");
                continue;
            }

            double total = exportList(list);
            System.out.printf("Total sum: $%.2f\n", total);
        }
    }

    private static double exportList(List<Purchase> list) {
        double total = 0;

        for (Purchase pc :
                list) {
            System.out.println(pc);
            total += pc.getPrice();
        }
        return total;
    }

}
