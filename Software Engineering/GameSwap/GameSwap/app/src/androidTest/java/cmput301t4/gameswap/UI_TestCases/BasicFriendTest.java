package cmput301t4.gameswap.UI_TestCases;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import cmput301t4.gameswap.Activities.CreateProfileActivity;
import cmput301t4.gameswap.Activities.FriendProfileActivity;
import cmput301t4.gameswap.Activities.MainActivity;
import cmput301t4.gameswap.Activities.SearchFriendActivity;
import cmput301t4.gameswap.Activities.selectTaskActivity;
import cmput301t4.gameswap.R;

/**
 * Created by kynan on 12/8/15.
 */
public class BasicFriendTest extends ActivityInstrumentationTestCase2 {

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


    public BasicFriendTest() {
        super(cmput301t4.gameswap.Activities.MainActivity.class);
    }//end Constructor


    public void testFriendBasic(){
        setupSecondUser();
        selectTaskActivity finishsetupLogin = setupLogin();
        SearchFriendActivity finishFriendNotExist = testSearchforFriendDoesNotExist(finishsetupLogin);
        SearchFriendActivity finishFriendAddedFriend = testSearchforFriendInUser(finishFriendNotExist);
        testDeleteFriend(finishFriendAddedFriend);
    }//end testFriendBasic

    public void testDeleteFriend(SearchFriendActivity activity){
        getInstrumentation().invokeMenuActionSync(activity, R.id.showPeople, 0);
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendProfileActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchFriendView.setQuery("EvanBoy2", true);
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
        assertTrue(FriendProfileButton.getText().equals("Remove Friend"));


        friendProfile_activity.runOnUiThread(new Runnable() {
            public void run() {
                FriendProfileButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();
        assertTrue(FriendProfileButton.getText().equals("Add Friend"));

        friendProfile_activity.finish();
    }

    public SearchFriendActivity testSearchforFriendInUser(SearchFriendActivity activity){
        getInstrumentation().invokeMenuActionSync(activity, R.id.showPeople, 0);
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendProfileActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchFriendView.setQuery("EvanBoy2", true);
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
        assertTrue(FriendProfileButton.getText().equals("Add Friend"));


        friendProfile_activity.runOnUiThread(new Runnable() {
            public void run() {
                FriendProfileButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();
        assertTrue(FriendProfileButton.getText().equals("Remove Friend"));

        friendProfile_activity.finish();
        return activity;
    }

    public SearchFriendActivity testSearchforFriendDoesNotExist(selectTaskActivity activity){
        SelectFriendButton = activity.getTraderButton();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SearchFriendActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
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

        searchFriendView = searchFriendActivity_Activity.getSearchView();

        searchFriendActivity_Activity.runOnUiThread(new Runnable() {
            public void run() {
                searchFriendView.setQuery("not a friend", true);
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();
        searchFriendListView = searchFriendActivity_Activity.getListView();


        return searchFriendActivity_Activity;
    }//end testSearchforFriendDoesNotExist

    /**
     *
     * @return
     * Runs the setup to Login into a dummy user made for testing
     */
    public selectTaskActivity setupLogin(){
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

        return Select_Activity;
    }//end SetupLogin

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
                EditusernameText.setText("EvanBoy2");
                EditemailText.setText("ThatEvanBoy@Darnkids.com");
                EditcityText.setText("YoungWhippersnapperTown");
                EditphonenumberText.setText("123456789");
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        assertTrue(EditusernameText.getText().toString().equals("EvanBoy2"));
        assertTrue(EditemailText.getText().toString().equals("ThatEvanBoy@Darnkids.com"));
        assertTrue(EditcityText.getText().toString().equals("YoungWhippersnapperTown"));
        assertTrue(EditphonenumberText.getText().toString().equals("123456789"));

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
