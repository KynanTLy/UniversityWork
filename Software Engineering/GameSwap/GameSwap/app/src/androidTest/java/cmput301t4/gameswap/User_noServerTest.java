package cmput301t4.gameswap;

import junit.framework.TestCase;

import java.util.ArrayList;

import cmput301t4.gameswap.Managers.FriendManager;
import cmput301t4.gameswap.Managers.InventoryManager;
import cmput301t4.gameswap.Managers.TradeManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.Models.Item;
import cmput301t4.gameswap.Models.TradeList;
import cmput301t4.gameswap.Models.User;


/**
 * Created by kynan on 11/2/15.
 */
public class User_noServerTest extends TestCase {

    //=====Basic Test (Without Controller)=====//

    /**
     *  Test create a user
     */
    public void testCreateUser(){
        User user = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        assertTrue(user.getUserName().equals("Enter Desired Username"));
        assertTrue(user.getUserEmail().equals("Enter Email"));
        assertTrue(user.getUserCity().equals("Enter City"));
        assertTrue(user.getUserPhoneNumber().equals("Enter Phone Number"));
        assertNull(user.getFriendList());
        assertNull(user.getInventory());
        assertNull(user.getPastTrades());
        assertNull(user.getPendingTrades());
    }//end testCreateUser

    /**
     * Test edit attribute of user
     */
    public void testEditUserInfo(){
        User user = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        assertTrue(user.getUserName().equals("Enter Desired Username"));
        assertTrue(user.getUserEmail().equals("Enter Email"));
        assertTrue(user.getUserCity().equals("Enter City"));
        assertTrue(user.getUserPhoneNumber().equals("Enter Phone Number"));
        user.setUserCity("Hawii");
        assertTrue(user.getUserCity().equals("Hawii"));
    }//end testEditUserInfo

    //=====Basic Test Friend (With Controller)=====//

    /**
     * est add a friend for user
     */
    public void testC_AddFriendForUser(){
        User friend = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        friend.setUserCity("Hawii");
        UserManager UM = new UserManager();
        FriendManager FM = new FriendManager();
        assertNull(UM.getTrader().getFriendList());
        UM.setFriendlist(FM.getFriendlist());
        assertNotNull(UM.getTrader().getFriendList());
        UM.getFriendlist().addFriend(friend.getUserName());
        assertTrue(UM.getFriendlist().getFriend(0).equals(friend.getUserName()));
    }//end testC_AddFriendForUser

    /**
     * Test del a friend for user
     */
    public void testC_DelFriend(){
        User friend = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        friend.setUserCity("Hawii");
        UserManager UM = new UserManager();
        FriendManager FM = new FriendManager();
        UM.getFriendlist().clearFriendList();
        assertTrue(UM.getTrader().getFriendList().isEmpty());
        UM.setFriendlist(FM.getFriendlist());
        assertNotNull(UM.getTrader().getFriendList());
        UM.getFriendlist().addFriend(friend.getUserName());
        assertTrue(UM.getFriendlist().getFriend(0).equals(friend.getUserName()));
        UM.getFriendlist().delFriend(0);
        assertFalse(UM.getFriendlist().hasFriend(friend.getUserName()));
    }//end testC_DelFriend

    /**
     * Test if we can find the inventory of friend
     * Did not accoun for public and private
     */
    public void testC_FindFriendInventory(){
        User friend = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        friend.setUserCity("Hawii");
        UserManager UM = new UserManager();
        FriendManager FM = new FriendManager();
        UM.getFriendlist().clearFriendList();
        assertTrue(UM.getTrader().getFriendList().isEmpty());
        UM.setFriendlist(FM.getFriendlist());
        assertNotNull(UM.getTrader().getFriendList());
        UM.getFriendlist().addFriend(friend.getUserName());
        assertTrue(UM.getFriendlist().getFriend(0).equals(friend.getUserName()));
        //assertNull(UM.getFriendlist().getFriend(0).getInventory());
        //Inventory inv = new Inventory();
        //friend.setInventory(inv);
        //assertNotNull(UM.getFriendlist().getFriend(0).getInventory());
        //assertTrue(UM.getFriendlist().getFriend(0).getInventory().isEmpty());
    }//end testC_FindFriendInventory

    //=====Basic Test Trade (With Controller)=====//

    /**
     * Test if we can find pending trade of user
     */
    public void testC_FindPendingTrade(){
        User friend = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        friend.setUserCity("Hawii");
        UserManager UM = new UserManager();
        TradeManager TM = new TradeManager();
        TM.getCurrent().clearTradelist();
        UM.setPendinglist(TM.getCurrent());
        assertTrue(UM.getTrader().getPendingTrades().isEmpty());
        assertNotNull(UM.getTrader().getPendingTrades());
        TradeList tradeList = new TradeList();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
       // TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
       // Trade trade = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //assertTrue(TM.getTrade(0,1).equals(trade));
    }//end testC_FindPendingTrade


