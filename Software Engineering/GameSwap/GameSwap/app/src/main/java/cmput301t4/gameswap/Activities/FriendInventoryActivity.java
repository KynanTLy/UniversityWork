package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301t4.gameswap.Managers.CreateTradeManager;
import cmput301t4.gameswap.Managers.InvSearchManager;
import cmput301t4.gameswap.Managers.InventoryManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.Models.Inventory;
import cmput301t4.gameswap.R;

public class FriendInventoryActivity extends Activity {

    private ArrayList<String> itemNamesList;
    private Inventory inventory;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    private int itemID;
    private String Name;
    /** A description of the Item */
    private String Description;
    /** The date when the game was released for purchase */
    private String ReleaseDate;
    /** Quality of the game*/
    private int Quality;
    /*IsPRivate */
    private Boolean IsPrivate;
    /*Platform of the game*/
    private int Platform;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_inventory);
        inventory = InvSearchManager.showFriendInventory(UserManager.getFriend().getInventory());
        itemNamesList = inventory.getItemsNames();
        listView = (ListView) findViewById(R.id.friendInventoryListView);
        adapter = new ArrayAdapter<String>(this,R.layout.friendinventorylistviewtext,itemNamesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Name = UserManager.getFriend().getInventory().getItem(position).getName();
                Description = UserManager.getFriend().getInventory().getItem(position).getDescription();
                ReleaseDate = UserManager.getFriend().getInventory().getItem(position).getReleaseDate();
                itemID = UserManager.getFriend().getInventory().getItem(position).getItemid();
                latitude = UserManager.getFriend().getInventory().getItem(position).getLocation().getLatitude();
                longitude = UserManager.getFriend().getInventory().getItem(position).getLocation().getLongitude();

                final Intent intent = new Intent(FriendInventoryActivity.this, ViewItemActivity.class);
                intent.putExtra("name", Name);
                intent.putExtra("description", Description);
                intent.putExtra("releaseDate", ReleaseDate);
                intent.putExtra("index", position);
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                intent.putExtra("itemId", itemID);
                startActivity(intent);
            }
        });
    }

    public void frienddoneButtonClicked(View v){
        finish();
    }

    //==========Function needed for TestCases===========//

    public ListView getListView(){
        return (ListView) findViewById(R.id.friendInventoryListView);
    }

    //==========End Function needed for TestCases=======//

}//end FriendInventoryActivity
