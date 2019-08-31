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
        System.out.print("You've encountered a " + e.getName());
        Scanner input = new Scanner(System.in);
        while(hero.getHP() != 0 || e.getHP() != 0){
            e.display();
            System.out.println("1. Fight");
            System.out.println("2. Run Away");
            try {
                int choice = -1;
                do {
                   choice = input.nextInt();
                }while(choice != 1 || choice != 2);
            } catch (IllegalArgumentException iae){
                System.out.println("Invalid input");
            }
        }
        return hero.getHP() != 0;
    }

    public static void itemRoom(Hero hero, Map map, ItemGenerator itemGenerator){

    }
}
