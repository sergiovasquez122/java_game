import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput=  new Scanner(System.in);
        System.out.println("What is your name?");
        String name = userInput.nextLine();
        Map map = new Map();
        ItemGenerator itemGenerator = new ItemGenerator();
        EnemyGenerator enemyGenerator = new EnemyGenerator(itemGenerator);
        Hero hero = new Hero(name, map);
        int currentMap = 1;
        boolean gameOver = false;
        while(!gameOver) {
            hero.display();
            hero.displayItems();
            map.displayMap(hero.getLocation());

            System.out.println("1. Go North");
            System.out.println("2. Go South");
            System.out.println("3. Go East");
            System.out.println("4. Go West");
            System.out.println("5. Quit");
            int choice = userInput.nextInt();
            if(choice != 5) {
                char c = 'n';
                switch (choice) {
                    case 1: c = hero.goNorth();
                            break;
                    case 2: c = hero.goSouth();
                            break;
                    case 3: c = hero.goEast();
                            break;
                    case 4: c = hero.goWest();
                            break;
                }

                if(c == 'e'){
                    if(!enemyRoom(hero, map, enemyGenerator)){
                        gameOver = true;
                    }
                } else if(c == 'i'){
                    itemRoom(hero, map, itemGenerator);
                } else if(c == 'f'){
                    if(hero.hasKey()){
                        hero.increaseLevel();
                        hero.increaseMaxHP(10);
                        currentMap++;
                        map.loadMap(currentMap);
                    }
                }
            } else{
                gameOver = true;
            }
        }
        System.out.println("Game Over");
    }

    public static boolean enemyRoom(Hero hero, Map map, EnemyGenerator enemyGenerator){
        Enemy e = enemyGenerator.generateEnemy(hero.getLevel());
        return fight(hero, e);
    }

    public static boolean fight(Hero hero, Enemy e){
        return true;
    }

    public static void itemRoom(Hero hero, Map map, ItemGenerator itemGenerator){
        Item item = itemGenerator.generateItem();
        // Does the hero have enough room in his inventory to pick up the item
        if(hero.pickUpItem(item)){
            map.removeCharAtLoc(hero.getLocation());
        } else{
            System.out.println("Inventory full! Would you like to drop an item (y / n) ");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine();
            if(choice.equals("y")){
                System.out.println("Which item would you like to drop?");
                int index = Integer.parseInt(input.nextLine());
                hero.removeItem(index);
                hero.pickUpItem(item);
            }
        }
    }
}
