////////////////////////////////////////////////////////////////////
// [Andrea] [Polo-Perucchin] [1169765]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import it.unipd.tos.model.MenuItem.itemType;

public class TestMenuItem {
    
    MenuItem menuItem;

    @Before
    public void before() {
        menuItem = new MenuItem(itemType.FRITTO, "arancini", 3.5);
    }
    
    @Test
    public void testGetPrice_itemGiven_itemPrice() {
        double expectedPriceItem = 3.5;
        assertEquals(expectedPriceItem, menuItem.getPrice(),0.00);
    }
    
    @Test
    public void testGetPrice_itemGiven_itemType() {
        itemType expectedItemType = itemType.FRITTO;
        assertEquals(expectedItemType, menuItem.getItemType());
    }
    
    
}
