package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cmput301t4.gameswap.Managers.InventoryManager;
import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.Models.ImageModel;
import cmput301t4.gameswap.Models.Inventory;
import cmput301t4.gameswap.Models.Item;
import cmput301t4.gameswap.R;

/**
 * Adds item to user inventory based off of user input
 * Created by Preyanshu and Blake 2015-11-04
 */
public class AddItemActivity extends Activity implements OnItemSelectedListener {
    //create the unique list views and adapters for console, quality, and public and private
    //code referenced from http://developer.android.com/guide/topics/ui/controls/spinner.html

    /**
     * The spinner to choose the console
     */
    private Spinner consoleSpinner;

    /**
     * The spinner to choose the quality
     */
    private Spinner qualitySpinner;

    /**
     * The spinner to choose if the item is public or private
     */
    private Spinner publicprivateSpinner;

    /**
     * The Date for the item being added
     */
    final static String DATE_FORMAT = "dd-MM-yyyy";

    /**
     * The title for the item being added
     */
    private String title;

    /**
     * The release date for the item being added
     */
    private String releaseDate;

    /**
     * The description for the item being added
     */
    private String description;

    /**
     * The check for if the date added is valid
     */
    private Boolean isDateValid;

    /**
     * The text if the user wants to edit the title of the item
     */
    private EditText titleEditText;
    /**
     * This is storing the image taken by the camera
     */
    private  Bitmap imageBitmap;

    /**
     * The text if the user wants to edit the description of the item
     */
    private EditText descEditText;

    /**
     * The text if the user wants to edit the release date of the item
     */
    private EditText releaseEditText;

    private ImageView userImageButton;

    private AddItemActivity activity = this;
    private int saveImage = 0;


    private ArrayAdapter<Item> adapter;
    /**
     * the request to use the camera to take pictures
     */
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //loads it upon saving
        super.onCreate(savedInstanceState);
        //sets it to the activity
        setContentView(R.layout.activity_add_item);
        //setting spinners
        consoleSpinner = (Spinner) findViewById(R.id.consoleSpinner);
        qualitySpinner = (Spinner) findViewById(R.id.qualitySpinner);
        publicprivateSpinner = (Spinner) findViewById(R.id.privatepublicSpinner);
        userImageButton= (ImageButton) findViewById(R.id.imageButton);
        prepareSpinnerdata();

    }

    /**
     * Prepares the data for the different spinners that we have
     */
    private void prepareSpinnerdata() {
        //function creates spinner data for us for the three spinners here.
        // Create an ArrayAdapter for console array
        ArrayAdapter<CharSequence> console_adapter = ArrayAdapter.createFromResource(this,
                R.array.Console, android.R.layout.simple_spinner_item);
        // Specify the layout to be dropdown
        console_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
        // Apply the adapter to the console spinner
        consoleSpinner.setAdapter(console_adapter);

        // Create Array adapter for quality array
        ArrayAdapter<CharSequence> quality_adapter = ArrayAdapter.createFromResource(this,
                R.array.Quality, android.R.layout.simple_spinner_item);
        // Specify the layout to be a dropdown
        quality_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
        // Apply the adapter to the quality spinner
        qualitySpinner.setAdapter(quality_adapter);

        // Create Array adapter for the array we wish to use for private/public
        ArrayAdapter<CharSequence> public_private_adapter = ArrayAdapter.createFromResource(this,
                R.array.Public_or_Private, android.R.layout.simple_spinner_item);
        // use the layout for public and private
        public_private_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
        publicprivateSpinner.setAdapter(public_private_adapter);

    }
