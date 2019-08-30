/**
 * @author Sergio Vasquez
 * Force Enemy Class - Representation of a single Force Enemy
 */
public class ForceEnemy extends Enemy implements Force {
    /**
     * Creates an enemy with the specific attributes
     *
     * @param name  The name of the enemy
     * @param level The level of the enemy
     * @param maxHp The level of the enemy
     * @param item  The item of the enemy
     */
    public ForceEnemy(String name, int level, int maxHp, Item item) {
        super(name, level, maxHp, item);
    }
    /**
     * Perform a force push
     * @return the attack power of the force push
     */
    @Override
    public int forcePush() {
        double prob = Math.random();
        final double THRESHOLD = 0.5;
        /* return different damage amount based on if probability reached certain threshold */
        if(Double.compare(prob, THRESHOLD) < 0){
            return 3;
        } else if(Double.compare(prob, THRESHOLD) > 1){
            return 12;
        } else {
            return 5;
        }
    }
    /**
     * Perform a force choke
     * @return the attack power of the force choke */
    @Override
    public int forceChoke() {
        return 7;
    }
    /**
     * Perform a force slam
     * @return the attack power of the force slam
     */
    @Override
    public int forceSlam() {
        int damage = (int) (Math.random() * getLevel()) + 1;
        double prob = Math.random();
        final double THRESHOLD = 0.5;
        /* return different damage amount based on if probability reached certain threshold */
        if (Double.compare(prob, THRESHOLD) < 0) {
            return 1;
        } else {
            return damage * 3;
        }
    }
    /**
     * Attack an entity
     * @param e the entity to be attacked
     */
    @Override
    public void attack(Entity e){
        /* Apply the damage of a regular enemy */
        super.attack(e);
        int extraDamage = (int) (Math.random() * getLevel())  + 1;
        e.takeDamage(extraDamage);
    }
}
