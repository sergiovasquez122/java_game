import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;

public class MapTest {
    @Test
    public void findStartTest(){
        Map map = new Map();
        map.loadMap(1);
        Point p = map.findStart();
        Assert.assertEquals("The point should be at location (2,0) ", p, new Point(2,0));

        map.loadMap(2);
        p = map.findStart();
        Assert.assertEquals("The point should be at location (4,4) ", p, new Point(4,4));

        map.loadMap(3);
        p = map.findStart();
        Assert.assertEquals("The point shoudl be at location (0, 3) ", p, new Point(0,3 ));
    }
}
