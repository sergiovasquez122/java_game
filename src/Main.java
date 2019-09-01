import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("What is your name?");
        String name = CheckInput.getString();

        Map map = new Map();
        int mapNum = 1;
        ItemGenerator itemGenerator = new ItemGenerator();
        EnemyGenerator enemyGenerator = new EnemyGenerator(itemGenerator);

        Hero hero = new Hero(name, map);
        hero.pickUpItem(new Item("Holocron"));

        for(int i = 0;i < 5; ++i){
            for(int j = 0; j< 5; ++j){
                map.reveal(new Point(i, j));
            }
        }

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
       while( hero.getHP() != 0){
            enemyRoom(hero, map, enemyGenerator);
            hero.displayItems();
       }
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

    public static boolean fight(Hero hero, Enemy e) {
        if (hero.hasHolocron()) {
            System.out.println("1. Use Blaster\n2. Use Force");
            int choice = CheckInput.getIntRange(1, 2);
            // If the choice is 2 then decide what force power to use
            // else use the standard blaster attack
            if (choice == 2) {
                System.out.println(Force.FORCE_MENU);
                int force_power_chosen = CheckInput.getIntRange(1, 3);
                int damage_amount;
                String power_chosen;
                if (force_power_chosen == 1) {
                    damage_amount = hero.forcePush();
                    power_chosen = "Force Push";
                } else if (force_power_chosen == 2) {
                    damage_amount = hero.forceChoke();
                    power_chosen = "Force Choke";
                } else {
                    damage_amount = hero.forceSlam();
                    power_chosen = "Force Slam";
                }
                System.out.println(hero.getName() + " hits  " + e.getName() + " with " + power_chosen + " for " + damage_amount + " damage.");
                e.takeDamage(damage_amount);
                hero.removeItem("Holocron");
            }
        } else {
            hero.attack(e);
        }

        if (e.getHP() != 0) {
            e.attack(hero);
        } else {
            Item item = e.getItem();
            if (hero.pickUpItem(item)) {
                System.out.println("You received a " + item.getName() + " from the enemy.");
            } else {
                System.out.println("Inventory full would you like to drop an item?");
                // User decided to drop an item
                if (CheckInput.getYesNo()) {
                    System.out.println("What item would you liked to drop?");
                    hero.displayItems();

                    int index = CheckInput.getIntRange(1, 5);
                    hero.removeItem(index - 1);
                    hero.pickUpItem(item);
                }
            }
        }
        return hero.getHP() != 0;
    }

    /**
     * @param hero
     * @param map
     * @param itemGenerator
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
            }
        }
    }
}
