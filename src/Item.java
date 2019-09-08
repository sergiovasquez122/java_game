/**
 * Item class - Representation of a single item
 * @author Sergio Vasquez
 */
public class Item {
    /** The name of the item */
    private String name;
    /**
     * Constructor - Create an item with the specified name
     * @param n sets the name of the item
     */
    public Item(String n) {
        name = n;
    }
    /**
     * Retrieve the item's name
     * @return the item's name
     */
    public String getName() {
        return name;
    }
}
