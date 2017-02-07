public class TradeFriendTest extends ActivityInstrumentationTestCase2 {

	//Note 1: Current Account parameter are longer than actually stated in testcase
	//For now (+ simplicity + readability) we only use 3 parameter
	
	//Note 2: It is assumed you have a connection online, and therefore the testcases
	//are for online connection

	//Note 3: The set of test do not deal with interaction with server. i.e the
	//testcases do not deal with connecting account to server, accessing account of
	//borrower from server, etc. More advance test will be written later after
	//the completion of basic functionality of these test.

	//Note 4: It is also assumed that all item are in shareable (or public)

	//Note 5: These testcases

	public CreateAccountTest(){
        	super(tradegames.class);    //"tradegamesapp" is name of the app for now
        }

	//US04.01.01
	//As an borrower, I want to offer a trade with a friend. A trade will consist of an item from the owner's inventory 
	//and 0 or more items from the borrower's inventory.
	//Test if you can submit trade with no items
	public void testSubmitTradeEmpty(){
        	Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		//=====Borrower offers empty item=====/
		Borrower.addTradeMyItem();
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();
		//Borrower currenttrade is not empty
		assertFalse(Borrower.currentTrade().isEmpty());


	}
	//US04.01.01
	//As an borrower, I want to offer a trade with a friend. A trade will consist of an item from the owner's inventory 
	//and 0 or more items from the borrower's inventory.
	//Test if you can submit trade with no items
	public void testSubmitTrade(){
        	Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		//Fill the Borrower Inventory (to test for atleast 1 item trade)
		AccountInventory BorrowerInventory = new AccountInventory();
        	Item itemBorrower =new Item("Duty of Call", "2005", "New", TRUE);
        	BorrowerInventory.addItem(item1);
		Borrower.setInventory(OwnerInventory);
		//Borrower account is not empty
		assertTrue(Borrower.Inventory().notempty()); 

		//=====Borrower offers an item=====/
		Borrower.addTradeMyItem(Borrower.BorrowerInventory("Duty of Call"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();

		//Borrower currenttrade is not empty
		assertFalse(Borrower.currentTrade().isEmpty());	
	
	}

	//US04.02.01
	//As an owner, when a borrower offers a trade I will be notified of the trade.
	//Test to see if Owner has a notify and there is a current trade in owner
	public void testNotifyTrade(){
        	Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		Borrower.addTradeMyItem(Borrower.BorrowerInventory("Duty of Call"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();

		//Borrower currenttrade is not empty
		assertFalse(Borrower.currentTrade().isEmpty());	
		//Check to see if Owner got a notify
		assertFalse(Owner.getNotify().isEmpty());
		//Check that Owner currenttrade is not empty
		assertFalse(Owner.currentTrade().isEmpty());	
	}
	//US04.03.01
	//As an owner, I can accept or decline a trade. 
	//Test if you can accept a trade
    	public void testacceptTrade(){
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		Borrower.addTradeMyItem(Borrower.BorrowerInventory("Duty of Call"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();
		//Borrower currenttrade is not empty
		assertFalse(Borrower.currentTrade().isEmpty());	
		//Check to see if Owner got a notify
		assertFalse(Owner.getNotify().isEmpty());
		//Check that Owner currenttrade is not empty
		assertFalse(Owner.currentTrade().isEmpty());	
		//Find the trade and see if it initially set reply to NULL (Owner did not reply)
		assertNULL(Owner.currentTrade(0).getReply());
		//Set curent Trade as true (Owner accepts trade)
		Owner.currentTrade(0).setReply(TRUE);
		//Check to see if it know that the trade has been handled 
		assertTrue(Owner.currentTrade(0).getReply);
		//See if the result of the trade was "ACCEPTED" by Owner
		assertTrue(Owner.currentTrade(0).Result);
		//Check to see if Borrower knows that it has been accepted
		assertFalse(Borrower.getNotify().isEmpty());
		}
	//US04.03.01
	//As an owner, I can accept or decline a trade. 
	//Test if you can decline a trade
	public void testdeclineTrade(){
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		Borrower.addTradeMyItem(Borrower.BorrowerInventory("Duty of Call"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();
		//Borrower currenttrade is not empty
		assertFalse(Borrower.currentTrade().isEmpty());	
		//Check to see if Owner got a notify
		assertFalse(Owner.getNotify().isEmpty());
		//Check that Owner currenttrade is not empty
		assertFalse(Owner.currentTrade().isEmpty());	
		//Find the trade and see if it initially set reply to NULL (Owner did not reply)
		assertNULL(Owner.currentTrade(0).getReply());
		//Check to see if it know that the trade has been handled 
		Owner.currentTrade(0).setReply(TRUE);
		//See if the result of the trade was "DECLINED" by Owner
		assertfalse(Owner.currentTrade(0).getReply);
		//Check to see if Borrower knows that it has been declined
		assertFalse(Borrower.getNotify().isEmpty());
	}
	//US04.04.01
	//As an owner, upon declining a trade I can offer a counter trade initialized with the items of the declined trade.
	//US04.05.01
	//As a borrower or owner, when composing a trade or counter-trade I can edit the trade.
	//Test user ability to counter trade
	public void testSubmitCounterTrade(){
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		//Fill the Borrower Inventory (to test for atleast 1 item trade)
		AccountInventory BorrowerInventory = new AccountInventory();
        	Item itemBorrower =new Item("BORROWER", "2005", "New", TRUE);
        	BorrowerInventory.addItem(item1);
		Borrower.setInventory(OwnerInventory);
		//Borrower account is not empty
		assertTrue(Borrower.Inventory().notempty()); 

		Borrower.addTradeMyItem(Borrower.BorrowerInventory("BORROWER"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();

		//Check that Owner currenttrade is not empty
		assertFalse(Owner.currentTrade().isEmpty());	
		//Find the trade and see if it initially set reply to NULL (Owner did not reply)
		assertNULL(Owner.currentTrade(0).getReply());
		//Check to see if it know that the trade has been handled 
		Owner.currentTrade(0).setReply(TRUE);
		//See if the result of the trade was "DECLINED" by Owner
		assertfalse(Owner.currentTrade(0).getReply);

		//SOMETHING HERE IN UI STARTES A NEW TRADE (A COUNTER OFFER)

		//Owner submit a countertrade with same item 
		Owner.addTradeMyItem(Owner.OwnerInventory("Call of Duty"));
		Owner.addTradeForItem(Borrower.BorrowerInventory("BORROWER"));
		Owner.submitTrade();
		//Check to see if Borrower knows that it has been declined
		assertFalse(Borrower.getNotify().isEmpty());
		//Check to see if the decline notification went through (should be first notication)
		assertTrue(Borrower.getNotifty(0));
		//Check to see if the trade notification went through (should be the 2nd notitication)
		assertTrue(Borrower.getNotifty(1));

		//Borrower currenttrade is not empty
		assertFalse(Borrower.currentTrade().isEmpty());
		//Owner currenttrade is not empty
		assertFalse(Owner.currentTrade().isEmpty());
	}
	//US04.06.01
	//As a borrower, when composing a trade or counter-trade I can delete the trade
	//test if you can decline current trade midway
	public void testCancelCurrentWorkingTrade(){
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		//Fill the Borrower Inventory (to test for atleast 1 item trade)
		AccountInventory BorrowerInventory = new AccountInventory();
        	Item itemBorrower =new Item("BORROWER", "2005", "New", TRUE);
        	BorrowerInventory.addItem(item1);
		Borrower.setInventory(OwnerInventory);
		//Borrower account is not empty
		assertTrue(Borrower.Inventory().notempty()); 

		Borrower.addTradeMyItem(Borrower.BorrowerInventory("BORROWER"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.clearTradeItem();
	
		//Check that Owner/Borrower currenttrade is not empty
		assertTrue(Owner.currentTrade().isEmpty());	
		assertTrue(Borrower.currentTrade().isEmpty());
			
		//A debug method for testing
		//There should be no trade item present
		assertNULL(Borrower.getTradeItem());
	}
	//US04.06.01
	//As a borrower, when composing a trade or counter-trade I can delete the trade
	//test if you can decline current counter-trade midway
	public void testtCancelCurrentWorkingCounterTrade(){
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		//Fill the Borrower Inventory (to test for atleast 1 item trade)
		AccountInventory BorrowerInventory = new AccountInventory();
        	Item itemBorrower =new Item("BORROWER", "2005", "New", TRUE);
        	BorrowerInventory.addItem(item1);
		Borrower.setInventory(OwnerInventory);
		//Borrower account is not empty
		assertTrue(Borrower.Inventory().notempty()); 

		Borrower.addTradeMyItem(Borrower.BorrowerInventory("BORROWER"));
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();

		//Check that Owner currenttrade is not empty
		assertFalse(Owner.currentTrade().isEmpty());	
		//Find the trade and see if it initially set reply to NULL (Owner did not reply)
		assertNULL(Owner.currentTrade(0).getReply());
		//Check to see if it know that the trade has been handled 
		Owner.currentTrade(0).setReply(TRUE);
		//See if the result of the trade was "DECLINED" by Owner
		assertfalse(Owner.currentTrade(0).getReply);
		//Owner submit a countertrade with same item 
		Owner.addTradeMyItem(Owner.OwnerInventory("Call of Duty"));
		Owner.addTradeForItem(Borrower.BorrowerInventory("BORROWER"));
		Owner.clearTradeItem();
	
		//Check that Owner/Borrower currenttrade is not empty
		assertTrue(Owner.currentTrade().isEmpty());	
		assertTrue(Borrower.currentTrade().isEmpty());
			
		//A debug method for testing
		//There should be no trade item present
		assertNULL(Owner.getTradeItem());
	}
	//US04.08.01
	//As an owner or borrower, I can browse all past and current trades involving me.
	//US04.09.01
	//As an owner or borrower, I can browse all past and current trades involving me as either borrower or owner. I should look at my trades and trades offered to me.
	//test if you can find/get info from current trades
	public void testBrowseCurrentTrade(){
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		Borrower.addTradeMyItem();
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();
		
		Borrower.addTradeMyItem();
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();

		Borrower.addTradeMyItem();
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();
		
		//Check that Owner/Borrower currenttrade is not empty
		assertTrue(Owner.currentTrade().isEmpty());	
		assertTrue(Borrower.currentTrade().isEmpty());
		
		//There should be 3 trades for owner
		assertFalse(Owner.currentTrade(0).isEmpty());
		assertFalse(Owner.currentTrade(1).isEmpty());
		assertFalse(Owner.currentTrade(2).isEmpty());
	
		//There should be 3 trades for borrower
		assertFalse(Borrower.currentTrade(0).isEmpty());
		assertFalse(Borrower.currentTrade(1).isEmpty());
		assertFalse(Borrower.currentTrade(2).isEmpty());
		
		//Check if the trade item detail is there (owner)
		assertTrue(Owner.currentTrade(0).wantItemName().equals("Call of Duty"));

		//Check if the trade item detail is there (borrower)
		assertTrue(Borrower.currentTrade(0).wantItemName().equals("Call of Duty"));
	}

	//US04.08.01
	//As an owner or borrower, I can browse all past and current trades involving me.
	//US04.09.01
	//As an owner or borrower, I can browse all past and current trades involving me as either borrower or owner. I should look at my trades and trades offered to me.
	//test if you can find/get info from current trades
	public void testBrowsePastTrade(){
		Account Owner = new Account("Owner","Edmonton","rupehra@ualberta.ca");
		Account Borrower = new Account("Borower","Edmonton","rupehra@ualberta.ca");

		//Fill the Owner Inventory
		AccountInventory OwnerInventory = new AccountInventory();
        	Item itemOwner =new Item("Call of Duty", "2005", "New", TRUE);
        	OwnerInventory.addItem(item1);
		Owner.setInventory(OwnerInventory);
		//Owner account is not empty
		assertTrue(Owner.Inventory().notempty()); 

		Borrower.addTradeMyItem();
		Borrower.addTradeForItem(Owner.OwnerInventory("Call of Duty"));
		Borrower.submitTrade();
		
		//Borrower currenttrade is not empty
		assertFalse(Borrower.currentTrade().isEmpty());	
		//Check that Owner currenttrade is not empty
		assertFalse(Owner.currentTrade().isEmpty());	

		//Check to see if the Owner past trade is empty
		assertTrue(Owner.pastTrade(0).isEmpty());
		//Check to see if the Borrower past trade is empty
		assertTrue(Borrower.pastTrade(0).isEmpty());

		//Find the trade and see if it initially set reply to NULL (Owner did not reply)
		assertNULL(Owner.currentTrade(0).getReply());
		//Set curent Trade as true (Owner accepts trade)
		Owner.currentTrade(0).setReply(TRUE);
		//Check to see if it know that the trade has been handled 
		assertTrue(Owner.currentTrade(0).getReply);
		//See if the result of the trade was "ACCEPTED" by Owner
		assertTrue(Owner.currentTrade(0).Result);
		//Check to see if Borrower knows that it has been accepted
		assertFalse(Borrower.getNotify().isEmpty());

		//Check to see if the past trade is empty Owner
		assertFalse(Owner.pastTrade(0).isEmpty());
		//Check to see if the past trade is empty Borrower
		assertFalse(Borrower.pastTrade(0).isEmpty());

		//Check if the past trade item detail is there (owner)
		assertTrue(Owner.pastTrade(0).wantItemName().equals("Call of Duty"));
		//Check if the past trade item detail is there (borrower)
		assertTrue(Borrower.pastTrade(0).wantItemName().equals("Call of Duty"));

	}

}//End TradeFriendTest
