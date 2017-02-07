package cmput301t4.gameswap;

import junit.framework.TestCase;

import java.util.ArrayList;

import cmput301t4.gameswap.Managers.InventoryManager;
import cmput301t4.gameswap.Models.Inventory;
import cmput301t4.gameswap.Models.Item;

public class InventoryTest extends TestCase {

    //=====Basic Inventory Test (No Inventory Controller)=====//

    //Test add 1 item to inventory
    public void testAddItemToInventory() {
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        assertTrue(inventory.hasItem(item_1));
    }//End testADdItemToInventory

    //Test del 1 item from inventory (Del take Index not obj)
    public void testDelItemToInventory() {
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        assertTrue(inventory.hasItem(item_1));
        inventory.del(0);
        assertFalse(inventory.hasItem(item_1));
    }//End testADdItemToInventory

    //Test del 1 item from inventory of 2 item (Del take Index not obj)
    public void testDelItemToInventory2() {
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        inventory.add(item_1);
        inventory.add(item_2);
        assertTrue(inventory.hasItem(item_1));
        assertTrue(inventory.hasItem(item_2));
        inventory.del(1);
        assertTrue(inventory.hasItem(item_1));
        assertFalse(inventory.hasItem(item_2));
    }//End testADdItemToInventory

    //Test if inventory has X item
    public void testInventoryHasItem(){
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        assertTrue(inventory.hasItem(item_1));
        Item returnItem_1 = inventory.getItem(0);
        assertTrue((item_1.getName().equals(returnItem_1.getName())));
        assertTrue((item_1.getReleaseDate().equals(returnItem_1.getReleaseDate())));
        assertTrue(item_1.getIsPrivate().equals(returnItem_1.getIsPrivate()));
        assertTrue(item_1.getQuality().equals(returnItem_1.getQuality()));
        assertTrue(item_1.getPlatform().equals(returnItem_1.getPlatform()));
        assertTrue(item_1.getDescription().equals(returnItem_1.getDescription()));
    }//End testInventoryHasItem

    //Test Replace 1 existing inventory item with a new one
    public void testReplaceItemInInventory(){
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        assertTrue(inventory.hasItem(item_1));
        Item edit_item = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        //==Used 0 for now, later on need a method to get index==//
        inventory.replace(edit_item, 0);
        assertTrue(inventory.hasItem(edit_item));
        assertFalse(inventory.hasItem(item_1));
    }//end testEditItemInInventory

    //=====Advance Inventory Test (No Inventory Controller)=====//

    //test bulk delete from inventory
    public void testBulkDel(){
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        Item item_3 = new Item("Call of Happy", "03-03-3000", true, 9, 7, "BOO");
        Item item_4 = new Item("Call of Whatever", "04-04-5000", false, 3, 12, "BEST THING EVAR");
        inventory.add(item_1);
        inventory.add(item_2);
        inventory.add(item_3);
        inventory.add(item_4);
        ArrayList<Integer> delItemAtIndex = new ArrayList<Integer>();
        delItemAtIndex.add(1);
        delItemAtIndex.add(3);
        inventory.bulkdel(delItemAtIndex);
        assertTrue(inventory.hasItem(item_1));
        assertFalse(inventory.hasItem(item_2));
        assertTrue(inventory.hasItem(item_3));
        assertFalse(inventory.hasItem(item_4));
    }//end testBulkDel

    //Test editing an item's parameter
    public void testEditItemParameter(){
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        assertTrue(inventory.hasItem(item_1));
        inventory.getItem(0).setDescription("Never mind it is terrible");
        inventory.getItem(0).setPublic();
        inventory.getItem(0).setQuality(4);
        assertTrue((item_1.getDescription().equals("Never mind it is terrible")));
        assertTrue((item_1.getIsPrivate().equals(true)));
        assertTrue((item_1.getQuality().equals(4)));
    }//end testEditItemParameter

    //Test editing an item's parameter (assume we know index)
    /*So in the test above it edits each item attribute individually
    In this case, as long as we have a value for each attribute we can
    run editItem function to do everything. Removing the need to run multiple line
    of "inventory.getItem.set...." over and over again
    */
    public void testEditItemInOneTime(){
        Inventory inventory = new Inventory();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        inventory.add(item_1);
        assertTrue(inventory.hasItem(item_1));
        //==Attribute Change==//
        String name = "Doom-a-genton";
        Integer quality = 46;
        //==Attribute No Change==//
        String date = inventory.getItem(0).getReleaseDate();
        boolean isPrivate = inventory.getItem(0).getIsPrivate();
        Integer platform = inventory.getItem(0).getPlatform();
        String description = inventory.getItem(0).getDescription();
        inventory.editItem(name, date, isPrivate, quality, platform, description, 0);
        //==This should fail as we changed some parameter==//
        assertFalse(inventory.hasItem(item_1));
        assertTrue((inventory.getItem(0).getName().equals(name)));
        assertTrue((inventory.getItem(0).getQuality().equals(quality)));
    }//end testEditItemInOneTime

