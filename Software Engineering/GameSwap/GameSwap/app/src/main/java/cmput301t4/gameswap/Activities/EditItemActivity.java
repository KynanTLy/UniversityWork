package cmput301t4.gameswap.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cmput301t4.gameswap.Managers.InventoryManager;
import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.Models.Item;
import cmput301t4.gameswap.R;

/**
 * Edits user info on a certain Item
 * created by Blake and Preyanshu 2015-11-05
 */
public class EditItemActivity extends Activity {

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


    final static String DATE_FORMAT = "dd-MM-yyyy";

    private String title;
    private String releaseDate;
    private String description;
    private Boolean isDateValid;
    private EditText titleEditText;
    private EditText releaseEditText;
    private EditText descEditText;
    private Integer index;
    private ArrayList<Item> inventory;



    private Button saveEditItemButton;

    private static final String FILENAME = "file.sav"; // model

    static final int REQUEST_IMAGE_CAPTURE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //loads it upon saving
        super.onCreate(savedInstanceState);
        //sets it to the activity
        setContentView(R.layout.activity_edit_item);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        //setting spinners
        consoleSpinner = (Spinner) findViewById(R.id.editconsoleSpinner);
        qualitySpinner = (Spinner) findViewById(R.id.editqualitySpinner);
        publicprivateSpinner = (Spinner) findViewById(R.id.editprivatepublicSpinner);

        titleEditText = (EditText) findViewById(R.id.editGameTitle);
        releaseEditText = (EditText) findViewById(R.id.editReleaseDate);
        descEditText = (EditText) findViewById(R.id.editdescriptionBox);

        System.out.println("at edit item activity");


        if (b != null) {
            titleEditText.setText(b.getString("name"));
            descEditText.setText(b.getString("description"));
            releaseEditText.setText(b.getString("releaseDate"));
            index = b.getInt("index");
        }

        prepareSpinnerdata();

    }

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

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void saveButtonClick(View view) {
        //Toast.makeText(getBaseContext(), "Saving", Toast.LENGTH_SHORT).show();
        Boolean isPrivate;
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
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            Item item = new Item(title, releaseDate, isPrivate, qual, console, description);

            //int index = UserManager.getInventory().findItemByIndx(b.getInt("itemID"));
            UserManager.getInventory().replace(item, b.getInt("index"));
            //inventory = InventoryManager.getItems();
            ServerManager.saveUserOnline(UserManager.getTrader());
            this.finish();

        }

    }

    public void cancelButtonClick(View view) {
        this.finish();
    }

    public void addImageOption(View view) {
        final ImageButton takePhoto = (ImageButton) findViewById(R.id.newItemPicture);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView gameImageView = (ImageView) findViewById(R.id.newItemPictureView);
            gameImageView.setImageBitmap(imageBitmap);
        }
    }


    public static boolean checkDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    private void saveToFile() {
        //TODO: Remove this insanity. Use the CacheManager or the ServerManager.
        try {
            ArrayList<Item> items = InventoryManager.getInstance().getItems();
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(items, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public void setToCurrentLocation(View view) {
        //TODO: Hook up the UI to this
        // Code acquired from http://www.vogella.com/tutorials/AndroidLocationAPI/article.html#locationapi on Nov 30, 2015
        LocationManager service = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();

            return;
        }

        service.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //TODO: Figure out which item we are editing then call next line
                //InventoryManager.setItemLocation(??, location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, null);
    }

    //=====Function needed for Test=====//

    public EditText editUsernameText(){
        return titleEditText;
    }//end editUsernameText

    public EditText editReleaseText(){
        return releaseEditText;
    }//end editReleaseText

    public EditText editDescriptionText(){
        return descEditText;
    }//end editDescriptionText

    public Spinner editPlatformSpinner(){
        return consoleSpinner;
    }//end editPlatform

    public Spinner editQualitySpinner(){
        return qualitySpinner;
    }//end editQuality

    public Spinner editSharedSpinner(){
        return publicprivateSpinner;
    }//end editSharedSpinner

    public Button editSaveButton(){
        Button button = (Button) findViewById(R.id.editItemSaveButton);
        return button;
    }//end editSaveButton

    //=====End Function needed for Test=====//

}