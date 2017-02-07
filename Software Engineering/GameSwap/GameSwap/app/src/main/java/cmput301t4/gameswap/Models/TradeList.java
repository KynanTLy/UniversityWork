package cmput301t4.gameswap.Models;

import java.util.ArrayList;

import cmput301t4.gameswap.Managers.UserManager;

/**
 * Created by kynan on 11/1/15.
 * Model for list of trades that a User will have
 */
public class TradeList {
    /** The trade list of all current and past trades of the user */
    private ArrayList<Trade> tradelist = new ArrayList<Trade>();

    //======Basic Function=====//
    /**
    * Adds a trade to trade list
    * @param trade The trade that was done
    */
    public void add(Trade trade) {
        tradelist.add(trade);
    }//end add to tradelist

    /**
     * removes inputted trade object from tradelist
     * @param trade
     */
    public void delete(Trade trade){
       for(int i = 0; i < UserManager.getFriend().getPendingTrades().getSize(); i++){
           if(trade.equals(UserManager.getFriend().getPendingTrades().getTrade(i))){
               UserManager.getFriend().getPendingTrades().del(i);
           }
       }
    }

    /**
     * Deletes a trade from the trade list
     * @param position The position of hte trade the user wants to delete
     */
    //This del the trade if you have trade object already, most likely will
    //be changed later to take in position
    public void del(int position) {
        tradelist.remove(tradelist.get(position));
    }//end del

    /**
     * Gets the trade the user wished to view from trade list
     * @param index position of the wanted trade
     */
    //Retrieve Trade at index
    public Trade getTrade(int index){
        return tradelist.get(index);
    }//End getTrade

    /**
     * return size of tradelist
     * @return size of trade list
     */
    public int getSize(){return tradelist.size();}

    /**
     * Checks if there is a trade in trade list
     * @param trade The trade that was done
     */
    //See if tradelist contain trade
    public boolean hasTrade(Trade trade) {
        return tradelist.contains(trade);
    }//end hasTrade

    /**
     * Clears the tradelist
     */
    //Wrote this in for testing, Not sure if we really need
    public void clearTradelist(){
        tradelist.clear();
    }//end clearTradelist
    /**
     * Checks if trade list is empty
     */
    public boolean isEmpty(){return tradelist.isEmpty();}//end isEmpty

    public int getIndex(Trade trade){
        return tradelist.indexOf(trade);
    }//end getIndex


    public ArrayList<String> getBorrowerNames() {
        ArrayList<String> name = new ArrayList<String>();
        for (int i=0;i<tradelist.size();i++){
            name.add(tradelist.get(i).getBorrowerName());
        }
        return  name;
    }


    public ArrayList<String> getNames(){
        ArrayList<String> name  = new ArrayList<String>();
        for (int i=0; i < tradelist.size(); i++){
            if (tradelist.get(i).getBorrowerName().equals(UserManager.getTrader().getUserName())){
                name.add("From: " + tradelist.get(i).getOwnername());
            } else {
                name.add("To: " + tradelist.get(i).getBorrowerName());
            }
        }
        return name;
    }



}//end TradeList