    //=====Basic Inventory Test (With Inventory Controller)=====//
    /*
    Note: Added IM.clearInventory to send of each test as the previous
    testcases' inventory carried across testcases
     */

    public void testC_AddItemToInventory() {
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(IM.hasItem(item_1));
        IM.clearInventory();
    }//End testADdItemToInventory

    //Test del 1 item from inventory (Del take Obj not Index)
    public void testC_DelItemToInventory() {
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(IM.hasItem(item_1));
        IM.delItem(0);
        assertFalse(IM.hasItem(item_1));
        IM.clearInventory();
    }//End testADdItemToInventory

    //Test del 1 item from inventory of 2 item Del take Obj not Index)
    public void testC_DelItemToInventory2() {
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        assertTrue(IM.hasItem(item_1));
        assertTrue(IM.hasItem(item_2));
        IM.delItem(1);
        assertTrue(IM.hasItem(item_1));
        assertFalse(IM.hasItem(item_2));
        IM.clearInventory();
    }//End testADdItemToInventory

    //Test if inventory has X item (Del take Obj not Index)
    public void testC_InventoryHasItem(){
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(IM.hasItem(item_1));
        Item returnItem_1 = IM.getItem(0);
        assertTrue((item_1.getName().equals(returnItem_1.getName())));
        assertTrue((item_1.getReleaseDate().equals(returnItem_1.getReleaseDate())));
        assertTrue(item_1.getIsPrivate().equals(returnItem_1.getIsPrivate()));
        assertTrue(item_1.getQuality().equals(returnItem_1.getQuality()));
        assertTrue(item_1.getPlatform().equals(returnItem_1.getPlatform()));
        assertTrue(item_1.getDescription().equals(returnItem_1.getDescription()));
        IM.clearInventory();
    }//End testInventoryHasItem

    //Test Replace existing inventory item with a new one
    public void testC_ReplaceItemInInventory(){
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(IM.hasItem(item_1));
        Item edit_item = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        //==Used 0 for now, later on need a method to get index==//
        IM.replaceItem(edit_item, 0);
        assertTrue(IM.hasItem(edit_item));
        assertFalse(IM.hasItem(item_1));
        IM.clearInventory();
    }//end testEditItemInInventory

    //=====Advance Inventory Test (With Inventory Controller)=====//

    //test bulk delete from inventory
    public void testC_BulkDel(){
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        Item item_3 = new Item("Call of Happy", "03-03-3000", true, 9, 7, "BOO");
        Item item_4 = new Item("Call of Whatever", "04-04-5000", false, 3, 12, "BEST THING EVAR");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        IM.addItem("Call of Happy", "03-03-3000", true, 9, 7, "BOO");
        IM.addItem("Call of Whatever", "04-04-5000", false, 3, 12, "BEST THING EVAR");
        ArrayList<Integer> delItemAtIndex = new ArrayList<Integer>();
        delItemAtIndex.add(0);
        delItemAtIndex.add(2);
        IM.bulkDel(delItemAtIndex);
        assertFalse(IM.hasItem(item_1));
        assertTrue(IM.hasItem(item_2));
        assertFalse(IM.hasItem(item_3));
        assertTrue(IM.hasItem(item_4));
        IM.clearInventory();
    }//end testBulkDel

    //Test editing an item's parameter
    public void testC_EditItemParameter(){
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(IM.hasItem(item_1));
        IM.getItem(0).setDescription("Never mind it is terrible");
        IM.getItem(0).setPublic();
        IM.getItem(0).setQuality(4);
        assertTrue((IM.getItem(0).getDescription().equals("Never mind it is terrible")));
        assertTrue((IM.getItem(0).getIsPrivate().equals(true)));
        assertTrue((IM.getItem(0).getQuality().equals(4)));
        IM.clearInventory();
    }//end testEditItemParameter

    //Test editing an item's parameter (assume we know index)
    /*So in the test above it edits each item attribute individually
    In this case, as long as we have a value for each attribute we can
    run editItem function to do everything. Removing the need to run multiple line
    of "inventory.getItem.set...." over and over again
    */
    public void testC_EditItemInOneTime(){
        InventoryManager IM = new InventoryManager();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(IM.hasItem(item_1));
        //==Attribute Change==//
        String name = "Doom-a-genton";
        Integer quality = 46;
        //==Attribute No Change==//
        String date = IM.getInstance().getItem(0).getReleaseDate();
        boolean isPrivate = IM.getItem(0).getIsPrivate();
        Integer platform = IM.getItem(0).getPlatform();
        String description = IM.getItem(0).getDescription();
        IM.editItem(name, date, isPrivate, quality, platform, description, 0);
        //==This should fail as we changed some parameter==//
        assertFalse(IM.hasItem(item_1));
        assertTrue((IM.getItem(0).getName().equals(name)));
        assertTrue((IM.getItem(0).getQuality().equals(quality)));
        IM.clearInventory();
    }//end testEditItemInOneTime

}//end InventoryTest