package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cmput301t4.gameswap.Managers.FriendManager;
import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.R;

public class FriendProfileActivity extends Activity {

    private Button FriendStatusButton;
    private Button friendInventory;
    private Button friendTrade;
    private TextView traderNameTextView;
    private TextView traderCityTextView;
    private TextView traderPhoneTextView;
    private TextView traderEmailTextView;
    private String traderName;
    private Boolean isFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        isFriend = b.getBoolean("isfriend");
        traderNameTextView = (TextView) findViewById(R.id.traderNameTextView);
        traderCityTextView = (TextView) findViewById(R.id.traderCityTextView);
        traderPhoneTextView = (TextView) findViewById(R.id.traderPhoneTextView);
        traderEmailTextView = (TextView) findViewById(R.id.traderEmailTextView);
        FriendStatusButton = (Button)findViewById(R.id.removeTraderButton);
        friendInventory = (Button) findViewById(R.id.friendInventoryButton);
        friendTrade = (Button) findViewById(R.id.friendTradeButton);

        if (isFriend==Boolean.FALSE){
            FriendStatusButton.setText("Add Friend");
            traderNameTextView.setText(UserManager.getFriend().getUserName());
            traderCityTextView.setText("City Unavailable");
            traderPhoneTextView.setText("Phonenumber Unavailable");
            traderEmailTextView.setText("Email Unavailable");
            friendInventory.setEnabled(false);
            friendTrade.setEnabled(false);
            traderName = traderNameTextView.getText().toString();
        } else {
            FriendStatusButton.setText("Remove Friend");
            traderNameTextView.setText(UserManager.getFriend().getUserName());
            traderCityTextView.setText(UserManager.getFriend().getUserCity());
            traderPhoneTextView.setText(UserManager.getFriend().getUserPhoneNumber());
            traderEmailTextView.setText(UserManager.getFriend().getUserEmail());
            traderName = traderNameTextView.getText().toString();
            friendInventory.setEnabled(true);
            friendTrade.setEnabled(true);
        }

    }

    public void removeTraderButtonClicked(View view){
        if (isFriend == Boolean.TRUE){
            int index;
            UserManager.getTrader().getFriendList().hasFriend(UserManager.getFriend().getUserName());
            index = UserManager.getTrader().getFriendList().getFriendIndex(traderName);
            UserManager.getTrader().getFriendList().delFriend(index);
            ServerManager.saveUserOnline(UserManager.getTrader());
            stateswtich(isFriend);
        }
        else {
            FriendManager.addFriend(traderName);
            UserManager.saveUserLocally(this);
            ServerManager.saveUserOnline(UserManager.getTrader());
            stateswtich(isFriend);
        }
    }


    public void tradeButtonClicked(View v){
        //Toast.makeText(getBaseContext(), "Trade", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FriendProfileActivity.this,OfferTradeActivity.class);
        startActivity(intent);
        //finish();
    }

    public void friendInventoryButton(View v){
        Intent intent = new Intent(FriendProfileActivity.this,FriendInventoryActivity.class );
        startActivity(intent);
    }

    public void stateswtich(Boolean areFriend){
        if(areFriend == Boolean.TRUE){
            Toast.makeText(getBaseContext(), traderName + " Removed From FriendList", Toast.LENGTH_SHORT).show();
            traderCityTextView.setText("City Unavailable");
            traderPhoneTextView.setText("Phonenumber Unavailable");
            traderEmailTextView.setText("Email Unavailable");
            FriendStatusButton.setText("Add Friend");
            friendInventory.setEnabled(false);
            friendTrade.setEnabled(false);
            isFriend = Boolean.FALSE;
        } else {
            Toast.makeText(getBaseContext(), traderName + " Added to FriendList", Toast.LENGTH_SHORT).show();
            traderCityTextView.setText(UserManager.getFriend().getUserCity());
            traderPhoneTextView.setText(UserManager.getFriend().getUserPhoneNumber());
            traderEmailTextView.setText(UserManager.getFriend().getUserEmail());
            FriendStatusButton.setText("Remove Friend");
            friendInventory.setEnabled(true);
            friendTrade.setEnabled(true);
            isFriend = Boolean.TRUE;
        }
    }

    //=================Functions needed for Testcases==================//

    public Button getAddFriendButton(){
        return (Button) findViewById(R.id.removeTraderButton);
    }

    public TextView getNameView(){
        return (TextView) findViewById(R.id.traderNameTextView);
    }

    public TextView getCityView(){
        return (TextView) findViewById(R.id.traderCityTextView);
    }

    public TextView getPhoneView(){
        return (TextView) findViewById(R.id.traderPhoneTextView);
    }

    public TextView getEmailView(){
        return  (TextView) findViewById(R.id.traderEmailTextView);
    }

    public Button getInventoryButton(){
        return (Button) findViewById(R.id.friendInventoryButton);
    }

    public Button getTradeButton(){
        return (Button) findViewById(R.id.friendTradeButton);
    }

    //=================End Function needed for Testcases===============//

}
