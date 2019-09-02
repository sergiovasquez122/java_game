import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Map map = new Map();
        ItemGenerator itemGenerator = new ItemGenerator();
        EnemyGenerator enemyGenerator = new EnemyGenerator( itemGenerator );
        Hero hero = new Hero("Luke", map);
        Enemy e = enemyGenerator.generateEnemy(hero.getLevel());
        fight(hero, e);
       /*
        boolean keep_playing = true;

        while ( keep_playing ) {
            hero.display();
            hero.displayItems();
            map.displayMap(hero.getLocation());
            String menu = "1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Quit";
            System.out.println(menu);
            int choice = CheckInput.getIntRange(1, 5);
            char c = 'n';
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
                case 5:
                    System.out.println("Game Over");
                    return;
            }

            switch (c) {
                case 'e':
                    keep_playing = enemyRoom(hero, map, enemyGenerator);
                    break;
                case 'i':
                    itemRoom(hero, map, itemGenerator);
                    break;
                case 'f':
                    boolean open_door = false;
                    if (hero.hasKey()) {
                        open_door = true;
                    } else if (hero.hasHolocron()) {
                        System.out.println("Would you like to use the force to attempt to unlock the finish room");
                        if (CheckInput.getYesNo()) {
                            hero.removeItem("Holocron");

                            final double THRESH_HOLD = .5;
                            double probability = Math.random();

                            if (Double.compare(probability, THRESH_HOLD) > 1) {
                                open_door = true;
                            }
                        }
                    }

                    if (open_door) {
                        hero.increaseLevel();
                        map.loadMap(++mapNum);
                    }
                    break;
            }
        }
        */
    }

    /**
     * @param hero
     * @param map
     * @param enemyGenerator
     * @return true if the hero is still alive
     */
    public static boolean enemyRoom(Hero hero, Map map, EnemyGenerator enemyGenerator) {
        Enemy enemy = enemyGenerator.generateEnemy(hero.getLevel());
        System.out.println("You've encountered a " + enemy.getName());
        enemy.display();

        String menu = "1. Fight\n2. Run Away";
        int num_of_options = 2;
        // Hero has a med kit give them option of using it
        if (hero.hasMedKit()) {
            menu += "\n3. Med Kit";
            num_of_options++;
        }

        System.out.println(menu);
        int choice = CheckInput.getIntRange(1, num_of_options);
        while ( hero.getHP()!= 0 ) {
            if (choice == 1) {
                fight(hero, enemy);
                if (enemy.getHP() == 0) {
                    map.removeCharAtLoc(hero.getLocation());
                    return true;
                }
            } else if (choice == 2) {
                // Run away to an adjacent cell
                // where the cells are encodes as
                // 0 = north, 1 = south, 2 = west, 3 = east
                final int WALK_CHOICES = 4;
                int random_walk = (int) (Math.random() * WALK_CHOICES);
                if (random_walk == 0) {
                    hero.goNorth();
                } else if (random_walk == 1) {
                    hero.goSouth();
                } else if (random_walk == 2) {
                    hero.goWest();
                } else {
                    hero.goEast();
                }
                return false;
            } else {
                hero.heal(25);
                hero.removeItem("Med Kit");
            }
        }
        return hero.getHP() != 0;
    }

    /**
     * Does a single trial of a fight between the hero and the enemy
     * @param hero the current hero
     * @param e the current enemy
     * @return true if the hero is still alive, false otherwise
     */
    public static boolean fight(Hero hero, Enemy e) {
        hero.attack(e);
        if( e.getHP() != 0){
            e.attack( hero );
        } else{
            System.out.println("You defeated the " + e.getName() + "!");
            Item item = e.getItem();
            if( hero.pickUpItem(item) ){
                System.out.println("You received a " + item.getName() + " from the enemy.");
            }
        }
        return hero.getHP() != 0;
    }

    /**
     * Item room gives the hero a random item if they have available inventory
     * @param hero the current hero of the game
     * @param map the current map of the game
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
