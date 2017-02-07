package cmput301t4.gameswap.UI_TestCases;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import cmput301t4.gameswap.Activities.AddItemActivity;
import cmput301t4.gameswap.Activities.CreateProfileActivity;
import cmput301t4.gameswap.Activities.FriendInventoryActivity;
import cmput301t4.gameswap.Activities.FriendProfileActivity;
import cmput301t4.gameswap.Activities.MainActivity;
import cmput301t4.gameswap.Activities.SearchFriendActivity;
import cmput301t4.gameswap.Activities.ViewItemActivity;
import cmput301t4.gameswap.Activities.myInventoryActivity;
import cmput301t4.gameswap.Activities.selectTaskActivity;
import cmput301t4.gameswap.R;

/**
 * Created by kynan on 12/8/15.
 */
public class BasicViewOtherUserProfile extends ActivityInstrumentationTestCase2 {


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
    private TextView FriendNameTextView;
    private TextView FriendCityTextView;
    private TextView FriendPhoneTextView;
    private TextView FriendEmailTextView;
    private Button FriendInventoryButton;

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
     * Variables used in ViewItemActivity
     */
    private TextView itemNameText;
    private TextView itemDescriptionText;
    private TextView itemDateText;
    private String itemStatusText;

    public BasicViewOtherUserProfile() {
        super(cmput301t4.gameswap.Activities.MainActivity.class);
    }//end Constructor


    public void testBasicViewOtherUserProfile(){
        setupSecondUser();
        setupSecondUserInventory();
        SearchFriendActivity finishedSetup = setupLogin();
        testViewOtherProfile(finishedSetup);

    }//end testFriendBasic

    public void testViewOtherProfile(SearchFriendActivity activity){
        getInstrumentation().invokeMenuActionSync(activity, R.id.showPeople, 0);
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendProfileActivity.class.getName(),
                        null, false);

        searchFriendView = activity.getSearchView();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchFriendView.setQuery("GoneBoy2", true);
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
        FriendNameTextView = friendProfile_activity.getNameView();
        FriendCityTextView = friendProfile_activity.getCityView();
        FriendPhoneTextView = friendProfile_activity.getPhoneView();
        FriendEmailTextView = friendProfile_activity.getEmailView();
        assertTrue(FriendProfileButton.getText().equals("Add Friend"));
        assertTrue(FriendNameTextView.getText().equals("GoneBoy2"));
        assertTrue(FriendCityTextView.getText().equals("City Unavailable"));
        assertTrue(FriendPhoneTextView.getText().equals("Phonenumber Unavailable"));
        assertTrue(FriendEmailTextView.getText().equals("Email Unavailable"));


        friendProfile_activity.runOnUiThread(new Runnable() {
            public void run() {
                FriendProfileButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();
        assertTrue(FriendProfileButton.getText().equals("Remove Friend"));
        assertTrue(FriendCityTextView.getText().equals("YoungWhippersnapperTown"));
        assertTrue(FriendPhoneTextView.getText().equals("123456789"));
        assertTrue(FriendEmailTextView.getText().equals("ThatGoneBoy@Darnkids.com"));

        friendProfile_activity.finish();
    }

    public void testViewOtherProfileInventory(SearchFriendActivity activity){
        getInstrumentation().invokeMenuActionSync(activity, R.id.showPeople, 0);
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendProfileActivity.class.getName(),
                        null, false);

        searchFriendView = activity.getSearchView();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchFriendView.setQuery("GoneBoy2", true);
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

        FriendInventoryButton = friendProfile_activity.getInventoryButton();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendInventoryActivity.class.getName(),
                        null, false);

        friendProfile_activity.runOnUiThread(new Runnable() {
            public void run() {
                FriendInventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        FriendInventoryActivity  friendInventory_activity = (FriendInventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendInventory_activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                FriendInventoryActivity.class, friendInventory_activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivityMonitor =
                getInstrumentation().addMonitor(ViewItemActivity.class.getName(),
                        null, false);

        FriendInventory = friendInventory_activity.getListView();

        friendInventory_activity.runOnUiThread(new Runnable() {
            public void run() {
                FriendInventory.getChildAt(0).performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        ViewItemActivity  viewItem_activity = (ViewItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", viewItem_activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ViewItemActivity.class, viewItem_activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        itemNameText = viewItem_activity.getNameText();
        itemDescriptionText = viewItem_activity.getDescritionText();
        itemDateText = viewItem_activity.getDateText();
        itemStatusText = viewItem_activity.getStatusText();

        assertTrue(itemNameText.getText().equals("Darb Kids"));
        assertTrue(itemDescriptionText.getText().equals("That Game where"));
        assertTrue(itemDateText.getText().equals("11-11-2005"));
        assertTrue(itemStatusText.equals("PUBLIC"));

    }//end testViewOtherProfileInventory

    /**
     *
     * @return
     * Runs the setup to Login into a dummy user made for testing
     */
    public SearchFriendActivity setupLogin(){
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

        return searchFriendActivity_Activity;
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
                LoginText.setText("GoneBoy2");
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
        LoginText.setText("");
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
                EditusernameText.setText("GoneBoy2");
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
