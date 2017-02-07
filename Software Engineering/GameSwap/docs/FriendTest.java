public class FriendTest extends ActivityInstrumentationTestCase2 {

	//Note 1: Current Account parameter are longer than actually stated in testcase
	//For now (+ simplicity + readability) we only use 3 parameter

	//Note 2: It is assumed in add Friend they automatically accept the request
	
	//Note 3: It is assumed SM.updateAccountServer will be able to match your
	//local save "Account object" with you server save "Account object"
		
	//Note 4:It is assumed you have connection

	private boolean connection = Boolean.TRUE; //Assumption connection = true

	public CreateAccountTest(){
        	super(tradegames.class);    //"tradegamesapp" is name of the app for now
        }
    
	//US02.01.01
	//As an owner, I want to track people I know. Adding a textual username should be //enough.
	//Test to find user profile 
    	public testFindUserProfile(){
        	ServerManage SM= new ServerManager();
        	Account accountOne = new Account("rupehra","Edmonton","rupehra@ualberta.ca");
		assertTrue(connection); //Place holder for Connection test
		SM.addAccountServer(accountOne);
		//ViewOtherAccount is a subclass of Account that allows only viewing other profile
		try{
			//Check for shareable item
			ViewOtherAccount ViewAccount = SM.ViewAccount("rupehra");
		}catch (ExistException e){
			//Can't find the account
			assertTrue(ViewInventory.exist()); 
		}
        }

	//US02.02.01
	//As an owner, I want to add friends
	//Test adding Friends
    	public testAddFriend(){
		ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		Account MyAccount = new Account("rupehra1","Edmonton","rupehra@ualberta.ca");
		Account FriendAccount = new Account("rupehra2","Edmonton","rupehra@ualberta.ca");
		SM.addAccountServer(MyAccount);
		SM.addAccountServer(FriendAccount);
		//Add friend to MyAccount Arraylist of friends
		MyAccount.addFriend("rupehra2");
		//Check to see if the friendlist array contain "rupehra2" (local)
		assertTrue(MyAccount.friendList.contain("rupehra2"));
		//Update Account on the server
		SM.updateAccountServer(MyAccount);
		Account AccountCheck = SM.loadAccountServer(MyAccount);
		//Check if friendlist in Server updated with the friend (server)
		assertTrue(AccountCheck.friendList.contain("rupehra2"));    	
	}
    
	//US02.03.01
	//As an owner, I want to remove friends    
	//Test Removing friends
    	public testRemoveFriend(){
        	ServerManage SM= new ServerManager();
		assertTrue(connection); //Place holder for Connection test
		Account MyAccount = new Account("rupehra1","Edmonton","rupehra@ualberta.ca");
		Account FriendAccount = new Account("rupehra2","Edmonton","rupehra@ualberta.ca");
		SM.addAccountServer(MyAccount);
		SM.addAccountServer(FriendAccount);
		MyAccount.addFriend("rupehra2");
		//Check to see if the friendlist array contain "rupehra2" (local)
		assertTrue(MyAccount.friendList.contain("rupehra2"));
		//Update Account on the server
		SM.updateAccountServer(MyAccount);
		Account AccountCheck = SM.loadAccountServer(MyAccount);
		//Check if friendlist in Server updated with the friend (server)
		assertTrue(AccountCheck.friendList.contain("rupehra2"));    	
		AccountCheck.delFriend("rupehra2");
		//Assumed SM knows you are refering to MyAccount object on server side
		//even through you are using AccountCheck in parameter
		//Maybe in SM it matches the username, etc
		SM.updateAccountServer(AccountCheck);
		Account AccountCheck2 = SM.loadAccountServer(AccountCheck);
		assertFalse(AccountCheck2.friendList.contain("rupehra2")); 
    	}

}//end FriendTest

