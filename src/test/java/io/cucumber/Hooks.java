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
        ArrayList<OCFile> filesRoot = filesAPI.listItems("");
        for (OCFile iterator: filesRoot){
            if (!iterator.getName().equals("Documents") &&
                    !iterator.getName().equals("Photos") &&
                    !iterator.getName().equals("ownCloud Manual.pdf")
            ) {
                Log.log(Level.FINE, "CLEANUP: removing" + iterator.getName());
                filesAPI.removeItem(iterator.getName());
            }
        }
    }
}
