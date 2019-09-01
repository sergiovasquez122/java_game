import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Map map = new Map();
        Hero hero = new Hero("Luke",map);
        ItemGenerator itemGenerator = new ItemGenerator();
        for( int i = 0; i < 5; ++i){
            hero.pickUpItem(itemGenerator.generateItem());
        }
        itemRoom(hero,map,itemGenerator);
        hero.displayItems();
    }

    /**
     *
     * @param hero
     * @param map
     * @param enemyGenerator
     * @return true if the hero is still alive
     */
    public static boolean enemyRoom(Hero hero, Map map, EnemyGenerator enemyGenerator){
        Enemy enemy = enemyGenerator.generateEnemy(hero.getLevel());
        System.out.println("You've encountered a " + enemy.getName());
        enemy.display();

        String menu = "1. Fight\n2. Run Away\n";
        boolean hero_died = false;
        int num_of_options = 2;
        // Hero has a med kit give them option of using it
        if ( hero.hasMedKit() ){
            menu += "3. Med Kit\n";
            num_of_options ++;
        }

        int choice = CheckInput.getIntRange(1, num_of_options);
        while(hero_died) {
            if (choice == 1) {
                hero_died = fight(hero, enemy);
            } else if (choice == 2) {
                // Run away to an adjacent cell
                // where the cells are encodes as
                // 0 = north, 1 = south, 2 = west, 3 = east
                final int WALK_CHOICES = 4;
                int random_walk = (int) Math.random() * WALK_CHOICES;
                if(random_walk == 0){
                    hero.goNorth();
                } else if(random_walk == 1){
                    hero.goSouth();
                } else if(random_walk == 2){
                    hero.goWest();
                } else {
                    hero.goEast();
                }
                return true;
            } else {
                hero.heal(25);
                hero.removeItem("Med Kit");
            }
        }
        return !hero_died;
    }

    public static boolean fight(Hero hero, Enemy e){
        return true;
    }

    /**
     *
     * @param hero
     * @param map
     * @param itemGenerator
     */
    public static void itemRoom(Hero hero, Map map, ItemGenerator itemGenerator){
        Item item = itemGenerator.generateItem();
        System.out.println("You found a " + item.getName());
        if( hero.pickUpItem(item) ){
            map.removeCharAtLoc(hero.getLocation());
        } else {
            System.out.println("Inventory full would you like to drop an item?");
            // User decided to drop an item
            if( CheckInput.getYesNo() ){
                System.out.println("What item would you liked to drop?");
                hero.displayItems();

                int index = CheckInput.getIntRange(1, 5);
                hero.removeItem(index - 1);
                hero.pickUpItem(item);
            }
        }
    }
}
