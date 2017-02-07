package cmput301t4.gameswap;

import junit.framework.TestCase;

import java.util.ArrayList;

import cmput301t4.gameswap.Managers.TradeManager;
import cmput301t4.gameswap.Models.Item;
import cmput301t4.gameswap.Models.Trade;
import cmput301t4.gameswap.Models.TradeList;

/**
 * Created by kynan on 11/1/15.
 */
public class Trade_noServerTest extends TestCase{

    //=====Basic Trade Test (No Trade Controller)=====//
    /*
    Test written does not items out of inventory and then add them into trade
    */

    //Test add trade to tradelist
    public void testAddTradeToTradelist() {
        TradeList tradeList = new TradeList();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
       // tradeList.add(trade);
       // assertTrue(tradeList.hasTrade(trade));
    }//End testAddTradeToTradelist

    //Test del 1 trade from tradelist (Del take Index not obj)
    public void testDelTradeFromTradelist() {
        TradeList tradeList = new TradeList();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade_1 = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //tradeList.add(trade_1);
        //assertTrue(tradeList.hasTrade(trade_1));
        tradeList.del(0);
        //assertFalse(tradeList.hasTrade(trade_1));
    }//End testDelTradeFromTradelist

    //Test del 1 item from inventory of 2 item Del take Index not obj)
    public void testDelItemToInventory2() {
        TradeList tradeList = new TradeList();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade_1 = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //Trade trade_2 = new Trade("Borrower", "Owner", b_tradeitem, o_tradeitem);
        //tradeList.add(trade_1);
        //tradeList.add(trade_2);
        //assertTrue(tradeList.hasTrade(trade_1));
        //assertTrue(tradeList.hasTrade(trade_2));
        tradeList.del(1);
        //assertTrue(tradeList.hasTrade(trade_1));
        //assertFalse(tradeList.hasTrade(trade_2));
    }//End testDelItemToInventory2

    //Test if tradelist has X trade
    public void testTradelistHasTrade(){
        TradeList tradeList = new TradeList();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade_1 = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //tradeList.add(trade_1);
        Trade returntrade_1 = tradeList.getTrade(0);
       // assertTrue(trade_1.getOwnername().equals(returntrade_1.getOwnername()));
        //assertTrue(trade_1.getBorrowerName().equals(returntrade_1.getBorrowerName()));
        //assertTrue(trade_1.getOwnerItems().equals(returntrade_1.getOwnerItems()));
        //assertTrue(trade_1.getBorrowerItems().equals(returntrade_1.getBorrowerItems()));
        //assertTrue(trade_1.getDateTransaction().equals(returntrade_1.getDateTransaction()));
    }//End testTradelistHasTrade

    //=====Basic Trade Test (With Trade Controller)=====//

    public void testC_AddTradeToTradelist() {
        TradeManager TM = new TradeManager();
        TM.clearTradelist();
        TradeList testing = new TradeList(); //suedo friendlist
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        ArrayList<Item> c_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        c_tradeitem.add(item_1);
        assertTrue(o_tradeitem.equals(c_tradeitem));
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //testing.add(trade); //Add item to suedo friendlist
        //TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        assertFalse(TM.getCurrent().isEmpty());
        assertTrue(TM.getCurrent().getTrade(0).getOwnername().equals("Owner"));
        assertTrue(TM.getCurrent().getTrade(0).getBorrowerName().equals("Borrower"));
        assertTrue(TM.getCurrent().getTrade(0).getOwnerItems().equals(o_tradeitem));
        assertTrue(TM.getCurrent().getTrade(0).getBorrowerItems().equals(b_tradeitem));
        assertFalse(TM.getCurrent().isEmpty());
       // assertTrue(testing.getTrade(0).equals(trade));
        //assertTrue(TM.getCurrent().hasTrade(trade));
        //assertTrue(TM.hasTrade(trade));
        TM.clearTradelist();
    }//End testC_AddTradeToTradelist

    //Test del 1 trade from tradelist (Del take Index not obj)
    public void testC_DelTradeFromTradelist() {
        TradeManager TM = new TradeManager();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
       // TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //assertTrue(TM.getCurrent().hasTrade(trade));
        TM.delTrade(0);
        //assertFalse(TM.hasTrade(trade));
        TM.clearTradelist();
    }//End testDelTradeFromTradelist

    //Test del 1 item from inventory of 2 item Del take Index not obj)
    public void testC_DelItemToInventory2() {
        TradeManager TM = new TradeManager();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        //Trade trade_1 = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //Trade trade_2 = new Trade("Borrower", "Owner", b_tradeitem, o_tradeitem);
        //TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //TM.createTrade("Borrower", "Owner", b_tradeitem, o_tradeitem);
       // assertTrue(TM.getCurrent().hasTrade(trade_1));
       // assertTrue(TM.getCurrent().hasTrade(trade_2));
       // TM.delTrade(1);
       /// assertTrue(TM.getCurrent().hasTrade(trade_1));
       // assertFalse(TM.getCurrent().hasTrade(trade_2));
        TM.clearTradelist();
    }//End testC_DelItemToInventory2

    //Test if tradelist has X trade
    public void testC_TradelistHasTrade(){
        TradeManager TM = new TradeManager();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
       // TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        Trade returntrade_1 = TM.getCurrent().getTrade(0);
        assertTrue(TM.getCurrent().getTrade(0).getOwnername().equals(returntrade_1.getOwnername()));
        assertTrue(TM.getCurrent().getTrade(0).getBorrowerName().equals(returntrade_1.getBorrowerName()));
        assertTrue(TM.getCurrent().getTrade(0).getOwnerItems().equals(returntrade_1.getOwnerItems()));
        assertTrue(TM.getCurrent().getTrade(0).getBorrowerItems().equals(returntrade_1.getBorrowerItems()));
        //assertTrue(TM.getCurrent().getTrade(0).getDateTransaction().equals(returntrade_1.getDateTransaction()));
        TM.clearTradelist();
    }//End testC_TradelistHasTrade

    public void testC_MoveTrade(){
        TradeManager TM = new TradeManager();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        assertTrue(o_tradeitem.contains(item_1));
        assertTrue(b_tradeitem.contains(item_2));
        //Trade trade = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        TM.moveTrade(0);
        //assertFalse(TM.getCurrent().hasTrade(trade));
        //assertTrue(TM.getPast().hasTrade(trade));
    }//end testC_MoveTrade




}//end Trade_noServerTest
