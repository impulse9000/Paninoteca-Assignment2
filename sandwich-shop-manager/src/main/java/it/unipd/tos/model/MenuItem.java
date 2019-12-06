////////////////////////////////////////////////////////////////////
// [Andrea] [Polo-Perucchin] [1169765]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class MenuItem {
    itemType type;
    String name;
    double price;

    public enum itemType {
        PANINO, FRITTO, BEVANDA
    }

    public MenuItem(itemType type, String name, double price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public itemType getItemType() {
        return type;
    }

}
