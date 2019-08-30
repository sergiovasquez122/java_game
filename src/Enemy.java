/**
 * Enemy Class - Single instance of an enemy
 * @author Sergio Vasquez
 */
public class Enemy extends Entity{
    /** The item that the enemy is holding */
    private Item item;

    /**
     * Creates an enemy with the specific attributes
     * @param name      The name of the enemy
     * @param level     The level of the enemy
     * @param maxHp     The level of the enemy
     * @param item      The item of the enemy
     */
    public Enemy(String name, int level, int maxHp, Item item){
        super(name, level, maxHp);
        this.item = item;
    }
    /**
     * Attack an entity
     * @param e the entity to be attacked
     */
    @Override
    public void attack(Entity e){
        final int CONSTANT_DAMAGE = 3;
        int attackPower = (int) (Math.random() * e.getLevel()) + CONSTANT_DAMAGE;
        e.takeDamage(attackPower);
    }
    /**
     * Retrieve the enemy item
     * @return the enemy item
     */
    Item getItem(){
        return item;
    }

    public static void main(String[] args) {
        Enemy enemy = new Enemy("Stormtrooper", 2, 10, new Item("Holocron"));
        enemy.display();
    }
}
