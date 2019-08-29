/**
 * Entity class - Representation of a generic type of entity
 * @author Sergio Vasquez
 */
public abstract class Entity {
    /** The name of the entity */
    private String name;
    /** The current level of the entity */
    private int level;
    /** The maximum health of the entity */
    private int maxHp;
    /** The current health of the entity */
    private int hp;
    /**
     * Constructor - sets the attributes of the entity
     * @param n sets the name of the entity
     * @param l sets the level of the entity
     * @param m sets the max health of the entity
     */
    public Entity(String n, int l, int m){
        name = n;
        level = l;
        maxHp = m;
        hp = maxHp;
    }
    /**
     *
     * @param e
     */
    abstract void attack(Entity e);
    /**
     * Retrieve the entity's level
     * @return  the entity's level
     */
    int getLevel(){
        return level;
    }
    /**
     * Retrieve the entity's health
     * @return the entity's health
     */
    int getHP(){
        return hp;
    }
    /**
     * Retrieve the entity's max health
     * @return the entity's max health
     */
    int getMaxHP(){
        return maxHp;
    }
    /**
     *
     */
    void increaseLevel(){

    }
    /**
     *
     * @param h
     */
    void heal(int h){

    }
    /**
     *
     * @param h
     */
    void takeDamage(int h){

    }
    /**
     *
     * @param h
     */
    void increaseMaxHP(int h){

    }
    /**
     *
     * @param h
     */
    void decreaseMaxHP(int h){

    }
    /**
     *
     */
    void display(){

    }
}
