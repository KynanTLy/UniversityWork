package cmput301t4.gameswap.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cmput301t4.gameswap.Managers.ServerManager;
import cmput301t4.gameswap.Managers.UserManager;
import cmput301t4.gameswap.R;

public class EditProfileActivity extends Activity {

    private TextView nameEditText;
    private EditText emailEditText;
    private EditText cityEditText;
    private EditText phoneEditText;

    //static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEditText = (TextView) findViewById(R.id.changeName);
        emailEditText = (EditText) findViewById(R.id.changeEmail);
        cityEditText = (EditText) findViewById(R.id.changeCity);
        phoneEditText = (EditText) findViewById(R.id.changePhone);

        nameEditText.setText(UserManager.getTrader().getUserName());
        emailEditText.setText(UserManager.getTrader().getUserEmail());
        cityEditText.setText(UserManager.getTrader().getUserCity());
        phoneEditText.setText(UserManager.getTrader().getUserPhoneNumber());
    }

    /*public void addImageOption(View view) {
        final ImageButton takeProfPic = (ImageButton) findViewById(R.id.newProfilePicButton);
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
            ImageView gameImageView = (ImageView) findViewById(R.id.newProfilePicView);
            gameImageView.setImageBitmap(imageBitmap);
        }
    }*/


    public void editProfileButton (View view) {

        UserManager.editCity(cityEditText.getText().toString());
        UserManager.editPhoneNumber(phoneEditText.getText().toString());
        UserManager.editEmail(emailEditText.getText().toString());

        ServerManager.saveUserOnline(UserManager.getTrader());
        Intent intent = new Intent(EditProfileActivity.this,MyProfileActivity.class);
        this.finish();
        startActivity(intent);
    }

    public void clickCancelButton(View view) {
        this.finish();
    }

    //=====Function used for testcases=====//

    public TextView getNameText(){
        return nameEditText;
    }//end getNameText

    public EditText getCityText(){
        return cityEditText;
    }//end getCityText

    public EditText getEmailEditText(){
        return emailEditText;
    }//end getEmailEditText

    public EditText getPhoneEditText(){
        return phoneEditText;
    }//end getPhoneEditText

    public Button getSaveButton(){
        Button button = (Button) findViewById(R.id.saveButton);
        return button;
    }//end getSaveButton

    //=====End function needed for testcases=====//


}
