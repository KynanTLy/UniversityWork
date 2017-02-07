package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.Models.User;
import cmput301t4.gameswap.R;

/**
 * Created by Blake and Preyanshu on 2015-11-05.
 */
public class CreateProfileActivity extends Activity {
    private User myUser;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText cityEditText;
    private EditText phoneNumberEditText;
    //static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        usernameEditText = (EditText) findViewById(R.id.username);
        emailEditText = (EditText) findViewById(R.id.email_user);
        cityEditText = (EditText) findViewById(R.id.user_city);
        phoneNumberEditText = (EditText) findViewById(R.id.user_phone_num);
    }


    public void createProfileButton(View view) {

        ServerManager.searchForUser(usernameEditText.getText().toString());
        if(ServerManager.checkResult() == Boolean.TRUE){
            Toast.makeText(getBaseContext(), "This username is already in use", Toast.LENGTH_SHORT).show();
        }else {
            UserManager.createUser(usernameEditText.getText().toString(), emailEditText.getText().toString(), cityEditText.getText().toString(), phoneNumberEditText.getText().toString(), this);
            UserManager.setDefaultLocation(this);
            System.out.println(UserManager.getTrader().getDefaultLocation().toString());
            ServerManager.saveUserOnline(UserManager.getTrader());

            this.finish();
        }
    }

    public void cancelButton(View view) {
        this.finish();
    }



   /* public void addImageOption(View view) {
        final ImageButton takeProfPic = (ImageButton) findViewById(R.id.profilePic);
        takeProfPic.setOnClickListener(new View.OnClickListener() {
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
            ImageView profileImageView = (ImageView) findViewById(R.id.userProfileView);
            profileImageView.setImageBitmap(imageBitmap);
        }
    }*/

    public User getUser() {

        Log.w("myUser", myUser.getUserName());
        return myUser;
    }

    //======Code used for testcases=====//

    public EditText getusername(){return usernameEditText;}

    public EditText getemail(){return emailEditText;}

    public EditText getcity(){return cityEditText;}

    public EditText getphonenumber(){return phoneNumberEditText;}

    public Button getCreateProfilebutton(){
        Button button = (Button) findViewById(R.id.saveButton);
        return button;
    }

    //======End code used for testcases=====//
}
