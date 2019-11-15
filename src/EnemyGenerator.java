
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
    /** The list of all possible enemies */
    private ArrayList<Enemy> enemyList;
    /** The list of all possible items */
    private ItemGenerator itemGenerator;
    /**
     * Constructor - Constructs a EnemyGenerator with a specified ItemGenerator
     * @param itemGenerator generates the possible types of items the enemy can have
     */
    public EnemyGenerator(ItemGenerator itemGenerator) {
        this.itemGenerator = itemGenerator;
        enemyList = new ArrayList<>();

        // Read from file if file is found
        try {
            Scanner read = new Scanner(new File("EnemyList.txt"));
            do {
                // Enemy data is in the form [name, health_multiplier, force_user]
                String tokens[] = read.nextLine().split(",");

                // Get the attributes of the current enemy
                String enemyName = tokens[0];
                final int DEFAULT_LEVEL = 1;
                int baseHealth = Integer.parseInt(tokens[1]);
                String enemyType = tokens[2];
                Item item = itemGenerator.generateItem();

                // n is a normal enemy, f is a force enemy
                if ( enemyType.equals("n") ) {
                    enemyList.add(new Enemy(enemyName, DEFAULT_LEVEL, baseHealth, item));
                } else {
                    enemyList.add(new ForceEnemy(enemyName, DEFAULT_LEVEL, baseHealth, item));
                }
            } while (read.hasNextLine());
            // Close the file
            read.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found");
        }
    }

    /**
    public static void main(String[] args) {
        ItemGenerator itemGenerator = new ItemGenerator();
        EnemyGenerator enemyGenerator = new EnemyGenerator(itemGenerator);
        Map map = new Map();
        Hero hero = new Hero("Luke", map);
        Enemy e = enemyGenerator.generateEnemy(1);
        while(!e.getName().equals("Sith Lord")){
            e = enemyGenerator.generateEnemy(1);
        }

        while(hero.getHP() != 0){
            e.attack(hero);

        }

    }
     */
    /**
     * Generate a random enemy with specified level
     * @param level the level of the enemy
     * @return Generate a random enemy with specified level
     */
    public Enemy generateEnemy(int level) {
        // Get a random enemy with equal likelihood and generate a random item
        Random generator = new Random();
        int randomIndex = generator.nextInt(enemyList.size());
        Enemy enemy = enemyList.get(randomIndex);
        Item item = itemGenerator.generateItem();

        // Create different type of enemies and use supplied level as multiplier for the enemy hp
        if( !( enemy instanceof ForceEnemy ) ) {
            return new Enemy( enemy.getName(), level, level * enemy.getMaxHP(), item);
        } else {
            return new ForceEnemy(enemy.getName(), level, level * enemy.getMaxHP(), item);
        }
    }
}
