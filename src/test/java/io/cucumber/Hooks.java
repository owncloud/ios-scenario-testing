/**
 * ownCloud iOS Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import ios.AppiumManager;
import utils.api.FilesAPI;
import utils.api.TrashbinAPI;
import utils.entities.OCFile;
import utils.log.Log;

public class Hooks {

    //Before every scenario
    @Before
    public void setup(Scenario scenario){
        Log.log(Level.FINE, "START SCENARIO EXECUTION: " + scenario.getName());
        AppiumManager.getManager().getDriver().launchApp();
    }

    //After every scenario
    @After
    public void tearDown(Scenario scenario)
            throws IOException, ParserConfigurationException, SAXException {
        cleanUp();
        Log.log(Level.FINE, "END SCENARIO EXECUTION: " + scenario.getName() + "\n\n");
        AppiumManager.getManager().getDriver().terminateApp("com.owncloud.ios-app");
    }

    private void cleanUp()
            throws IOException, ParserConfigurationException, SAXException {
        FilesAPI filesAPI = new FilesAPI();
        TrashbinAPI trashbinAPI = new TrashbinAPI();
        //First, remove leftovers in root folder. Just keeping the skeleton items
        ArrayList<OCFile> filesRoot = filesAPI.listItems("");
        for (OCFile iterator: filesRoot){
            if (!iterator.getName().equals("Documents") &&
                    !iterator.getName().equals("Photos") &&
                    !iterator.getName().equals("ownCloud Manual.pdf") &&
                    !iterator.getName().equals("Alice")
            ) {
                Log.log(Level.FINE, "CLEANUP: removing " + iterator.getName());
                filesAPI.removeItem(iterator.getName());
            }
        }
        //Second, remove leftovers in "Documents" folder, where some stuff is created inside
        ArrayList<OCFile> filesDocuments = filesAPI.listItems("/Documents");
        for (OCFile iterator: filesDocuments){
            if (!iterator.getName().equals("Example.odt") &&
                    !iterator.getName().equals("Documents"))  {
                Log.log(Level.FINE, "CLEANUP: removing " + iterator.getName());
                filesAPI.removeItem("/Documents/"+iterator.getName());
            }
        }
        //Third, empty trashbin
        trashbinAPI.emptyTrashbin();
    }
}
