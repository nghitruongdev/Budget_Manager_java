package budget;

public enum Menu {
    MAIN("\nChoose your action:\n" +
            "1) Add income\n" +
            "2) Add purchase\n" +
            "3) Show list of purchases\n" +
            "4) Balance\n" +
            "5) Save\n" +
            "6) Load\n" +
            "7) Analyze (Sort)\n" +
            "0) Exit"),
    MAKE_PURCHASE("\nChoose the type of purchase\n" +
            "1) Food\n" +
            "2) Clothes\n" +
            "3) Entertainment\n" +
            "4) Other\n" +
            "5) Back"),
    SHOW_LIST("\nChoose the type of purchases\n" +
            "1) Food\n" +
            "2) Clothes\n" +
            "3) Entertainment\n" +
            "4) Other\n" +
            "5) All\n" +
            "6) Back"),
    SORT("\nHow do you want to sort?\n" +
            "1) Sort all purchases\n" +
            "2) Sort by type\n" +
            "3) Sort certain type\n" +
            "4) Back"),
    SORT_TYPE("\nChoose the type of purchase\n" +
            "1) Food\n" +
            "2) Clothes\n" +
            "3) Entertainment\n" +
            "4) Other");

    private String menu;

    Menu(String menu) {
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }
}
