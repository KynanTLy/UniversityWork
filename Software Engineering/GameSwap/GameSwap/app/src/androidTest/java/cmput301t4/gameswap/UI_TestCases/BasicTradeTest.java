package cmput301t4.gameswap.UI_TestCases;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Instrumentation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import cmput301t4.gameswap.Activities.AddItemActivity;
import cmput301t4.gameswap.Activities.CancelCreateTradeActivity;
import cmput301t4.gameswap.Activities.CreateProfileActivity;
import cmput301t4.gameswap.Activities.DecideTradeActivity;
import cmput301t4.gameswap.Activities.FriendInventoryActivity;
import cmput301t4.gameswap.Activities.FriendProfileActivity;
import cmput301t4.gameswap.Activities.MainActivity;
import cmput301t4.gameswap.Activities.OfferTradeActivity;
import cmput301t4.gameswap.Activities.SearchFriendActivity;
import cmput301t4.gameswap.Activities.SelectFromFriendInventoryActivity;
import cmput301t4.gameswap.Activities.TradesActivity;
import cmput301t4.gameswap.Activities.ViewItemActivity;
import cmput301t4.gameswap.Activities.myInventoryActivity;
import cmput301t4.gameswap.Activities.selectTaskActivity;
import cmput301t4.gameswap.R;

/**
 * Created by dren on 12/8/15.
 */
public class BasicTradeTest extends ActivityInstrumentationTestCase2 {


    /**
     * Variables used in MainActivity
     */
    private Button MainActivityLogin;
    private EditText LoginText;

    /**
     * Variables for setup user friend
     */
    private Button MainAcitivtyRegister;
    private Button CreateProfileSaveButton;
    private EditText EditusernameText;
    private EditText EditemailText;
    private EditText EditcityText;
    private EditText EditphonenumberText;

    /**
     * Variables from selectActivity
     */
    private Button SelectFriendButton;

    /**
     * Variables from SearchFriendActivity
     */
    private SearchView searchFriendView;
    private ListView searchFriendListView;

    /**
     * Variables from FriendProfileActivity
     */
    private Button FriendProfileButton;
    private Button FriendTradeButton;

    /**
     * Variables used in AddItemActivity
     */
    private Button AddItemActicityAddItemButton;

    private EditText GameTitleText;
    private EditText ReleaseDateText;
    private EditText DescriptionText;

    private Spinner PlatformSpinner;
    private Spinner QualitySpinner;
    private Spinner StatusSpinner;

    /**
     * Variables used in SelectTaskActivity
     */
    private Button SelectTaskActivityInventory;

    /**
     * Variables used in MyInventoryAcitivity
     */
    private Button MyInventoryActivityAdd;

    /**
     * Variables used in  FriendInventoryActivity
     */
    private ListView FriendInventory;

    /**
     * Variables used in OfferTradeActivity
     */
    private Button OfferFriendButton;
    private Button OfferFriendInventoryButton;

    /**
     * VAriables used in SelectfromFriendActivity
     */
    private ListView SelectFriendInventory;
    private Button SelectFriendDoneButton;

    /**
     * Variables from OfferTradeActivity
     *
     */
    private ListView Currentlistview;

    /**
     * Variables from Decidelist View
     */
    private Button cancelTradeButton;


    public BasicTradeTest() {
        super(cmput301t4.gameswap.Activities.MainActivity.class);
    }//end Constructor

    public void testStartBasicTrade(){
        setupSecondUser();
        setupSecondUserInventory();
        OfferTradeActivity finishSecondUserSetup = setupLogin();
        testStartATrade(finishSecondUserSetup);
    }


