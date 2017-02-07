package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.R;

public class ViewItemActivity extends Activity {

    /*Declaring all the listViews here*/
    private TextView name;
    private TextView quality;
    private TextView description;
    private TextView platform;
    private TextView date;
    private Boolean status;
    private String statusDisplay;
    private TextView statusView;
    private ImageView imageView;
    private TextView location;
    private ArrayList<String> platformList;
    private String platformString;
    private Integer platformIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        name  = (TextView) findViewById(R.id.viewItemName);
        quality = (TextView) findViewById(R.id.viewItemQuality);
        description = (TextView) findViewById(R.id.viewItemDesciption);
        platform = (TextView) findViewById(R.id.viewItemPlatform);
        date = (TextView) findViewById(R.id.viewItemDate);
        name = (TextView) findViewById(R.id.viewItemName);
        statusView = (TextView) findViewById(R.id.viewStatus);
        location =(TextView) findViewById(R.id.locationDescription);
        imageView = (ImageView) findViewById(R.id.gameImageView);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            description.setText(b.getString("description"));

            status = b.getBoolean("private");
            if (status == Boolean.TRUE){
                statusDisplay = "Private";
            }
            else {
                statusDisplay = "Public";
            }

            name.setText(b.getString("name"));
            date.setText(b.getString("releaseDate"));


            quality.setText(getQuality());
            platform.setText(getPlatform());

            statusView.setText(statusDisplay.toUpperCase());
            location.setText("Latitude: " + b.getDouble("Latitude") + ", Longitude: " + b.getDouble("Longitude"));


            //statusView.setText(status);
            //ServerManager.blakeLoadItemdImage(b.getInt("itemId"));
            ServerManager.loadImage(b.getInt("itemId"));

            statusView.setText(statusDisplay.toUpperCase());
            location.setText("Latitude: " + b.getDouble("Latitude") + ", Longitude: " + b.getDouble("Longitude"));

            if(UserManager.imageRdy == 1) {
                byte[] byteArray = UserManager.getImageModel().getImage();
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                //imageView.setImageBitmap(UserManager.getImage());
                imageView.setImageBitmap(image);
            }


        }
    }

    //=====Function needed for Testcases=====//

    public TextView getNameText() {
        return name;
    }

    public TextView getQualityText() {
        return quality;
    }

    public TextView getDescritionText() {
        return description;
    }

    public TextView getPlatformText() {
        return platform;
    }

    public TextView getDateText() {
        return date;
    }

    public String getStatusText() {
        return statusDisplay;
    }

    public TextView getName() {
        return name;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getStatusView() {
        return statusView;
    }
//=====End function needed for Testcases=====//

    public String getPlatform(){
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        int index = b.getInt("platform");
        ArrayList<String> convertPlatform = new ArrayList<String>();
        convertPlatform.add("PlayStation 4");
        convertPlatform.add("Xbox One");
        convertPlatform.add("PC");
        convertPlatform.add("Wii U");
        convertPlatform.add("Nintendo 3DS");
        convertPlatform.add("PlayStation 3");
        convertPlatform.add("PlayStation Vita");
        convertPlatform.add("Xbox 360");
        convertPlatform.add("Nintendo Wii");
        convertPlatform.add("Nintendo DS");
        convertPlatform.add("PlayStation 2");
        convertPlatform.add("Xbox");
        convertPlatform.add("Nintendo GameCube");
        convertPlatform.add("GameBoy Advance");
        convertPlatform.add("PlayStation Portable");
        convertPlatform.add("Nintendo 64");
        convertPlatform.add("GameBoy Colour");
        convertPlatform.add("SNES");
        convertPlatform.add("NES");

        for (int j= 0; j < 19; j++){
            if (j == index){
                return convertPlatform.get(j);
            }
        }
        return "";
    }

    public String getQuality(){
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        int index = b.getInt("quality");
        ArrayList<String> convertQuality = new ArrayList<String>();
        convertQuality.add("5-Perfect Condition/Unopened/Download Code");
        convertQuality.add("4-Opened No Scratches/Damage");
        convertQuality.add("3-Light Scratches/Damage");
        convertQuality.add("2-Decent Scratches/Damage");
        convertQuality.add("1-Heavy Scratches/Damage");
        for (int j= 0; j < 19; j++){
            if (j == index){
                return convertQuality.get(j);
            }
        }
        return "";
    }

}
