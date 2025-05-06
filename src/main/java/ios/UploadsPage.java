package ios;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.entities.OCFile;
import utils.log.Log;

public class UploadsPage extends CommonPage {

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Add\"])[2]")
    private WebElement add;

    public static UploadsPage instance;

    private UploadsPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static UploadsPage getInstance() {
        if (instance == null) {
            instance = new UploadsPage();
        }
        return instance;
    }

    public void selectPhotoGallery(int selection) {
        Log.log(Level.FINE, "Starts: Select Photo Gallery");
        List<WebElement> images = driver.findElements(By.className("XCUIElementTypeImage"));
        int j = 0;
        for (int i = 0; i < images.size() && j < selection; i++) {
            WebElement image = images.get(i);
            //select only in the group of images available
            if (image.getAttribute("accessible").equals("true")) {
                image.click();
                j++;
            }
        }
        add.click();
    }

    public boolean photoUploaded(ArrayList<OCFile> listFiles, int photos) {
        Log.log(Level.FINE, "Starts photoUploaded: " + listFiles.size());
        int mediaUploaded = 0;
        for (OCFile ocfile : listFiles) {
            Log.log(Level.FINE, "Item name: " + ocfile.getName());
            if ((ocfile.getName().contains("Photo-")) || (ocfile.getName().contains("Video-"))) {
                mediaUploaded++;
            }
        }
        return photos == mediaUploaded;
    }

    public boolean photoDisplayed(int expectedPhotos) {
        Log.log(Level.FINE, "Starts photoDisplayed: " + expectedPhotos);
        // List of files is inside a visible collection view
        List<WebElement> collections = findListCss("XCUIElementTypeCollectionView");
        WebElement visibleCollection = collections.stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No visible XCUIElementTypeCollectionView found"));
        // One XCUIElementTypeCell for each item in the list. The footer is also a cell!!
        List<WebElement> cells = visibleCollection.findElements(By.className("XCUIElementTypeCell"));

        Log.log(Level.FINE, "Number of elements +1: " + cells.size()
                + " Expected: " + expectedPhotos);
        // The footer is also a cell, not interesting for us
        return (cells.size() -1 ) == expectedPhotos;
    }
}
