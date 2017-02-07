package cmput301t4.gameswap;

import junit.framework.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cmput301t4.gameswap.Exceptions.DateFormatException;
import cmput301t4.gameswap.Managers.InvSearchManager;
import cmput301t4.gameswap.Models.Inventory;
import cmput301t4.gameswap.Models.Item;


/**
 * Created by dren on 11/4/15.
 */
public class InvSearchTest extends TestCase {

    public void testFindItemsByName(){
        InvSearchManager searcher = new InvSearchManager();
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Duty", "01-01-2002", false, 5, 5, "It's Okay");
        Item item_3 = new Item("Call of Duty", "01-01-2005", false, 5, 5, "It's Okay");
        Item item_4 = new Item("Call of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        Item item_5 = new Item("Call Not of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        inventory.add(item_2);
        inventory.add(item_3);
        inventory.add(item_4);
        inventory.add(item_5);
        Inventory inventory2 = searcher.searchItembyName(inventory, "Call of Duty");
        assertTrue(inventory2.size() == 4);
        for(int i = 0; i < inventory2.size();i++){
            assertTrue(inventory2.getItem(i).getName().equals("Call of Duty"));
        }
    }

    public void testFindItemsByPlatform(){
        InvSearchManager searcher = new InvSearchManager();
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Duty", "01-01-2002", false, 5, 6, "It's Okay");
        Item item_3 = new Item("Call of Duty", "01-01-2005", false, 5, 7, "It's Okay");
        Item item_4 = new Item("Call of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        Item item_5 = new Item("Call Not of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        inventory.add(item_2);
        inventory.add(item_3);
        inventory.add(item_4);
        inventory.add(item_5);
        Inventory inventory2 = searcher.searchItembyPlatform(inventory, 5);
        assertTrue(inventory2.size() == 3);
        for(int i = 0; i < inventory2.size();i++){
            assertTrue(inventory2.getItem(i).getPlatform() == 5);
        }
    }

    public void testFindItemsByQuality(){
        InvSearchManager searcher = new InvSearchManager();
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Duty", "01-01-2002", false, 6, 6, "It's Okay");
        Item item_3 = new Item("Call of Duty", "01-01-2005", false, 6, 7, "It's Okay");
        Item item_4 = new Item("Call of Duty", "01-01-2008", false, 6, 5, "It's Okay");
        Item item_5 = new Item("Call Not of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        inventory.add(item_2);
        inventory.add(item_3);
        inventory.add(item_4);
        inventory.add(item_5);
        Inventory inventory2 = searcher.searchItembyQuality(inventory, 6);
        assertTrue(inventory2.size() == 3);
        for(int i = 0; i < inventory2.size();i++){
            assertTrue(inventory2.getItem(i).getQuality() == 6);
        }
    }

    public void testFindItemsByDate(){
        InvSearchManager searcher = new InvSearchManager();
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Duty", "01-01-2002", false, 6, 6, "It's Okay");
        Item item_3 = new Item("Call of Duty", "01-01-2005", false, 6, 7, "It's Okay");
        Item item_4 = new Item("Call of Duty", "01-01-2008", false, 6, 5, "It's Okay");
        Item item_5 = new Item("Call Not of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        inventory.add(item_2);
        inventory.add(item_3);
        inventory.add(item_4);
        inventory.add(item_5);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = formatter.parse("01-01-2008");
        } catch (ParseException e) {
            throw new DateFormatException();
        }
        Inventory inventory2 = searcher.searchItembyDate(inventory, date);
        assertTrue(inventory2.size() == 3);
        for(int i = 0; i < inventory2.size();i++){
            assertTrue(inventory2.getItem(i).getReleaseDate().equals(date));
        }
    }

    public void testFindItemsByPrivate(){
        InvSearchManager searcher = new InvSearchManager();
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2008", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Duty", "01-01-2002", false, 6, 6, "It's Okay");
        Item item_3 = new Item("Call of Duty", "01-01-2005", false, 6, 7, "It's Okay");
        Item item_4 = new Item("Call of Duty", "01-01-2008", false, 6, 5, "It's Okay");
        Item item_5 = new Item("Call Not of Duty", "01-01-2008", true, 5, 5, "It's Okay");
        inventory.add(item_1);
        inventory.add(item_2);
        inventory.add(item_3);
        inventory.add(item_4);
        inventory.add(item_5);
        Inventory inventory2 = searcher.searchItembyPrivate(inventory, false);
        assertTrue(inventory2.size() == 4);
        for(int i = 0; i < inventory2.size();i++){
            assertTrue(inventory2.getItem(i).getIsPrivate() == false);
        }
    }
}
