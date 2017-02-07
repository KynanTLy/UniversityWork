package cmput301t4.gameswap.UI_TestCases;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cmput301t4.gameswap.Activities.CreateProfileActivity;
import cmput301t4.gameswap.Activities.EditProfileActivity;
import cmput301t4.gameswap.Activities.MainActivity;
import cmput301t4.gameswap.Activities.MyProfileActivity;
import cmput301t4.gameswap.Activities.selectTaskActivity;
import cmput301t4.gameswap.R;

/**
 * Created by kynan on 11/21/15.
 * Test 3 things:   1. Can we register a user
 *                  2. Can we login with a username that not in the system
 *                  3. Can we login with a username that does exist
 */
public class BasicUserTest extends ActivityInstrumentationTestCase2{

    /**
     * Variables for testRegisterUser
     */
    private Button MainAcitivtyRegister;
    private Button CreateProfileSaveButton;
    private EditText EditusernameText;
    private EditText EditemailText;
    private EditText EditcityText;
    private EditText EditphonenumberText;

    /**
     * Variables for testRegisterUser + testLoginSuccessful
     */
    private Button MainActivityLogin;
    private EditText LoginText;

    /**
     * Variables for testEditProfile (from my_profile activity)
     */
    private Button MyProfileEditButton;
    private TextView ProfileName;
    private TextView ProfileCity;
    private TextView ProfilePhone;
    private TextView ProfileEmail;
    /**
     * Variables for testEditProfile (from edit_profile activity)
     */
    private Button   EditProfileSaveButton;
    private EditText EditCityText;
    private EditText EditPhoneText;


    public BasicUserTest() {
        super(cmput301t4.gameswap.Activities.MainActivity.class);
    }//end BasicUserTest

    /**
     * Runs the test to: Create a User, Login in with a user that does not exist, Login with a existing user
     */
    public void testBasicUser(){
        MainActivity finishRegisterUser = registerUserTest();
        MainActivity finishNotAUser =loginUserDoesNotExistTest(finishRegisterUser);
        selectTaskActivity finishSuccessLogin = loginSuccessfulTest(finishNotAUser);
        editUserProfileTest(finishSuccessLogin);
    }//end testBasicUser

    /**
     *
     * @return
     * Runs the UI test to register a user
     */
    public MainActivity registerUserTest(){
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
                EditusernameText.setText("JimBoy2");
                EditemailText.setText("ThatJimBoy@Darnkids.com");
                EditcityText.setText("YoungWhippersnapperTown");
                EditphonenumberText.setText("123456789");
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        assertTrue(EditusernameText.getText().toString().equals("JimBoy2"));
        assertTrue(EditemailText.getText().toString().equals("ThatJimBoy@Darnkids.com"));
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
        return activity;
    }//end registerUserTest

    /**
     *
     * @param activity
     * @return
     * Runs the UI test for login with a user that does not exist
     */
    public MainActivity loginUserDoesNotExistTest(MainActivity activity) {
        MainActivityLogin = activity.getLoginButton();
        LoginText = activity.getUsernameField();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                LoginText.setText("DoesNotExist");
                MainActivityLogin.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        assertEquals("Activity is still on Login Page",
                MainActivity.class, activity.getClass());

        getInstrumentation().waitForIdleSync();
        return activity;
    }//end loginUserDoesNotExistTest

    /**
     *
     * @param activity
     * Runs the UI test for a user that does exist
     */
    public selectTaskActivity loginSuccessfulTest(MainActivity activity){
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
        //Date: Oct 16
        // Validate that ReceiverActivity is started
        selectTaskActivity receiverActivity = (selectTaskActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                selectTaskActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        return receiverActivity;
    }//end testLoginSuccessful

    public void editUserProfileTest(final selectTaskActivity activity){
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(MyProfileActivity.class.getName(),
                        null, false);


        getInstrumentation().invokeMenuActionSync(activity, R.id.my_profile, 0);
        getInstrumentation().waitForIdleSync();

        MyProfileActivity myProfile_Activity = (MyProfileActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", myProfile_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                MyProfileActivity.class, myProfile_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivityMonitor =
                getInstrumentation().addMonitor(EditProfileActivity.class.getName(),
                        null, false);

        MyProfileEditButton = myProfile_Activity.getEditButton();

        myProfile_Activity.runOnUiThread(new Runnable() {
            public void run() {
                MyProfileEditButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        EditProfileActivity editProfile_Activity = (EditProfileActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editProfile_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditProfileActivity.class, editProfile_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivityMonitor =
                getInstrumentation().addMonitor(MyProfileActivity.class.getName(),
                        null, false);

        EditProfileSaveButton = editProfile_Activity.getSaveButton();
        EditCityText = editProfile_Activity.getCityText();
        EditPhoneText = editProfile_Activity.getPhoneEditText();


        editProfile_Activity.runOnUiThread(new Runnable() {
            public void run() {
                EditCityText.setText("Edmonton");
                EditPhoneText.setText("7802544987");
                EditProfileSaveButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        myProfile_Activity = (MyProfileActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", myProfile_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                MyProfileActivity.class, myProfile_Activity.getClass());

        ProfileName = myProfile_Activity.getNameText();
        ProfileCity = myProfile_Activity.getCityText();
        ProfilePhone = myProfile_Activity.getContactText();
        ProfileEmail = myProfile_Activity.getEmailText();

        assertTrue(ProfileName.getText().toString().equals("JimBoy2"));
        assertTrue(ProfileCity.getText().toString().equals("Edmonton"));
        assertTrue(ProfilePhone.getText().toString().equals("7802544987"));
        assertTrue(ProfileEmail.getText().toString().equals("ThatJimBoy@Darnkids.com"));
        getInstrumentation().callActivityOnResume(myProfile_Activity);

    }//end editUserProfile

}//end BasicUserTest