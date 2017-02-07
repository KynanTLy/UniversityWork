package cmput301t4.gameswap.Managers;

import java.util.ArrayList;

import cmput301t4.gameswap.Models.Inventory;
import cmput301t4.gameswap.Models.Item;

/**
 * Created by Rupehra on 2015-11-29.
 */
public class CreateTradeManager {

    private static Inventory OwnerSide = null;
    private static Inventory FriendSide = null;
    private static ArrayList<String> ownerSideName;
    private static ArrayList<String> friendSideName;

    //=====Singleton Code=====//
    static public Inventory getOwnerSide(){
        if (OwnerSide == null){
            OwnerSide = new Inventory();
        }
        return OwnerSide;
    }//end getOwnerSide

    static public Inventory getFriendSide(){
        if (FriendSide == null){
            FriendSide = new Inventory();
        }
        return FriendSide;
    }//end getFriendSide

    static public ArrayList<String> getOwnerSideName(){
        if (ownerSideName == null){
            ownerSideName = new ArrayList<String>();
        }
        return ownerSideName;
    }//end getOwnerSide

    static public ArrayList<String> getFriendSideName(){
        if (friendSideName == null){
            friendSideName = new ArrayList<String>();
        }
        return friendSideName;
    }//end getFriendSide

    //=====End Singleton Code======//

    static public Inventory clearOwnerSide(){
        OwnerSide = new Inventory();
        updateOwnerSideName();
        return OwnerSide;
    }

    static public Inventory clearFriendSide(){
        FriendSide = new Inventory();
        updateFriendSiderName();
        return FriendSide;
    }

    static public void setOwnerName(ArrayList<String> previous){
        ownerSideName = previous;
    }

    static public void setBorrowerName(ArrayList<String> previous){
        friendSideName = previous;
    }

    static public Inventory setOwnerSide(Inventory inventory){
        OwnerSide = inventory;
        return inventory;
    }

    static public Inventory setFriendSide(Inventory inventory){
        FriendSide = inventory;
        return inventory;
    }

    static public Boolean OwnerSideContian(Item item){
        for(int i = 0; i < getOwnerSide().size(); i++){
            if(item.getItemid() == (getOwnerSide().getItem(i).getItemid())) {
                return true;
            }
        }
        return false;
    }

    static public Boolean FriendSideContain(Item item){
        for(int i = 0; i < getFriendSide().size(); i++){
            if(item.getItemid() == ( getFriendSide().getItem(i).getItemid())){
                return true;
            }
        }
        return false;
    }

    static public void addOwnerSide(Item item){
        getOwnerSide().add(item);
    }//end addOwnerSide

    static public void addFriendSide(Item item){
        getFriendSide().add(item);
    }//end addFriendSide

    static public void updateOwnerSideName(){
        ownerSideName = getOwnerSide().getItemsNames();
    }

    static public void updateFriendSiderName(){
        friendSideName = getFriendSide().getItemsNames();
    }

}
