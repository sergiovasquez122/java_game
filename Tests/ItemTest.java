import org.junit.Assert;
import org.junit.Test;

public class ItemTest {
    @Test
    public void testGetName(){
        Item item = new Item("Armor");
        String name = item.getName();
        Assert.assertEquals("The name of the item should be 'Armor'", name, item.getName());
    }
}
