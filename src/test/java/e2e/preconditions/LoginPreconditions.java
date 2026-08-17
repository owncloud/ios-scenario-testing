/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.preconditions;

import e2e.world.World;
import utils.LocProperties;

public class LoginPreconditions {

    private final World world;

    public LoginPreconditions(World world) {
        this.world = world;
    }

    public void logged(String userName) throws Throwable {
        if (!world.loginPage().loggedIn()) {
            String password = LocProperties.getProperties().getProperty("pwdDefault");
            world.loginPage().addAccount();
            world.loginPage().typeURL();
            world.loginPage().typeCredentials(userName, password);
            world.loginPage().submitLogin();
            world.loginPage().selectDrive();
        } else {
            world.loginPage().selectFirstBookmark();
        }
    }
}
