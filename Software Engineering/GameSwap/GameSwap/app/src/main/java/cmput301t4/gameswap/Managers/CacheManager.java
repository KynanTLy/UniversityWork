package cmput301t4.gameswap.Managers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import cmput301t4.gameswap.Models.Cache;
import cmput301t4.gameswap.Models.Item;
import cmput301t4.gameswap.Models.Trade;
import cmput301t4.gameswap.Models.User;

/**
 * The MVC controller used for all accesses to <code>Cache</code>
 */
public class CacheManager {

    final static String ITEMS_SAVE_FILE = "gameswap_local_items.sav";
    final static String TRADES_SAVE_FILE = "gameswap_local_trades.sav";
    private static Cache cache = null;

    /**
     * Used to get the app-wide singleton of <code>Cache</code>
     *
     * @return The cache used to store pending <code>Trades</code> and <code>Items</code> that
     * were created offline as well as the last friend that was viewed by the user
     */
    static public Cache getInstance() {
        if(cache == null) {
            cache = new Cache();
        }

        return cache;
    }

    /**
     * Stores all cache data to local files for persistence between sessions
     *
     * @param context The <code>Context</code> of the running app
     */
    static public void saveToFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(ITEMS_SAVE_FILE, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(pullItemCache(), out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

        try {
            FileOutputStream fos = context.openFileOutput(TRADES_SAVE_FILE, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(pullTradeCache(), out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

        //TODO: store saved friend to file
        //TODO: Store offline user to file
    }

    /**
     * Used to pull in cache data that was created in a previous app instance
     *
     * @param context The <code>Context</code> of the running app
     */
    private void loadFromFile(Context context) {
        try {
            FileInputStream fis = context.openFileInput(ITEMS_SAVE_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type arraylistType = new TypeToken<ArrayList<Item>>() {}.getType();
            ArrayList<Item> pulledItems = gson.fromJson(in, arraylistType);
            cacheItems(pulledItems);
        } catch (FileNotFoundException e) {
            ArrayList<Item> pulledItems = new ArrayList<Item>();
            cacheItems(pulledItems);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FileInputStream fis = context.openFileInput(TRADES_SAVE_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type arraylistType = new TypeToken<ArrayList<Item>>() {}.getType();
            ArrayList<Trade> pulledTrades = gson.fromJson(in, arraylistType);
            cacheTrades(pulledTrades);
        } catch (FileNotFoundException e) {
            ArrayList<Trade> pulledTrades = new ArrayList<Trade>();
            cacheTrades(pulledTrades);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TODO: load friend from save file
        //TODO: load offline user from file
    }

    /**
     * Adds a single <code>Item</code> to the singleton <code>Cache</code>
     *
     * @param item The <code>Item</code> to be cached
     */
    static public void cacheItem(Item item) {
        getInstance().addItemToCache(item);
    }

    /**
     * Adds an ArrayList of <code>Items</code> to the <code>Cache</code>
     *
     * @param items A <code>ArrayList</code> of <code>Item</code>
     */
    static public void cacheItems(ArrayList<Item> items) {
        getInstance().addItemsToCache(items);
    }

    /**
     * Retrieves the <code>ArrayList</code> of <code>Items</code> that are waiting to
     * be pushed to the server
     *
     * @return An <code>ArrayList</code> of <code>Items</code>
     */
    static public ArrayList<Item> pullItemCache() {
        return getInstance().getPendingItems();
    }

    /**
     * Adds a single <code>Trade</code> to the singleton <code>Cache</code>
     *
     * @param trade The <code>Trade</code> te be cached
     */
    static public void cacheTrade(Trade trade) {
        getInstance().addTradeToCache(trade);
    }

    /**
     * Adds an ArrayList of <code>Trades</code> to the <code>Cache</code>
     *
     * @param trades A <code>ArrayList</code> of <code>Trade</code>
     */
    static public void cacheTrades(ArrayList<Trade> trades) {
        getInstance().addTradesToCache(trades);
    }

    /**
     * Retrieves the <code>ArrayList</code> of <code>Trades</code> that are waiting to
     * be pushed to the server
     *
     * @return An <code>ArrayList</code> of <code>Trades</code>
     */
    static public ArrayList<Trade> pullTradeCache() {
        return getInstance().getPendingTrades();
    }

    /**
     * Stores the offline <code>User</code> in the <code>Cache</code>
     *
     * @param self The <code>User</code> to be saved
     */
    static public void storeProfile(User self) {
        getInstance().storeProfile(self);
    }

    /**
     * Stores the last viewed friend in the <code>Cache</code>
     *
     * @param friend The friend to be saved
     */
    static public void storeLastFriend(User friend) {
        getInstance().storeFriend(friend);
    }
}
