package cmput301t4.gameswap.Models;


import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.Collections;

import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.UserManager;

/**
 * Since we might not have an account we might need to shift Account Manager's
 * functionality to User Manager
 *
 * friendList is just a list of other traders
 */
public class User {
    //=====Attribute=====//
    private String userName;
    private String userEmail;
    private String userCity;
    private String userPhoneNumber;
    private Inventory inventory;
    private Location defaultLocation;
    private int itemCounter;
    private FriendList friendList;
    private TradeList pendingTrades;
    private TradeList pastTrades;
    private ArrayList<Integer> notificationAmount;
    private ArrayList<String> notification;

    @Override
    public String toString() {
        return userName;
    }

    public User(String username, String email, String city, String phoneNumber) {
        this.userName = username;
        this.userEmail = email;
        this.userCity = city;
        this.userPhoneNumber = phoneNumber;
        this.itemCounter=0;
        NotificationConstructor();
        ManagerConstructor();
    }//end Trader constructor

    //=====Notification Releated Code=====//

    /**
     * Notification Constructor
     * NotficationAmount Index meaning:
     *  0: new Trade
     *  1: Counter Trade
     *  2: Trade Cancel
     *
     *  Notification Index meaning:
     *  1: new Trade
     *  2: Counter Trade
     *  3: Trade Cancel
     */
    public void NotificationConstructor(){
        this.notification = new ArrayList<String>();
        this.notification.add("You have ");
        this.notification.add(" New Trade");
        this.notification.add(" New Counter Offer");
        this.notification.add(" Trade Cancellation");
        this.notification.add(" Trade Accepted");
        this.notificationAmount = new ArrayList<Integer>(Collections.nCopies(4,0));
    }//end NotificationContructor

    public void IncreaseNotifiyAmount(Integer type){
        this.notificationAmount.set(type, notificationAmount.get(type) + 1);
    }//end IncreaseNotifiyAmount

    public void incrementCounter(){
        itemCounter+=1;
    }
    public int getCounter(){
        return itemCounter;
    }
    // index 0: new Trade 1: Counter Trade 2: Trade Cancel
    public void clearNotificationAmount(){
        this.notificationAmount.set(0,0);
        this.notificationAmount.set(1,0);
        this.notificationAmount.set(2,0);
        this.notificationAmount.set(3,0);
    }//end clearNotification

    public String IfNotify(int i){

        if (notificationAmount.get(i) != 0){
            String message = DisplayNotify(i);
            return message;
        }else {
            return "You have no " + notification.get(i + 1);
        }
    }//end IfNotify

    //Display the notification for one category
    public String DisplayNotify(Integer type){
        System.out.println(notification.get(0) + notificationAmount.get(type) + notification.get(type + 1));
        String message = notification.get(0) + notificationAmount.get(type) + notification.get(type+1);
        ClearNotify(type);
        return message;
    }//end DisplayNotify

    //Just clear noitify (you seen the update)
    public void ClearNotify(Integer type){
        this.notificationAmount.set(type, 0);
        ServerManager.saveUserOnline(UserManager.getTrader());
    }//end ClearNotify

    public ArrayList<Integer> getNotificationAmount() {
        return notificationAmount;
    }

    //=====End of Notification Code=====//

    //=====Manager related code=====//

    public void ManagerConstructor(){


        this.setPastTrades(getPastTrades());
        this.setPendingTrades(getPendingTrades());
        this.setFriendList(getFriendList());
        this.setInventory(getInventory());
    }//end ManagerConstructor


    //=====End Manager related code=====//

    //=====Setters and Getters=====//

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setFriendList(FriendList friendList) {
        this.friendList = friendList;
    }

    public void setPendingTrades(TradeList pendingTrades) {
        this.pendingTrades = pendingTrades;
    }

    public void setPastTrades(TradeList pastTrades) {
        this.pastTrades = pastTrades;
    }

    public Inventory getInventory() {
        if(inventory == null) {
            inventory = new Inventory();
        }

        return inventory;
    }

    public FriendList getFriendList() {
        if(friendList == null) {
            friendList = new FriendList();
        }

        return friendList;
    }

    public TradeList getPendingTrades() {
        if(pendingTrades == null) {
            pendingTrades = new TradeList();
        }

        return pendingTrades;
    }

    public TradeList getPastTrades() {
        if(pastTrades == null) {
            pastTrades = new TradeList();
        }

        return pastTrades;
    }


    public void setDefaultLocation(Location location) {
        defaultLocation = location;
    }

    public Location getDefaultLocation() {

       if (defaultLocation == null){
           defaultLocation = new Location(LocationManager.GPS_PROVIDER);
           defaultLocation.setLatitude(10);
           defaultLocation.setLongitude(10);
       }

        return defaultLocation;
    }

}//end Trader


