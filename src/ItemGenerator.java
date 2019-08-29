import java.util.ArrayList;
import java.util.Random;

/**
 * ItemGenerator class - Representation of single ItemGenerator
 * @author Sergio Vasquez
 */
public class ItemGenerator {
    /** Contains all possible items to be generated */
    private ArrayList<Item> itemList;
    /**
     *  Constructor - fills the ItemGenerator with possible items
     */
    public ItemGenerator(){
    }
    /**
     * Retrieves a random item from the ItemGenerator
     * @return a random item from the ItemGenerator
     */
    public Item generateItems(){
        Random generator = new Random();
        int randomIndex = generator.nextInt(itemList.size());
        Item i = itemList.get(randomIndex);
        return new Item(i.getName());
    }
}
