import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * EnemyGenerator Class - Single representation of EnemyGenerator
 * @author Sergio Vasquez
 */
public class EnemyGenerator {
    /**
     * The list of all possible enemies
     */
    private ArrayList<Enemy> enemyList;
    private ItemGenerator itemGenerator;

    /**
     * Constructor - Constructs a EnemyGenerator with a specified ItemGenerator
     *
     * @param itemGenerator generates the possible types of items the enemy can have
     */
    public EnemyGenerator(ItemGenerator itemGenerator) {
        this.itemGenerator = itemGenerator;
        try {
            Scanner read = new Scanner(new File("EnemyList.txt"));
            enemyList = new ArrayList<>();
            do {
                String tokens [] = read.nextLine().split(",");

            } while (read.hasNextLine());
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found");
        }
    }

    /**
     * Generate a random enemy with specified level
     *
     * @param level the level of the enemy
     * @return Generate a random enemy with specified level
     */
    Enemy generateEnemy(int level) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(enemyList.size());
        Enemy e = enemyList.get(randomIndex);
        Item item = itemGenerator.generateItems();
        return new Enemy(e.getName(), level, e.getMaxHP(), item);
    }
}
