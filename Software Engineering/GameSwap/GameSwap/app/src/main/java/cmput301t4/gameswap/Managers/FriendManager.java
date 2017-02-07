package cmput301t4.gameswap.Managers;

//commented out getAllUsers
import java.util.ArrayList;

import cmput301t4.gameswap.Models.FriendList;

/**
 * Created by kynan on 11/2/15.
 */
public class FriendManager {
    //=====Singleton Code=====//
    static public FriendList getFriendlist(){
        return UserManager.getFriendlist();
    }

    static public void addFriend(String friend){
        getFriendlist().addFriend(friend);
    }//end addFriend

    //This del the friend , most likely will
    //be changed later to take in position
    static public void delFriend(int position) {getFriendlist().delFriend(position);
    }//end del

    /*
     */
    public void setFriendList(ArrayList<String> friends)
    {getFriendlist().setFriendList(friends);}

    //Retrieve friend at index
    static public String getUser(int index){return getFriendlist().getFriend(index);}//End getUser

    static public ArrayList<String> getAllUsers() {             //getting error in this one so commented it out
       return getFriendlist().getAllFriends();
    }

    //See if friendList contains friend
    static public boolean hasFriend(String trader) {
        return getFriendlist().hasFriend(trader);
    }//end hasFriend

    //Wrote this in for testing, Not sure if we really need
    static public void clearFriendlist(){
        getFriendlist().clearFriendList();
    }//end clearFriendList

    static public int getFriendIndex(String friendName){
        return getFriendlist().getFriendIndex(friendName);
    }

}//end FriendManager
