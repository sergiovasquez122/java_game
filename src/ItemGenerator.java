import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ItemGenerator class - Representation of single ItemGenerator
 * @author Sergio Vasquez
 */
public class ItemGenerator {
    /** Contains all possible items to be generated */
    private ArrayList<Item> itemList;
    /**
     * Constructor - fills the ItemGenerator with possible items
     */
    public ItemGenerator() {
        final String fileName = "ItemList.txt";
        itemList = new ArrayList<>();

        // Read from file if file is found
        try {
            Scanner read = new Scanner(new File(fileName));
            do {
                String itemName = read.nextLine();
                itemList.add(new Item(itemName));
            } while (read.hasNextLine());
            // Close the file
            read.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found");
        }
    }

    /**
    public static void main(String[] args) {
        ItemGenerator generator = new ItemGenerator();

        for (int i = 0; i < 10; ++i) {
            System.out.println(generator.generateItem().getName());
        }
    }
     */
    /**
     * Retrieves a random item from the ItemGenerator
     * @return a random item from the ItemGenerator
     */
    public Item generateItem() {
        // Get an random index in the range [0, itemList.size())
        // with equal likelihood
        Random generator = new Random();
        int randomIndex = generator.nextInt(itemList.size());

        // Create new instance of the itme
        // from the template
        Item i = itemList.get(randomIndex);
        return new Item(i.getName());
    }
}
