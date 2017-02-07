package cmput301t4.gameswap.Models;

import java.util.ArrayList;

/**
 * Holds all offline data until a connection to the server can be established again.
 */
public class Cache {
    /** The Items that were created offline that will be sent to the server */
    ArrayList<Item> itemsToBePushed = new ArrayList<Item>();
    /** The Trades that were created offline that will be sent to the server */
    ArrayList<Trade> tradesToBePushed = new ArrayList<Trade>();
    /** An offline copy of the User object */
    User offlineCopy;
    /** An offline copy of the last friend the user viewed */
    User lastFriend;


    /**
     * Method to add an ArrayList of Items to the Cache
     *
     * @param items An ArrayList of offline Items created by the user
     */
    public void addItemsToCache(ArrayList<Item> items) {
        itemsToBePushed.addAll(items);
    }

    /**
     * Method to add an Item to the Cache
     *
     * @param item An Item that was created offline by the user
     */
    public void addItemToCache(Item item) {
        itemsToBePushed.add(item);
    }

    /**
     * Retrieves all Items that are waiting to be pushed to the server
     *
     * @return The ArrayList of Items waiting to be pushed to the server
     */
	public ArrayList<Item> getPendingItems() {
		return itemsToBePushed;
	}

    /**
     * Method to add an ArrayList of Trades to the Cache
     *
     * @param trades An ArrayList of offline Trades created by the user
     */
    public void addTradesToCache(ArrayList<Trade> trades) {
        tradesToBePushed.addAll(trades);
    }

    /**
     * Method to add a Trade to the Cache
     *
     * @param trade A Trade that was created offline by the user
     */
    public void addTradeToCache(Trade trade) {
        tradesToBePushed.add(trade);
    }

    /**
     * Retrieves all Trades that are waiting to be pushed to the server
     *
     * @return The ArrayList of Trades waiting to be pushed to the server
     */
	public ArrayList<Trade> getPendingTrades() {
		return tradesToBePushed;
	}

    /**
     * Saves an offline copy of the User object in tha Cache
     *
     * @param self The User object to be stored
     */
    public void storeProfile(User self) {
        offlineCopy = self;
    }

    /**
     * Saves the data of the most recently viewed friend in the Cache
     *
     * @param friend The User that was most recently viewed by the user
     */
    public void storeFriend(User friend) {
		lastFriend = friend;
    }
}
