public class PhotographTest extends ActivityInstrumentationTestCase2 {

	//Note 1: Current Account parameter are longer than actually stated in testcase
	//For now (+ simplicity + readability) we only use 3 parameter
	
	//Note 2: It is assumed you have a connection online, and therefore the testcases
	//are for online connection

	//Note 3: usercase US06.02.01. partially address in load, as enlarging image is 
	//more on UI end. relates to loadphoto.

	//Note 4: "Photo File" is suppose to be an actual photofile like .png , etc

	//Note 5: usercase US06.05.01. When the image is enlarged you can then choose from 
	//there choose to downlaod photo. relates to loadphoto

	public CreateAccountTest(){
        	super(tradegames.class);    //"tradegamesapp" is name of the app for now
        }
	
	private boolean connection = Boolean.TRUE; //Assumption connection = true

	//US06.01.01
	//As an owner, I want to optionally attach photographs of items to the item. Photos are optional for items.
	//US06.03.01
	//As an owner, I want to delete any attached photograph receipt on an item, so that unclear images can be re-taken.
	//Test adding a photo to an item
        public void testAddItemPhoto(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		//Code goes inside here to retake photo, and to accept/decline photo
		item1.addPhoto("Photo File"); //Photo File will be a real PhotoFile
		MyInventory.addItem(item1);
		//Connect inventory to an account
		MyAccount.setInventory(MyInventory);
		ViewAccountInventory ViewInventory = SM.ViewAccountInventory("rupehra");
		//Check to see if we can view photo
		//Currently photo is a "string" not a file
		assertTrue(ViewInventory.getPhoto().equals("Photo File"); 
	}

	//US06.04.01
	//As a sysadmin, I want each receipt image file to be under 65536 bytes in size.
	//Test photo size exception 
        public void testSizePhoto(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		try{		
			item1.addPhoto("Photo File"); //Photo File will be a real PhotoFile
		} catch (SizeException e){
			displayMessage("Photo file too big");
			//Option to retake photo or choose a new photo
		}
		
	}
	//US06.05.01
	//As a borrower, if photo downloads are disabled, I want the option of manually choosing inventory photos to download.
	//US06.02.01
	//As an owner, I want to view any attached photograph for an item.
	public void testLoadPhoto(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		//Code goes inside here to retake photo, and to accept/decline photo
		item1.addPhoto("Photo File"); 
		MyAccount.setInventory(MyInventory);
		assertTrue(MyAccount.Inventory(0).searchImage("Photo File").equals("Photo File"))
	}




}//end PhotographTest