/*
    private void selectImage(){

        Intent choosePicIntent=new Intent();
        choosePicIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent takePicIntent = new Intent();
        takePicIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    }
/*
    /**
     * A necessary function that must be added to choose the item in the spinner
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        // On selecting a spinner item
        //String item = parent.getItemAtPosition(pos).toString();

        //code referenced from http://stackoverflow.com/questions/13431644/save-and-retrieve-selected-spinner-position
        int userChoiceConsole = consoleSpinner.getSelectedItemPosition();
        int userChoiceQuality = qualitySpinner.getSelectedItemPosition();
        int userChoicePrivate = publicprivateSpinner.getSelectedItemPosition();
        SharedPreferences sharedPref = getSharedPreferences("FileName", 0);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("userChoiceSpinner", userChoiceConsole);
        prefEditor.putInt("userChoiceSpinner", userChoiceQuality);
        prefEditor.putInt("userChoiceSpinner", userChoicePrivate);


        prefEditor.commit();
    }
    /**
     * A necessary function to implement spinners if nothing is chosen
     */
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * opens camera to take photo
     * @param view
     */
    public void addImageOption(View view){

        ImageButton takeItemPic = (ImageButton) findViewById(R.id.imageButton);
        takeItemPic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    /**
     * after taking a photo, displays the resulting image taken
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            ImageView gameImageView = (ImageView) findViewById(R.id.gameImageView);
            gameImageView.setImageBitmap(imageBitmap);
            saveImage = 1;

            //ImageModel image = new ImageModel(UserManager.getTrader().getCounter(), UserManager.getTrader().getUserName(), imageBitmap);
            //ServerManager.saveImage(image);

        }
    }
    /**
     * Saves the data from the inputs we enter
     */
    public void saveButtonClick(View view) {
        Boolean isPrivate;
        titleEditText = (EditText) findViewById(R.id.gameTitle);
        releaseEditText = (EditText) findViewById(R.id.releaseDateEdit);
        descEditText = (EditText) findViewById(R.id.descriptionBox);

        int console = consoleSpinner.getSelectedItemPosition();
        int qual = qualitySpinner.getSelectedItemPosition();
        if (publicprivateSpinner.getSelectedItemPosition() == 1){
            isPrivate = Boolean.TRUE;
        } else {
            isPrivate = Boolean.FALSE;
        }

        title = titleEditText.getText().toString();
        releaseDate = releaseEditText.getText().toString();
        description = descEditText.getText().toString();

        isDateValid = checkDate(releaseDate);
        if (isDateValid == false)
            Toast.makeText(getBaseContext(), "Wrong date format!", Toast.LENGTH_SHORT).show();

        else if (TextUtils.isEmpty(title) || TextUtils.isEmpty(releaseDate) || TextUtils.isEmpty(description)) {
            Toast.makeText(getBaseContext(), "At least one of the fields is empty!", Toast.LENGTH_SHORT).show();
        } else {

            UserManager.getTrader().setInventory(InventoryManager.getInstance());
            //code taken from http://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array

            InventoryManager.addItem(title, releaseDate, isPrivate, qual, console, description);

            if(saveImage == 1){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                ImageModel image = new ImageModel(UserManager.getTrader().getCounter() - 1, UserManager.getTrader().getUserName(), byteArray);
                ServerManager.saveImage(image);
                InventoryManager.getItem(InventoryManager.getInstance().size() - 1).setHasPicture();

                //ServerManager.blakeSaveItemImage(UserManager.getTrader().getCounter(), imageBitmap);
            }
            //String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);




            ServerManager.saveUserOnline(UserManager.getTrader());
            Intent intent = new Intent(AddItemActivity.this, myInventoryActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

    public void cancelButtonClick(View view) {
        this.finish();
    }


    public  boolean checkDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }




    //=====Function needed for Testcases=====//

    public Button addItemButton(){
        Button button = (Button) findViewById(R.id.saveButton);
        return button;
    }//end addItemButton

    public EditText gameTitleText(){
        EditText text = (EditText) findViewById(R.id.gameTitle);
        return text;
    }//end gameTitleText

    public EditText releaseDataText(){
        EditText text = (EditText) findViewById(R.id.releaseDateEdit);
        return text;
    }//end releaseDataText

    public EditText descriptionText(){
        EditText text = (EditText) findViewById(R.id.descriptionBox);
        return text;
    }//end descriptionText

    public Spinner platformSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.consoleSpinner);
        return spinner;
    }//end platformSpinner

    public Spinner quailitySpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.qualitySpinner);
        return spinner;
    }//end qialitySpinner

    public Spinner eitherPublicOrPrivateSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.privatepublicSpinner);
        return spinner;
    }//end eitherPublicOrPrivateSpinner

    //=====End Function needed for Testcases=====//

}

