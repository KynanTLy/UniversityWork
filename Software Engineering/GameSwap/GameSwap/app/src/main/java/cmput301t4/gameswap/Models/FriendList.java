package cmput301t4.gameswap.Models;

import java.util.ArrayList;

/**
 * Stores the list of Users that the current user has added as friends
 */
public class FriendList {
    private ArrayList <String> friendList = new ArrayList<String>();

    /**
     * Checks how many friends the current user has
     *
     * @return The number of friends the current user has
     */
    public int getFriendlistSize() {
        return friendList.size();
    }

    /**
     * Stores a User as a friend
     *
     * @param trader The User that the user has added as a friend
     */
    public void addFriend(String trader) {
        friendList.add(trader);
    }

    /**
     * Removes a friend based on the position in the list
     *
     * @param position The position in the list of the friend
     */
    public void delFriend(int position) {
        friendList.remove(position);
    }

    /**
     * Simple getter to retrieve a friend based on their position in the list
     *
     * @param index The position in the list of the friend
     * @return The User at the provided position
     */
    public String getFriend(int index){
        return friendList.get(index);
    }

    /* Setter to set the friendsList
    */
    public void setFriendList(ArrayList<String> friends){friendList = friends;}


    /**
     * Generic getter to retrieve all friends of a user
     *
     * @return The Collection of all friends of that user
     */
   // public Collection<? extends User> getAllFriends() {
    public ArrayList<String> getAllFriends(){
        return friendList;
    }

    /**
     * Check if a User is a friend of the current user
     *
     * @param trader The User to be checked
     * @return A boolean that is true if the provided User is a friend of the current user
     */
    public boolean hasFriend(String trader) {
        return friendList.contains(trader.toLowerCase());
    }

    /**
     * Removes all of the current user's friends
     */
    public void clearFriendList(){
        friendList.clear();
    }

    /**
     * Checks if the current user has any friends :(
     *
     * @return A boolean that is true if the current user has no friends
     */
    public boolean isEmpty(){
        return friendList.isEmpty();
    }

    public int getFriendIndex(String friendName) {
       int i;
        for (i=0;i < friendList.size();i++){
            System.out.println(friendName + " " + i);
            if (friendList.get(i).toLowerCase().equals(friendName.toLowerCase())){
               return i;
            }
        }
        return i;
    }

}//end FriendLis

