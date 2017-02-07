package cmput301t4.gameswap.UI_TestCases;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import cmput301t4.gameswap.Activities.AddItemActivity;
import cmput301t4.gameswap.Activities.EditItemActivity;
import cmput301t4.gameswap.Activities.MainActivity;
import cmput301t4.gameswap.Activities.ViewItemActivity;
import cmput301t4.gameswap.Activities.myInventoryActivity;
import cmput301t4.gameswap.Activities.selectTaskActivity;
import cmput301t4.gameswap.R;

/**
 * Created by kynan on 11/28/15.
 */
public class BasicInventoryTest  extends ActivityInstrumentationTestCase2 {

    /**
     * Variables used in MainActivity
     */
    private Button MainActivityLogin;
    private EditText LoginText;

    /**
     * Variables used in SelectTaskActivity
     */
    private Button SelectTaskActivityInventory;

    /**
     * Variables used in MyInventoryAcitivity
     */
    private Button MyInventoryActivityAdd;

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
     * Variables used in EditItemTest
     */
    private ListView myInventoryList;
    private EditText EditUserText;
    private EditText EditReleaseText;
    private EditText EditDescriptionText;
    private Spinner  EditPlatformSpin;
    private Spinner  EditQualitySpin;
    private Spinner  EditSharedSpin;
    private Button   EditSaveButton;

    /**
     * Popup menu + Alertdialog Variable
     */
    private Menu myInventoryPopup;
    private AlertDialog myInventoryDialog;



    public BasicInventoryTest() {
        super(cmput301t4.gameswap.Activities.MainActivity.class);
    }//end Constructor

    /**
     * Runs all the basic item test (Add, Edit, Delete) in one testcases
     * Calls 1 helper function (setup Login)
     * Uses 3 UI test function  (addItemTest, editItemTest, deleteItemTest)
     *
     */
    public void testInventoryBasic(){
        myInventoryActivity finishAddItem = addItemTest();
        assertNotNull(finishAddItem);
        myInventoryActivity finishEditItem = editItemTest(finishAddItem);
        assertNotNull(finishEditItem);
        myInventoryActivity finishViewItemDetail = viewItemDetailTest(finishEditItem);
        deleteItemTest(finishViewItemDetail);

    }//end testInventoryBasic

    /**
     *
     * @param myInventory_Activity
     *
     * Runs the UI test to delete Item from my Inventory
     */
    public void deleteItemTest(myInventoryActivity myInventory_Activity){
        myInventoryList = myInventory_Activity.getInventoryList();

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                myInventoryList.getChildAt(0).performLongClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        myInventoryPopup = myInventory_Activity.getPopupMenu();

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                myInventoryPopup.performIdentifierAction(R.id.deleteItemMenuId, 0);
            }
        });
        getInstrumentation().waitForIdleSync();

        myInventoryDialog = myInventory_Activity.getAlertDialog();

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                myInventoryDialog.getButton(Dialog.BUTTON_POSITIVE).performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertNull(myInventoryList.getChildAt(0));
    }//end deleteItemTest

    public myInventoryActivity editItemTest(myInventoryActivity myInventory_Activity){
        myInventoryList = myInventory_Activity.getInventoryList();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditItemActivity.class.getName(),
                        null, false);

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                myInventoryList.getChildAt(0).performLongClick();

            }
        });
        getInstrumentation().waitForIdleSync();

        myInventoryPopup = myInventory_Activity.getPopupMenu();

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                myInventoryPopup.performIdentifierAction(R.id.editItemMenuId, 0);

            }
        });
        getInstrumentation().waitForIdleSync();

        EditItemActivity EditItem_Activity = (EditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", EditItem_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditItemActivity.class, EditItem_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        EditUserText = EditItem_Activity.editUsernameText();
        EditReleaseText = EditItem_Activity.editReleaseText();
        EditDescriptionText = EditItem_Activity.editDescriptionText();
        EditPlatformSpin = EditItem_Activity.editPlatformSpinner();
        EditQualitySpin = EditItem_Activity.editQualitySpinner();
        EditSharedSpin = EditItem_Activity.editSharedSpinner();
        EditSaveButton = EditItem_Activity.editSaveButton();


        EditItem_Activity.runOnUiThread(new Runnable() {
            public void run() {
                EditUserText.setText("Darn Kids PART 2");
                EditReleaseText.setText("12-12-5555");
                EditDescriptionText.setText("That Game where things");
                EditPlatformSpin.requestFocus();
                EditPlatformSpin.setSelection(2);
                EditQualitySpin.requestFocus();
                EditQualitySpin.setSelection(1);
                EditSharedSpin.requestFocus();
                EditSharedSpin.setSelection(0);
                EditSaveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();



        getInstrumentation().callActivityOnResume(myInventory_Activity);

        return myInventory_Activity;
    }//end editItemTest

    /**
     *
     * @return
     *
     * Runs the UI test for adding an item
     */
    public myInventoryActivity addItemTest(){
        selectTaskActivity startactivity = setupLogin();
        assertEquals("At my Inventory Activity",selectTaskActivity.class, startactivity.getClass());

        SelectTaskActivityInventory = startactivity.getInventoryButton();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(myInventoryActivity.class.getName(),
                        null, false);

        startactivity.runOnUiThread(new Runnable() {
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

        myInventoryList = myInventory_Activity.getInventoryList();
        assertNotNull(myInventoryList.getChildAt(0));
        return myInventory_Activity;
    }//end addItemTest

    /**
     * @return
     * @param myInventory_Activity
     * Runs UI test for View Item Detail
     */
    public myInventoryActivity viewItemDetailTest(myInventoryActivity myInventory_Activity){
        myInventoryList = myInventory_Activity.getInventoryList();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ViewItemActivity.class.getName(),
                        null, false);

        myInventory_Activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = myInventoryList.getChildAt(0);
                myInventoryList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        ViewItemActivity ViewItem_Activity = (ViewItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", ViewItem_Activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ViewItemActivity.class, ViewItem_Activity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivityMonitor =
                getInstrumentation().addMonitor(MainActivity.class.getName(),
                        null, false);


        assertTrue(ViewItem_Activity.getNameText().getText().toString().equals("Darn Kids PART 2"));
        //assertTrue(ViewItem_Activity.getDateText().getText().toString().equals("12-12-5555"));
        assertTrue(ViewItem_Activity.getDescritionText().getText().toString().equals("That Game where things"));
        assertTrue(ViewItem_Activity.getPlatformText().getText().toString().equals("PC"));
        assertTrue(ViewItem_Activity.getQualityText().getText().toString().equals("4-Opened No Scratches/Damage"));
        assertTrue(ViewItem_Activity.getStatusText().toString().equals("Public"));


        ViewItem_Activity.finish();
        getInstrumentation().waitForIdleSync();
        getInstrumentation().callActivityOnResume(myInventory_Activity);
        return myInventory_Activity;

    }//end viewItemDetailTest

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

}//end BasicInventoryTest