    public void testStartATrade(OfferTradeActivity activity){

        OfferFriendInventoryButton = activity.getFriendInventoryButton();
        OfferFriendButton = activity.getOfferTradeButton();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SelectFromFriendInventoryActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                OfferFriendInventoryButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: Nov 16 2015
        // Validate that ReceiverActivity is started
        final SelectFromFriendInventoryActivity SelectfromFriend_Activity = (SelectFromFriendInventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", SelectfromFriend_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                SelectFromFriendInventoryActivity.class, SelectfromFriend_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);


        SelectFriendInventory = SelectfromFriend_Activity.getListView();
        SelectFriendDoneButton = SelectfromFriend_Activity.getDone();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                SelectFriendInventory.getChildAt(0).performClick();
                SelectFriendDoneButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();


        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivityMonitor =
                getInstrumentation().addMonitor(TradesActivity.class.getName(),
                        null, false);


        OfferFriendButton = activity.getOfferTradeButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                OfferFriendButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        TradesActivity Trade_activity = (TradesActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", Trade_activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TradesActivity.class, Trade_activity.getClass());


        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);


    }

    /**
     *
     * @return
     * Runs the setup to Login into a dummy user made for testing
     */
    public OfferTradeActivity setupLogin(){
        MainActivity activity = (MainActivity) getActivity();

        MainActivityLogin = activity.getLoginButton();
        LoginText = activity.getUsernameField();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: November 21
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(selectTaskActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                LoginText.setText("JimBoy2");
                MainActivityLogin.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: Nov 16 2015
        // Validate that ReceiverActivity is started
        selectTaskActivity Select_Activity = (selectTaskActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", Select_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                selectTaskActivity.class, Select_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        SelectFriendButton = Select_Activity.getTraderButton();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(SearchFriendActivity.class.getName(),
                        null, false);

        Select_Activity.runOnUiThread(new Runnable() {
            public void run() {
                SelectFriendButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        SearchFriendActivity  searchFriendActivity_Activity = (SearchFriendActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", searchFriendActivity_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                SearchFriendActivity.class, searchFriendActivity_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        getInstrumentation().invokeMenuActionSync(searchFriendActivity_Activity, R.id.showPeople, 0);
        getInstrumentation().waitForIdleSync();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendProfileActivity.class.getName(),
                        null, false);

        searchFriendView = searchFriendActivity_Activity.getSearchView();

        searchFriendActivity_Activity.runOnUiThread(new Runnable() {
            public void run() {
                searchFriendView.setQuery("dantest2", true);
            }
        });

        getInstrumentation().waitForIdleSync();

        final FriendProfileActivity  friendProfile_activity = (FriendProfileActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendProfile_activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                FriendProfileActivity.class, friendProfile_activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);


        FriendProfileButton = friendProfile_activity.getAddFriendButton();
        FriendTradeButton = friendProfile_activity.getTradeButton();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(OfferTradeActivity.class.getName(),
                        null, false);

        friendProfile_activity.runOnUiThread(new Runnable() {
            public void run() {
                FriendProfileButton.performClick();
                FriendTradeButton.performClick();
            }
        });

        OfferTradeActivity  offerTrade_activity = (OfferTradeActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", offerTrade_activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                OfferTradeActivity.class,offerTrade_activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);


        return offerTrade_activity;
    }//end SetupLogin

    public void setupSecondUserInventory(){

        MainActivity activity = (MainActivity) getActivity();

        MainActivityLogin = activity.getLoginButton();
        LoginText = activity.getUsernameField();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: November 21
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(selectTaskActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                LoginText.setText("dantest2");
                MainActivityLogin.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: Nov 16 2015
        // Validate that ReceiverActivity is started
        selectTaskActivity Select_Activity = (selectTaskActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", Select_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                selectTaskActivity.class, Select_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        SelectTaskActivityInventory = Select_Activity.getInventoryButton();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(myInventoryActivity.class.getName(),
                        null, false);

        Select_Activity.runOnUiThread(new Runnable() {
            public void run() {
                SelectTaskActivityInventory.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        myInventoryActivity  myInventory_Activity = (myInventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", myInventory_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                myInventoryActivity.class, myInventory_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivityMonitor =
                getInstrumentation().addMonitor(AddItemActivity.class.getName(),
                        null, false);


        MyInventoryActivityAdd = myInventory_Activity.getAddItemButton();

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                MyInventoryActivityAdd.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        AddItemActivity AddItem_Acitivty = (AddItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", AddItem_Acitivty);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddItemActivity.class, AddItem_Acitivty.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        GameTitleText = AddItem_Acitivty.gameTitleText();
        ReleaseDateText = AddItem_Acitivty.releaseDataText();
        DescriptionText = AddItem_Acitivty.descriptionText();
        PlatformSpinner = AddItem_Acitivty.platformSpinner();
        QualitySpinner = AddItem_Acitivty.quailitySpinner();
        StatusSpinner = AddItem_Acitivty.eitherPublicOrPrivateSpinner();
        AddItemActicityAddItemButton = AddItem_Acitivty.addItemButton();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(myInventoryActivity.class.getName(),
                        null, false);

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                GameTitleText.setText("Darn Kids");
                ReleaseDateText.setText("11-11-2005");
                DescriptionText.setText("That Game where");
                PlatformSpinner.requestFocus();
                PlatformSpinner.setSelection(2);
                QualitySpinner.requestFocus();
                QualitySpinner.setSelection(1);
                StatusSpinner.requestFocus();
                StatusSpinner.setSelection(0);
                AddItemActicityAddItemButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        myInventory_Activity = (myInventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", myInventory_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                myInventoryActivity.class, myInventory_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        myInventory_Activity.finish();
        Select_Activity.finish();
    }

    public void setupSecondUser(){
        MainActivity activity = (MainActivity) getActivity();
        MainAcitivtyRegister = activity.getRegisterButton();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: November 21 2015
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(CreateProfileActivity.class.getName(),
                        null, false);


        activity.runOnUiThread(new Runnable() {
            public void run() {
                MainAcitivtyRegister.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: Nov 21 2015
        // Validate that ReceiverActivity is started
        CreateProfileActivity receiverActivity = (CreateProfileActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                CreateProfileActivity.class, receiverActivity.getClass());


        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        EditusernameText = receiverActivity.getusername();
        EditemailText = receiverActivity.getemail();
        EditcityText = receiverActivity.getcity();
        EditphonenumberText = receiverActivity.getphonenumber();

        assertNotNull(EditusernameText);


        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                EditusernameText.setText("dantest2");
                EditemailText.setText("ThatGoneBoy@Darnkids.com");
                EditcityText.setText("YoungWhippersnapperTown");
                EditphonenumberText.setText("123456789");
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        CreateProfileSaveButton = receiverActivity.getCreateProfilebutton();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                CreateProfileSaveButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

    }//end registerUserTest
}
