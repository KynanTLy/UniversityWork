package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301t4.gameswap.Managers.CreateTradeManager;
import cmput301t4.gameswap.Managers.FriendManager;
import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.TradeManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.R;

public class OfferTradeActivity extends Activity {

    private ListView myInventoryItemsListView;
    private ListView friendInventoryItemsListView;
    private ArrayAdapter<String> myadapter;
    private ArrayAdapter<String> friendAdapter;
    private ArrayList<String> myItems;
    private ArrayList<String> friendItems;
    private TextView friendName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //clears trade offers
        CreateTradeManager.clearOwnerSide();
        CreateTradeManager.clearFriendSide();
        setContentView(R.layout.activity_offer_trade);
        myInventoryItemsListView = (ListView) findViewById(R.id.itemsFromMyInventory);
        friendInventoryItemsListView = (ListView) findViewById(R.id.itemsFromFriendInventory);
        myadapter = new ArrayAdapter<String>(this, R.layout.myselecteditemstext, CreateTradeManager.getOwnerSideName());
        friendAdapter = new ArrayAdapter<String>(this, R.layout.friendselecteditemstext, CreateTradeManager.getFriendSideName());
        myInventoryItemsListView.setAdapter(myadapter);
        friendInventoryItemsListView.setAdapter(friendAdapter);

        //sets name of friends inventory to the actual name of friend
        friendName = (TextView) findViewById(R.id.friendofferinventory);
        friendName.setText(UserManager.getFriend().getUserName() + "'s Inventory");

    }

    private void resetAdapter(){
        myadapter = new ArrayAdapter<String>(this, R.layout.myselecteditemstext, CreateTradeManager.getOwnerSideName());
        friendAdapter = new ArrayAdapter<String>(this, R.layout.friendselecteditemstext, CreateTradeManager.getFriendSideName());
        myInventoryItemsListView.setAdapter(myadapter);
        friendInventoryItemsListView.setAdapter(friendAdapter);

    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("on resume has run");
        resetAdapter();
    }

    public void friendInventoryButtonClicked(View v) {
        Intent intent = new Intent(OfferTradeActivity.this, SelectFromFriendInventoryActivity.class);
        startActivity(intent);
    }

    public void myInventoryButtonClicked(View v) {
        Intent intent = new Intent(OfferTradeActivity.this, MineInventoryActivity.class);
        startActivity(intent);
    }

    public void offerTradeClicked(View v){
        Intent intent2 = getIntent();
        Bundle b = intent2.getExtras();
        if (b!=null){
            UserManager.getFriend().getPastTrades().add(TradeManager.getCurrent().getTrade(b.getInt("index")));
            UserManager.getFriend().getPendingTrades().delete(TradeManager.getCurrent().getTrade(b.getInt("index")));
            TradeManager.moveTrade(b.getInt("index"));
            CreateTradeManager.clearFriendSide();
            CreateTradeManager.clearOwnerSide();
            //Toast.makeText(getBaseContext(), "Offering Trade", Toast.LENGTH_SHORT).show();
            ServerManager.saveUserOnline(UserManager.getTrader());
            ServerManager.saveUserOnline(UserManager.getFriend());
            Intent intent = new Intent(OfferTradeActivity.this, TradesActivity.class);
            ServerManager.notifyTrade(1);
            startActivity(intent);
            finish();

        }else {
        TradeManager.createTrade(UserManager.getTrader().getUserName(), UserManager.getFriend().getUserName(), CreateTradeManager.getOwnerSide(), CreateTradeManager.getFriendSide());

        CreateTradeManager.clearFriendSide();
        CreateTradeManager.clearOwnerSide();
        //Toast.makeText(getBaseContext(), "Offering Trade", Toast.LENGTH_SHORT).show();
        ServerManager.saveUserOnline(UserManager.getTrader());
        Intent intent = new Intent(OfferTradeActivity.this, TradesActivity.class);
        ServerManager.notifyTrade(0);
        startActivity(intent);
        finish();
        }

    }

    //===========Function needed for TestCases=========//

    public Button getFriendInventoryButton(){
        return (Button) findViewById(R.id.friendInventoryButton);
    }

    public Button getOfferTradeButton(){
        return (Button) findViewById(R.id.counterSaveButton);
    }

    //===========End Function needed for TestCases=========//
}
