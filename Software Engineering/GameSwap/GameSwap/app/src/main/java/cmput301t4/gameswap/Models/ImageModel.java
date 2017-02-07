package cmput301t4.gameswap.Models;

/**
 * Created by preyansh on 11/29/15.
 */
public class ImageModel {
    private int itemId;
    private String userName;
    private byte[] itemPicture;

    public ImageModel(int itemId, String userName, byte[] picReceived) {
        this.itemId = itemId;
        this.userName = userName;
        this.itemPicture = picReceived;
    }

    public int getImageItemId(){return itemId;}

    public String getImageuserName(){return userName;}

    public byte[] getImage(){return itemPicture;}

    public void setImageItemId(int num){this.itemId = num;}

    public void setImageUser(String user){this.userName = user;}
}