    /**
     * Test if we can find past trade of user
     * Also see if move trade works as intended
     */
    public void testC_FindPastTrade(){
        User friend = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        friend.setUserCity("Hawii");
        UserManager UM = new UserManager();
        TradeManager TM = new TradeManager();
        TM.getPast().clearTradelist();
        TM.getCurrent().clearTradelist();
        UM.setPastlist(TM.getPast());
        assertTrue(UM.getTrader().getPastTrades().isEmpty());
        assertNotNull(UM.getTrader().getPastTrades());
        TradeList tradeList = new TradeList();
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        //TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
       // TM.moveTrade(0);
        //Trade trade = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //assertTrue(TM.getTrade(0,0).equals(trade));
    }//end testC_FindPendingTrade


    //=====Basic Test Inventory (With Controller)=====//

    public void testC_AddItemUserInventory(){
        UserManager UM = new UserManager();
        InventoryManager IM = new InventoryManager();
        UM.setInventory(IM.getInstance());
        UM.getInventory().clearInventory();
        assertTrue(UM.getTrader().getInventory().isEmpty());
        assertNotNull(UM.getTrader().getFriendList());
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(UM.getInventory().getItem(0).equals(item_1));
    }//end testC_AddItemUserInventory

    public void testC_DelItemUserInventory(){
        UserManager UM = new UserManager();
        InventoryManager IM = new InventoryManager();
        UM.setInventory(IM.getInstance());
        UM.getInventory().clearInventory();
        assertTrue(UM.getTrader().getInventory().isEmpty());
        assertNotNull(UM.getTrader().getFriendList());
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        assertTrue(UM.getInventory().getItem(0).equals(item_1));
        IM.delItem(0);
        assertTrue(UM.getInventory().isEmpty());
        assertFalse(UM.getInventory().hasItem(item_1));
    }//end testC_DelItemUserInventory
/*
    //=====Test In Work Notify=====//
    public void testC_Notify(){
        UserManager UM = new UserManager();
        UM.pseduoConstructor();
        System.out.println("Test 1");
        UM.IncreaseNotifiyAmount(0);
        UM.DisplayNotify(0);
    }//testC_Notify
*/
    /**
     * This is not actually how Notifying you friend going to work
     */
    /*
    public void testC_NotifyFriend(){
        UserManager UM = new UserManager();
        FriendManager FM = new FriendManager();
        User friend = new User("Enter Desired Username", "Enter Email", "Enter City", "Enter Phone Number");
        friend.pseduoConstructor();
        friend.clearNotificationAmount();
        System.out.println("Test 2");
        friend.IncreaseNotifiyAmount(1);
        friend.IncreaseNotifiyAmount(2);
        friend.IncreaseNotifiyAmount(2);
        FM.clearFriendlist();
        UM.setFriendlist(FM.getFriendlist());
        FM.addFriend(friend.getUserName());
        //UM.getFriendlist().getFriend(0).IfNotify();
    }//end testC_NotifyFriend
*/
    /**
     * Test finding friend via their name and sending them a notify
     *//*
    public void testC_SendNewTradeNotifyToFriend(){
        System.out.println("Test 3");
        UserManager UM = new UserManager();
        FriendManager FM = new FriendManager();
        User friend = new User("Bob", "Enter Email", "Enter City", "Enter Phone Number");
        friend.pseduoConstructor();
        FM.clearFriendlist();
        UM.setFriendlist(FM.getFriendlist());
        FM.addFriend(friend.getUserName());
        //UM.SendNewTradeNotify(UM.findBorrowerFriend("Bob"));
        //assertTrue(friend.getNotificationAmount().get(0).equals(1));
        //friend.IfNotify();
    }//end testC_SendNotifyToFriend
*/
    /**
     * Test finding a friend add trade + notify
     */
    /*
    public void testC_Send_Add_Trade_NotifyToFriend(){
        System.out.println("Test 4");
        UserManager UM = new UserManager();
        FriendManager FM = new FriendManager();
        TradeManager TM = new TradeManager();
        User friend = new User("Bob", "Enter Email", "Enter City", "Enter Phone Number");
        TradeList friendPending = new TradeList();
        friend.setPendingTrades(friendPending);
        friend.pseduoConstructor();
        FM.clearFriendlist();
        UM.setFriendlist(FM.getFriendlist());
        FM.addFriend(friend.getUserName());
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        assertNotNull(UM.findBorrowerFriend("Bob"));
        //Currently removed due to changes in friendlist
        //UM.SendNewTradeNotify(UM.findBorrowerFriend("Bob"));
        //assertTrue(UM.findBorrowerFriend("Bob").getPendingTrades().isEmpty());
        //UM.findBorrowerFriend("Bob").getPendingTrades().add(new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem));
        //Trade check = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //assertTrue(UM.getPendingList().getTrade(0).equals(check));
        //assertTrue(UM.getFriendlist().getFriend(0).getPendingTrades().getTrade(0).equals(check));
    }//end testC_Send_Add_Trade_NotifyToFriend

    public void testC_CancelTrade(){
        System.out.println("Test 4");
        UserManager UM = new UserManager();
        FriendManager FM = new FriendManager();
        TradeManager TM = new TradeManager();
        User friend = new User("Bob", "Enter Email", "Enter City", "Enter Phone Number");
        TradeList friendPending = new TradeList();
        friend.setPendingTrades(friendPending);
        friend.pseduoConstructor();
        FM.clearFriendlist();
        TM.clearTradelist();
        UM.setFriendlist(FM.getFriendlist());
        UM.setPendinglist(TM.getCurrent());
        FM.addFriend(friend.getUserName());
        ArrayList<Item> o_tradeitem = new ArrayList<Item>();
        ArrayList<Item> b_tradeitem = new ArrayList<Item>();
        Item item_1 = new Item("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        Item item_2 = new Item("Call of Doom", "02-02-1000", true, 2, 5, "It's better than Okay");
        o_tradeitem.add(item_1);
        b_tradeitem.add(item_2);
        TM.createTrade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        assertNotNull(UM.findBorrowerFriend("Bob"));
        //Currently removed due to changes in friendlist
        //UM.SendNewTradeNotify(UM.findBorrowerFriend("Bob"));
        //assertTrue(UM.findBorrowerFriend("Bob").getPendingTrades().isEmpty());
        //UM.findBorrowerFriend("Bob").getPendingTrades().add(new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem));
        //Trade check = new Trade("Owner", "Borrower", o_tradeitem, b_tradeitem);
        //assertTrue(UM.getPendingList().getTrade(0).equals(check));
        //assertTrue(UM.getFriendlist().getFriend(0).getPendingTrades().getTrade(0).equals(check));
        //UM.getPendingList().del(0);
        //UM.findBorrowerFriend("Bob").getPendingTrades().del(0);
        //assertTrue(UM.getPendingList().isEmpty());
        //assertTrue(UM.findBorrowerFriend("Bob").getPendingTrades().isEmpty());
    }//end testC_CancelTrade
*/
    //=====End Test Notify Stuff=====//

