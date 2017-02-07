public class InventoryTest extends ActivityInstrumentationTestCase2{

	//Note 1: Current Item parameter are longer than actually stated in testcase
	//For now (+ simplicity + readability) we only use 3 parameter

	//Note 2: In 2nd set of test we assume there a connection and you are.
	//Viewing your own inventory. Testcases will change 
	//once we ensure the basic functionality of Account are working

	//Note 3: In 1st set of test they are just testing the basically functionality
	//of the inventory system and does not deal with loading inventory/item from
	//user's account on the server. Those will be their own testcases added later
	//Once basic functionality is implemented

	//Note 4: In 3rd set of Test we assume user and other are already friend

	//Note 5: we did not do a testcase for US01.07.01
	//since it hard to test without a design inplace for it

	//Note 6: for simplicity we only had 1 category for test for US01.06.01.

	private boolean connection = Boolean.TRUE; //Assumption connection = true

	public OwnerInventoryTest(){
       		super(tradegames.class)
        }

	//=======1st Set of Test========//
	//Deals with Inventory item management


	//Test add item to inventory
	public void testAddOwnerItem(){
		AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
        	MyInventory.addItem(item1);
		//Make sure we have successfully added the item
        	assertTrue(MyInventory.hasItem(item1));
        }
	//Test empty Parameter
	//When developing the program there will be different exception to each paremeter
	//so we can tell the user which one is wrong. For now we are using a blanket statement
	public void testItemInvalidInformation(){
        	AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("", "", "",NULL);
        	try{
        		MyInventory.addItem(item1);
        	}catch(InvalidItemNameException e){
        		displayMessage("Enter the a proper Parameter");
        	}
        }
	//US01.05.01
	//As an owner, I want to delete inventory items.
	//Test removing item from inventory
	public void testRemoveOwnerItem()[
        	AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		MyInventory.addItem(item1);
		//Make sure we have successfully added the item
		assertTrue(MyInventory.hasItem(item1));
  		MyInventory.delItem(item1);
		//Make sure we have successfully deleted the item
        	assertFalse(MyInventory.hasItem(item1));
		//Test multiple delelte
		Item item2 =new Item("Call of Buddy", "2005", "New", FALSE);
		Item item3 =new Item("Call of Stranger", "2005", "New", FALSE);
		MyInventory.addItem(item1);
		MyInventory.addItem(item2);
		MyInventory.addItem(item3);
		//Make sure we have successfully added the item
		assertTrue(MyInventory.hasItem(item1));
		assertTrue(MyInventory.hasItem(item2));
		assertTrue(MyInventory.hasItem(item3));
		//We should be able to del multiple item using .delItem
  		MyInventory.delItem(item1,item2,item3);
		//Make sure we have successfully deleted the item
        	assertFalse(MyInventory.hasItem(item1));
		assertFalse(MyInventory.hasItem(item2));
		assertFalse(MyInventory.hasItem(item3));

        }
	//US01.04.01
	//As an owner, I want to edit and modify inventory items.
	//Test editing an item 
	public void testEditOwnerItem(){
        	AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		MyInventory.addItem(item1);
		//To edit an item it is assumed it already exist
		//Therefore we need to load it from inventory
        	Item item2 = MyInventory.loadItem(item1);
		//Check to see if item was loaded properly
		assertTrue(item1 == item2);
		//Check the current name of item
		assertTrue(item2.getName().equal("Call of Duty"));	
        	item2.setName("Call of Buddy");
		//Check if change in name worked
        	assertTrue(item2.getName().equal("Call of Buddy"));	
	}
	//US01.03.01
	//As an owner, not every item in my inventory will be shared or listed. Items that are //not to publicly shared will not be. 
	//As an owner, I might use them for trades.
	//Test item shareStatus
	public void testItemShareStatus(){
        	AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		Item item2 =new Item("Buddy", "2005", "New", TRUE);
		//The current status should be false
        	assertFalse(item1.getShare());
		assertTrue(item2.getShare());
		try{	
			//It should not add item1
        		Item itemShare = MyInventory.loadShare(item1);
        	}catch(ShareableException e){
			//We should see this message
        		displayMessage("Item not shareable");
        	}
		try{
			//Should add item2
        		Item itemShare = MyInventory.loadShare(item2);
        	}catch(ShareableException e){
			//Should not see this message
        		displayMessage("Item not shareable");
        	}
		//We should have loaded item2
		assertTrue(item2.getName().equal("Buddy"));	
		assertTrue(itemShare.getShare());
        }
        //US01.06.01
        //As an owner,I want the category for an item to be one of 10relevant categories for<THINGS>.
	//Check if user has a Category
	public void testItemCategory(){
        	AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		MyInventory.addItem(item1);
        	assertTrue(Item.getCategory().equals("New"));
        }

	//========2nd set of more advance Inventory Test============//
	//Deals with the Viewing of inventory items of user
	//SM.ViewAccountInventory("____") return Arraylist<items> that are shared    


	//us01.02.01
	//As an owner, I want to view my inventory and its details. 
	//Test finding Inventory
	public void testFindInventory(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		Account MyAccount = new Account("rupehra","Edmonton","rupehra@ualberta.ca");
		AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		MyInventory.addItem(item1);
		//Connect inventory to an account
		MyAccount.setInventory(MyInventory);
		//User account is not empty
		assertTrue(MyAccount.Inventory().notempty()); 
		//Check to see if inventory has "Call of Duty"
		assertTrue(MyAccount.myInventory("Call of Duty"));
        }
	//US01.03.01
	//As an owner, I want to view each of my inventory items.
	//Test if we can view one items detail
	public void testViewItemDetail(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		AccountInventory MyInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", FALSE);
		MyInventory.addItem(item1);
		//Connect inventory to an account
		MyAccount.setInventory(MyInventory);
		///Make sure Inventory is not empty
		assertFalse(FriendAccount.Inventory().notempty());
		//Makre sure item detail was saved correctly
		assertTrue(FriendAccount.Inventory(0).getName(search).equals("Call of Duty"))
	}
	
	
	//========3rd set of more advance Inventory Test============//
	//Deals with viewing other people's inventory

	//Note 1: ViewOtherAccountInventory is subclass of AccountInventory that can only 
	//View Inventory not edit (A.K.A used to view other people's inventory)	
	//SM.ViewAccountInventory("____") return Arraylist<items> that are shared     


	//US03.01.01
	//As an borrower, I want to search the inventories of my friends.
	//US03.02.01
	//As an owner, any of my publicly shared items will be browseable / searchable by my friends.
	//test open friend inventory that is shareable
	public void testOpenFriendInv(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		Account FriendAccount = new Account("rupehra","Edmonton","rupehra@ualberta.ca");
		AccountInventory FriendInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", TRUE);
		FriendInventory.addItem(item1);
		//Connect inventory to an account
		FriendAccount.setInventory(FriendInventory);
		//Ask to server to find the inventory of the account
		try{	
			//Check for shareable item
			ViewOtherAccountInventory ViewInventory = SM.ViewAccountInventory("rupehra");
		}catch (ShareableException e){
			assertTrue(ViewInventory.notempty()); 
		}
		//Make sure Inventory is not empty
		assertFalse(FriendAccount.Inventory().notempty());
		//Makre sure item detail was saved correctly
		assertTrue(FriendAccount.Inventory(0).getName(search).equals("Call of Duty"))
		
	}
	//US03.01.03
	//As an borrower, I want to search/browse the inventories of my friends by textual query..
	//test search friend inventory by text that is shareable
	public void testBrowseFriendInventoryText(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		Account FriendAccount = new Account("rupehra","Edmonton","rupehra@ualberta.ca");
		AccountInventory FriendInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", TRUE);
		FriendInventory.addItem(item1);
		//Connect inventory to an account
		FriendAccount.setInventory(FriendInventory);
		try{
			//Check for shareable item
			ViewOtherAccountInventory ViewInventory = SM.ViewAccountInventory("rupehra");
		}catch (ShareableException e){
			assertTrue(ViewInventory.notempty()); 
		}
		String search = "Call";
		//Make sure Inventory is not empty
		assertFalse(FriendAccount.Inventory().notempty());
		//Makre sure item detail was saved correctly
		assertTrue(FriendAccount.Inventory(0).searchName(search).equals("Call of Duty"))
	}
	//US03.01.02
	//As an borrower, I want to search/browse the inventories of my friends by category.
	//test search friend inventory by category that is shareable
	public void testBrowseFriendInventoryCategory(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		Account FriendAccount = new Account("rupehra","Edmonton","rupehra@ualberta.ca");
		AccountInventory FriendInventory = new AccountInventory();
        	Item item1 =new Item("Call of Duty", "2005", "New", TRUE);
		FriendInventory.addItem(item1);
		//Connect inventory to an account
		FriendAccount.setInventory(FriendInventory);
		try{
			//Check for shareable item
			ViewOtherAccountInventory ViewInventory = SM.ViewAccountInventory("rupehra");
		}catch (ShareableException e){
			assertTrue(ViewInventory.notempty()); 
		}
		String Category = "New";
		//Make sure Inventory is not empty
		assertFalse(FriendAccount.Inventory().notempty());
		//Makre sure item detail was saved correctly
		assertTrue(FriendAccount.Inventory(0).searchCategory(Category).equals("Call of Duty"))
	}

}//End Inventorytest

