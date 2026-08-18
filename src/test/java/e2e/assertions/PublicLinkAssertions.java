/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package e2e.assertions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import e2e.model.OCShare;
import e2e.support.shares.ShareUtils;
import e2e.world.World;

public class PublicLinkAssertions {

    private final World world;

    public PublicLinkAssertions(World world) {
        this.world = world;
    }

    public void linkCreatedWithFields(String itemName, Map<String, String> fields)
            throws IOException, ParserConfigurationException, SAXException {
        String linkName = "";
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getKey().equals("name")) {
                linkName = entry.getValue();
                assertTrue(world.sharePage().isNameCorrect(entry.getValue()));
            }
        }
        world.sharePage().openPublicLink(linkName);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "password" -> assertTrue(world.publicLinkPage().isPasswordEnabled(itemName, entry.getValue()));
                case "permissions" -> assertTrue(world.publicLinkPage().isPermissionEnabled(entry.getValue()));
                case "expiration" -> assertTrue(world.publicLinkPage().isExpirationCorrect(entry.getValue()));
                case "name" -> assertTrue(world.publicLinkPage().isNameCorrect(entry.getValue()));
            }
        }
        OCShare share = world.shareAPI().getShare(itemName);
        assertTrue(ShareUtils.checkCorrectShare(share, fields));
    }

    public void linkNotExisting(String itemName)
            throws IOException, ParserConfigurationException, SAXException {
        assertFalse(world.sharePage().isItemInListLinks(itemName));
        ArrayList<OCShare> shares = world.shareAPI().getLinksByDefault();
        assertTrue(shares.isEmpty());
    }
}