    //=====Testing Singleton Stuff=====//
    /*
    We cannot have two exclusive Managers/Controller
     */
    public void testC_TwoSingleton(){
        UserManager UM_1 = new UserManager();
        InventoryManager IM_1 = new InventoryManager();
        UserManager UM_2 = new UserManager();
        InventoryManager IM_2 = new InventoryManager();
        //===Clearing List===//
        UM_1.getInventory().clearInventory();
        UM_2.getInventory().clearInventory();
        UM_1.getFriendlist().clearFriendList();
        //===Test Start===//
        UM_2.getTrader().setUserCity("Edmonton");
        UM_1.setInventory(IM_1.getInstance());
        UM_2.setInventory(IM_2.getInstance());
        IM_1.addItem("Call of Duty", "01-01-2000", false, 5, 5, "It's Okay");
        IM_2.addItem("Doom", "01-01-2000", false, 5, 5, "It's Okay");
        assertFalse(UM_1.getInventory().isEmpty());
        assertFalse(UM_2.getInventory().isEmpty());
        assertTrue(UM_1.getInventory().getItem(0).getName().equals("Call of Duty"));
        assertTrue(UM_2.getInventory().getItem(0).getName().equals("Call of Duty"));
        //assertFalse(UM_1.getInventory().getItem(0).equals(UM_2.getInventory().getItem(0)));
        FriendManager FM_1 = new FriendManager();
        UM_1.setFriendlist(FM_1.getFriendlist());
        //UM_1.getFriendlist().addFriend(UM_2.getTrader());
        //assertTrue(UM_1.getFriendlist().hasFriend(UM_2.getTrader()));
        //assertTrue(UM_1.getFriendlist().getFriend(0).getUserCity().equals("Edmonton"));
        //assertTrue(UM_1.getTrader().getUserCity().equals("Edmonton"));
    }//end testC_TwoSingleton()

    //=====End Test Singleton Stuff=====//
}//end User_noServerTest
