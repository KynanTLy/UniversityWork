public class OfflineTest extends ActivityInstrumentationTestCase2 {

	//Note 1: Current Account parameter are longer than actually stated in testcase
	//For now (+ simplicity + readability) we only use 3 parameter
	
	//Note 2: It is assumed you have a connection online, and therefore the testcases
	//are for online connection

	//Note 3: usercase US09.03.01. We not test for if there a cache of friend inventory
	//we assumed there is one.

	public CreateAccountTest(){
        	super(tradegames.class);    //"tradegamesapp" is name of the app for now
        }
	
	//US09.01.01
	//As an owner, I want to make inventory items while offline, and push changes once I get connectivity.
	//Test adding an item offline
	public void testAddItemOffline(){
		Account MyAccount = new Account("rupehra","Edmonton","rupehra@ualberta.ca");
                AccountInventory OfflineInventory = FindOfflineAccountInventory(MyAccount);
                Item item1 = new Item("Call of Duty", "2005", "New", TRUE);
                OfflineInventory.addItemOffline(item1);
		MyAccount.setOfflineInventory(OfflineInventory);

               	assertTrue(MyAccount.OfflineInventory().notempty()); 
		//Check to see if inventory has "Call of Duty"
		String search = "Call of Duty";
		assertTrue(MyAccount.OfflineInventory(0).getName(search).equals("Call of Duty"));
		//Check push signal is set on (i.e need to update to server)
		assertTrue(MyAccount.OfflineInventory(0).isPushed());
	}
	//US09.02.01
	//As a borrower, I want to make trades while offline, and push changes online once I get connectivity.
	//US09.03.01
	//As a borrower, I should be able to browse the inventories of friends that I previously looked at. Cache the inventories of the friends.
	//Testing offline trade
	public void testSubmitOfflineTrade()//us.09.02.01
	{
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerOfflineInventory = FindOfflineAccountInventory(Owner);
                Item item1 = new Item("Call of Duty", "2005", "New", TRUE);
                OwnerOfflineInventory.addItemOffline(item1);
		Owner.setOfflineInventory(OfflineInventory);

		//Fill the Borrower Inventory (to test for atleast 1 item trade)
		AccountInventory BorrowerOfflineInventory = FindOfflineAccountInventory(Borrower);
        	Item itemBorrower =new Item("BORROWER", "2005", "New", TRUE);
        	BorrowerOfflineInventory.addItemOffline(itemBorrower);
		Borrower.setOfflineInventory(BorrowerOfflineInventory);
		//Borrower account is not empty

		Borrower.addTradeMyItem(Borrower.BorrowerInventory("BORROWER"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitOfflineTrade();
		
		//Check that Owner/Borrower currenttrade is not empty
		assertFalse(Owner.OfflineTrade().isEmpty());	
		//Check push signal is set on (i.e need to update to server)
		assertTrue(Owner.OfflineTrade(0).isPushed());


	}


}//end OfflineTest
