package unsw.loopmania;

public enum ShopItem {
    // equipment(name, buyingPrice, sellingPrice)
    SWORD("Sword", 20, 10),
    STAKE("Stake", 50, 25),
    STAFF("Staff", 40, 20),
    ARMOUR("Armour", 60, 30),
    SHIELD("Shield", 60, 30),
    HELMET("Helmet", 50, 25),
    HEALTHPOTION("Health Potion", 80, 40),
    DOGGIECOIN("Doggie Coin", 0, 0);

    private String name;
    private int buyingPrice, sellingPrice;

    private ShopItem(String name, int buyingPrice, int sellingPrice) {
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    public String getName() {
        return name;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }
}
