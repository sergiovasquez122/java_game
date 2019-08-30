import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput=  new Scanner(System.in);
        System.out.println("What is your name?");
        String name = userInput.nextLine();
        Map map = new Map();
        Hero hero = new Hero(name, map);
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
        }
        System.out.println("Game Over");
    }

    public static boolean enemyRoom(Hero hero, Map map, EnemyGenerator enemyGenerator){
        return true;
    }

    public static boolean fight(Hero hero, Enemy e){
        return true;
    }

    public static void itemRoom(Hero hero, Map map, ItemGenerator itemGenerator){

    }
}
