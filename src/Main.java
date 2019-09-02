import java.awt.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("What is your name? ");
        String name = CheckInput.getString();
        Map map = new Map();
        Hero hero = new Hero(name, map);
        ItemGenerator itemgenerator = new ItemGenerator();
        EnemyGenerator enemyGenerator = new EnemyGenerator(itemgenerator);
        int mapNum = 1;
        boolean game_over = false;
        while (!game_over) {
            hero.display();
            hero.displayItems();
            map.displayMap(hero.getLocation());
            System.out.println("1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Quit");
            int choice = CheckInput.getIntRange(1, 5);

            char c;
            switch (choice) {
                case 1:
                    c = hero.goNorth();
                    break;
                case 2:
                    c = hero.goSouth();
                    break;
                case 3:
                    c = hero.goEast();
                    break;
                case 4:
                    c = hero.goWest();
                    break;
                default:
                    System.out.println("Game Over");
                    return;
            }

            switch (c) {
                case 'e':
                    game_over = !enemyRoom(hero, map, enemyGenerator);
                    break;
                case 'i':
                    itemRoom(hero, map, itemgenerator);
                    break;
                case 'f':
                    boolean move_onto_next_level = false;
                    if (hero.hasKey()) {
                        move_onto_next_level = true;
                        hero.removeItem("Key");
                    } else if (hero.hasHolocron() && !hero.hasKey()) {
                        System.out.println(" Would you like to use the force to try to open the door?");

                        if (CheckInput.getYesNo()) {
                            hero.removeItem("Holocron");

                            final int BOUND = 10;
                            final int THRESHOLD = BOUND / 2;
                            move_onto_next_level = new Random().nextInt(BOUND + 1) > THRESHOLD;
                            if (!move_onto_next_level) {
                                System.out.println("Attempt Failed!");
                            }
                        }
                    }

                    if (move_onto_next_level) {
                        hero.increaseLevel();
                        map.loadMap(++mapNum);
                        System.out.println("Now on level " + mapNum);
                    }
                    break;
            }
        }
        System.out.println("Game Over");
    }

    /**
     * Method handles when Hero enters enemy room
     *
     * @param hero           the current hero
     * @param map            the current map
     * @param enemyGenerator generates a random enemy
     * @return true if the hero is still alive false otherwise
     */
    public static boolean enemyRoom(Hero hero, Map map, EnemyGenerator enemyGenerator) {
        Enemy enemy = enemyGenerator.generateEnemy(hero.getLevel());
        System.out.println("You've encountered a " + enemy.getName());
        enemy.display();

        while (hero.getHP() != 0 && enemy.getHP() != 0) {
            String menu = "1. Fight\n2. Run Away";
            int num_of_options = 2;
            // Hero has a med kit give them option of using it
            if (hero.hasMedKit()) {
                menu += "\n3. Med Kit";
                num_of_options++;
            }

            System.out.println(menu);
            int choice = CheckInput.getIntRange(1, num_of_options);
            switch (choice) {
                case 1:
                    fight(hero, enemy);
                    if (enemy.getHP() == 0) {
                        map.removeCharAtLoc(hero.getLocation());
                    }
                    break;
                case 2:
                    Random random = new Random();
                    Point old_location = hero.getLocation();
                    while (old_location.equals(hero.getLocation())) {
                        final int BOUND = 4;
                        int walk_direction = random.nextInt(BOUND);

                        switch (walk_direction) {
                            case 0:
                                hero.goNorth();
                                break;
                            case 1:
                                hero.goSouth();
                                break;
                            case 2:
                                hero.goWest();
                                break;
                            case 3:
                                hero.goEast();
                                break;
                        }
                    }
                    return true;
                case 3:
                    final int HEAL_AMOUNT = 25;
                    hero.heal(HEAL_AMOUNT);
                    hero.removeItem("Med Kit");
                    enemy.attack(hero);
                    break;
            }
        }
        return hero.getHP() != 0;
    }

    /**
     * Does a single trial of a fight between the hero and the enemy
     *
     * @param hero the current hero
     * @param e    the current enemy
     * @return true if the hero is still alive, false otherwise
     */
    public static boolean fight(Hero hero, Enemy e) {
        hero.attack(e);
        if (e.getHP() != 0) {
            e.attack(hero);
        } else {
            System.out.println("You defeated the " + e.getName() + "!");
            Item item = e.getItem();
            if (hero.pickUpItem(item)) {
                System.out.println("You received a " + item.getName() + " from the enemy.");
            }
        }
        return hero.getHP() != 0;
    }

    /**
     * Item room gives the hero a random item if they have available inventory
     *
     * @param hero          the current hero of the game
     * @param map           the current map of the game
     * @param itemGenerator generates a random item
     */
    public static void itemRoom(Hero hero, Map map, ItemGenerator itemGenerator) {
        Item item = itemGenerator.generateItem();
        System.out.println("You found a " + item.getName());
        if (hero.pickUpItem(item)) {
            map.removeCharAtLoc(hero.getLocation());
        } else {
            System.out.println("Inventory full would you like to drop an item?");
            // User decided to drop an item
            if (CheckInput.getYesNo()) {
                System.out.println("What item would you liked to drop?");
                hero.displayItems();

                int index = CheckInput.getIntRange(1, 5);
                hero.removeItem(index - 1);
                hero.pickUpItem(item);
                map.removeCharAtLoc(hero.getLocation());
            }
        }
    }
}
