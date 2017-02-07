package cmput301t4.gameswap.Managers;

import java.util.ArrayList;

import cmput301t4.gameswap.Models.Inventory;
import cmput301t4.gameswap.Models.Trade;
import cmput301t4.gameswap.Models.TradeList;

/**
 * Created by kynan on 11/1/15.
 */
public class TradeManager {

    //Singleton Code for currentList
    static public TradeList getCurrent(){
        return UserManager.getPendingList();
    }

    //Singleton Code for pastList
    static public TradeList getPast(){
        return UserManager.getPastList();
    }


    //=====Basic Function=====//

    //Create a new trade
    public static void createTrade(String OwnerName, String BorrowerName, Inventory OwnerItems, Inventory BorrowerItems){
        //String OwnerName, String BorrowerName, ArrayList<Item> OwnerItems, ArrayList<Item> BorrowerItems
        //Trade trade = new Trade(OwnerName, BorrowerName, OwnerItems, BorrowerItems);
        getCurrent().add(new Trade(OwnerName, BorrowerName, OwnerItems, BorrowerItems));

    }//end createTrade

    //Del trade from current Trade (most likely only used offline)
    public static void delTrade(int position){
        getCurrent().del(position);
    }//end deltrade

    //Get trade from list (1 = Current, else = Past)
    public Trade getTrade(int position, int list){
        if(list == 1){
            return getCurrent().getTrade(position);
        } else {
            return getPast().getTrade(position);
        }
    }//end getTrade

    public static Trade getMostRecentTrade(){
        int lastPosition = getCurrent().getSize() - 1;
        return getCurrent().getTrade(lastPosition);
    }


    static public ArrayList<String> getCurrentNames(Boolean trade){
        if (trade == Boolean.TRUE){
            return getCurrent().getNames();
        } else {
            return getPast().getNames();
        }
    }


    //Move trade from current to past
    static public void moveTrade(int position){
        getPast().add(getCurrent().getTrade(position));
        getCurrent().del(position);
    }//end moveTrade

    //check if they have trade object
    /*
    Used more see the friend tradelist has the trade object
    (situtation : counter trade offer)
     */
    public boolean hasTrade(Trade trade){
        return getCurrent().hasTrade(trade);
    }//end hasItem

    //Wrote this in for testing, not sure if we really need
    public void clearTradelist(){
        getCurrent().clearTradelist();
        getPast().clearTradelist();
    }//end clearTradelist


    //==More Advance Function (NOT IMPLEMENTED)==//
    /*
    These will be added once we implment server
    */
    //Del trade from both side (Not yet implemented)
    /*public void TradeCancel(Trade trade){
        find the trade object
        go find the other trader account
        go find the other trader current tradelist
        del from other trader current tradelist
        del from current trader current tradelist
    }//end TradeCancel
    */

    //Create a counter trade
    /*
    public void createCounterTrade(Trade trade, ArrayList<Item> OwnerItem, ArrayList<Item> BorrowerItem){
        createTrade(trade.getOwnername(),trade.getBorrowerName(), OwnerItem, BorrowerItem);
        //TradeCancel(trade);
    }//end createCounterTrade
    */

}//end TradeManager
