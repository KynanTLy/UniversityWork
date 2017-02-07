package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.R;

public class selectTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_task);
        checkTrades();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_select_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.my_profile){
            Intent intent = new Intent(selectTaskActivity.this, MyProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void openInventoryButton(View view){
        Intent intent = new Intent(selectTaskActivity.this,myInventoryActivity.class);
        startActivity(intent);
    }
    public void openFriendsButton(View view){
        Intent intent = new Intent(selectTaskActivity.this,SearchFriendActivity.class);
        startActivity(intent);
    }
    public void tradeOffersButton(View view){
        Intent intent = new Intent(selectTaskActivity.this,TradesActivity.class);
        startActivity(intent);
    }

    //=====Function needed for Testcases=====//

    public Button getInventoryButton(){
        Button button = (Button) findViewById(R.id.inventory);
        return button;
    }//end getInventoryButton

    public Button getTraderButton(){
        Button button = (Button) findViewById(R.id.friends);
        return button;
    }//end getTraderButton


    //=====End Function needed for Testcases

    public void checkTrades(){
        String message = "";
        for(int i = 0; i < 4; i++){
            if(i == 3){
                message += UserManager.getTrader().IfNotify(i);
            }else{
                message += UserManager.getTrader().IfNotify(i) + "\n";
            }
        }
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
