package cmput301t4.gameswap;

import junit.framework.TestCase;

import cmput301t4.gameswap.Managers.ServerManager;

/**
 * Created by dren on 11/2/15.
 */
public class ServerTest extends TestCase {

    public void testServerGet(){
        ServerManager server = new ServerManager();
        ServerManager.getUserOnline("daniel");
    }
}
