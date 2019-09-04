import java.awt.*;
import java.util.ArrayList;

/**
 * Hero Class - Representation of a Hero
 *
 * @author Sergio Vasquez
 */
public class Hero extends Entity implements Force {
    /**
     * Inventory of the Hero
     */
    private ArrayList<Item> items;
    /**
     * The map that the current currently is in
     */
    private Map map;
    /**
     * The current position of the Hero
     */
    private Point location;

    /**
     * Constructor - Constructs a hero at the specified map
     *
     * @param name the name of the hero
     * @param map  the current map
     */
    public Hero(String name, Map map) {
        super(name, 1, 15);
        items = new ArrayList<>();
        location = map.findStart();
        this.map = map;
    }

    public static void main(String[] args) {
        Map map = new Map();
        Hero hero = new Hero("Luke", map);
        hero.pickUpItem(new Item("Chestplate"));
        System.out.println(hero.removeFirstArmorItem());
    }

    /**
     * Attack an entity
     *
     * @param e the entity to be attacked
     */
    public void attack(Entity e) {
        final int BLASTER_DAMAGE = 3;
        int attack_damage = BLASTER_DAMAGE + getLevel();
        String current_attack = "Blaster";
        if (hasHolocron()) {
            System.out.println("1. Use Blaster\n2. Use Force");
            int choice = CheckInput.getIntRange(1, 2);
            if (choice == 2) {
                removeItem("Holocron");
                System.out.println(Force.FORCE_MENU);
                int force_choice = CheckInput.getIntRange(1, 3);
                if (force_choice == 1) {
                    attack_damage = forcePush();
                    current_attack = "Force Push";
                } else if (force_choice == 2) {
                    attack_damage = forceChoke();
                    current_attack = "Force Choke";
                } else {
                    attack_damage = forceSlam();
                    current_attack = "Force Slam";
                }
            }
        }
        System.out.println(getName() + " hits " + e.getName() + " with " + current_attack + " for " + attack_damage + " damage.");
        e.takeDamage(attack_damage);
    }

    /**
     * Display the attributes of the hero
     */
    public void display() {
        super.display();
    }

    /**
     * Display the inventory of the hero
     */
    public void displayItems() {
        System.out.println("Inventory:");
        for (int i = 0; i < items.size(); ++i) {
            System.out.println((i + 1) + ": " + items.get(i).getName());
        }
    }

    /**
     * Retrieve the amount of items the hero is carrying
     *
     * @return the amount of items the hero is carrying
     */
    public int getNumItems() {
        return items.size();
    }

    /**
     * Pick up an item
     *
     * @param i the item to be picked up
     * @return true if an item was picked up
     */
    public boolean pickUpItem(Item i) {
        if (items.size() == 5) {
            return false;
        }
        items.add(i);
        return true;
    }

    /**
     * Remove a item with the given name
     *
     * @param name the name of the item to be removed
     * @return a item with the given name
     */
    public Item removeItem(String name) {
        for (int i = 0; i < items.size(); ++i) {
            Item item = items.get(i);
            if (name.equals(item.getName())) {
                return items.remove(i);
            }
        }
        throw new IllegalArgumentException("That item does not exist");
    }

    /**
     * Remove the item at the indicated index
     *
     * @param index the index of the item to be removed
     * @return the item at the specified index
     */
    public Item removeItem(int index) {
        return items.remove(index);
    }

    /**
     * Checks if the hero has a Med Kit
     *
     * @return true if the hero has a Med Kit
     */
    public boolean hasMedKit() {
        for (Item item : items) {
            String itemName = item.getName();
            if (itemName.equals("Med Kit")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the hero has a key
     *
     * @return true if the hero has a key
     */
    public boolean hasKey() {
        for (Item item : items) {
            String itemName = item.getName();
            if (itemName.equals("Key")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the hero has armor
     *
     * @return true if the hero has armor
     */
    public boolean hasArmor() {
        for (Item item : items) {
            String itemName = item.getName();
            if (itemName.equals("Helmet") || itemName.equals("Shield") || itemName.equals("Chestplate")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the hero has a Holocron
     *
     * @return true if the hero has a Holocron
     */
    public boolean hasHolocron() {
        for (Item item : items) {
            String itemName = item.getName();
            if (itemName.equals("Holocron")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the hero's location
     *
     * @return the Point indicating the hero's location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Move the hero to the north
     *
     * @return the character to the north of the hero
     */
    public char goNorth() {
        // Try to move north if not possible then move back to where you were
        try {
            map.reveal(location);
            int x = location.x - 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        } catch (IndexOutOfBoundsException e) {
            int x = location.x + 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        }
    }

    /**
     * Move the hero the south
     *
     * @return the character to the south of the hero
     */
    public char goSouth() {
        // Try to move south if not possible then move back to where you were
        try {
            map.reveal(location);
            int x = location.x + 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        } catch (IndexOutOfBoundsException e) {
            int x = location.x - 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        }
    }

    /**
     * Move the hero to the east
     *
     * @return the character to the east of the hero
     */
    public char goEast() {
        try {
            // Try to move east if not possible then move back to where you were
            map.reveal(location);
            int y = location.y + 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        } catch (IndexOutOfBoundsException e) {
            int y = location.y - 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        }
    }

    /**
     * Move the hero to the west
     *
     * @return the character to the west of the hero
     */
    public char goWest() {
        try {
            // Try to move West if not possible then move back to where you were
            map.reveal(location);
            int y = location.y - 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        } catch (IndexOutOfBoundsException e) {
            int y = location.y + 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        }
    }

    /**
     * Perform a force push
     *
     * @return the attack power of the force push
     */
    @Override
    public int forcePush() {
        double prob = Math.random();
        final double THRESHOLD = 0.5;
        /* Force Choke is medium-risk medium-reward attack
         * deal medium damage if doesn't pass threshold of success */
        if (Double.compare(prob, THRESHOLD) < 0) {
            final int MEDIUM_DAMAGE = 3;
            return MEDIUM_DAMAGE * getLevel();
        } else {
            final int HIGH_DAMAGE = 5;
            return HIGH_DAMAGE * getLevel();
        }
    }

    /**
     * Perform a force choke
     *
     * @return the attack power of the force choke
     */
    @Override
    public int forceChoke() {
        final int MULTIPLER = 2;
        return MULTIPLER * getLevel();
    }

    /**
     * Remove the first item in the hero inventory
     * @name the name of the item removed
     */
    public String removeFirstArmorItem(){
        for(int i = 0;i < items.size();++i){
            Item item = items.get(i);
            if(item.getName().equals("Chestplate") || item.getName().equals("Chestplate") || item.getName().equals("Shield")){
                items.remove(i);
                return item.getName();
            }
        }
        throw new IllegalArgumentException("No item in the list");
    }

    /**
     * Perform a force slam
     *
     * @return the attack power of the force slam
     */
    @Override
    public int forceSlam() {
        int damage = (int) (Math.random() * getLevel()) + 1;
        double prob = Math.random();
        final double THRESHOLD = 0.5;
        /* Force Slam is high-risk high-reward attack
         * deal minimal damage if doesn't pass threshold of success */
        if (Double.compare(prob, THRESHOLD) < 0) {
            final int LOW_DAMAGE = 1;
            return LOW_DAMAGE * getLevel();
        } else {
            return damage * getLevel();
        }
    }
}
