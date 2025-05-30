package ios;

import com.google.common.collect.ImmutableList;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions.VideoQuality;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import utils.log.Log;

public class CommonPage {

    @iOSXCUITFindBy(id = "Don’t Allow")
    protected List<WebElement> dontAllow;

    @iOSXCUITFindBy(id = "Allow")
    protected List<WebElement> allow;

    protected static IOSDriver driver = AppiumManager.getManager().getDriver();
    protected static Actions actions;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    protected static final int WAIT_TIME = 10;
    protected static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));

    public CommonPage() {
        actions = new Actions(driver);
    }

    public static void waitByXpath(String resourceXpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(resourceXpath)));
    }

    public static void waitById(String resourceId) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(resourceId)));
    }

    public static void waitById(WebElement mobileElement) {
        wait.until(ExpectedConditions.visibilityOf(mobileElement));
    }

    public static void waitByIdInvisible(String resourceId) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(resourceId)));
    }

    public static void waitByIdInvisible(WebElement mobileElement) {
        wait.until(ExpectedConditions.invisibilityOf(mobileElement));
    }

    public void waitBySpaces() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.id("No spaces")));
    }

    public WebElement findXpath(String xpath) {
        return driver.findElement(AppiumBy.xpath(xpath));
    }

    public List<WebElement> findListXpath(String xpath) {
        return driver.findElements(AppiumBy.xpath(xpath));
    }

    public WebElement findTextByXpath(String text) {
        return findXpath("//XCUIElementTypeStaticText[contains(@name, '" + text + "')]");
    }

    public WebElement findId(String id) {
        return driver.findElement(AppiumBy.id(id));
    }

    public List<WebElement> findListId(String id) {
        return driver.findElements(AppiumBy.id(id));
    }

    public List<WebElement> findListCss(String cssClass) {
        return driver.findElements(AppiumBy.cssSelector(cssClass));
    }

    public static void swipe(double startx, double starty, double endx, double endy) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * starty);
        int endY = (int) (size.height * endy);
        int startX = (int) (size.width * startx);
        int endX = (int) (size.width * endx);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    public void longPress(WebElement element) {
        Point location = element.getLocation();
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "longp");
        Sequence longPress = new Sequence(pointerInput, 0);
        longPress.addAction(pointerInput.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), location.x, location.y));
        longPress.addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(pointerInput.createPointerMove(Duration.ofSeconds(1),
                PointerInput.Origin.viewport(), location.x, location.y));
        longPress.addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(ImmutableList.of(longPress));

    }

    public void tap(int X, int Y) {
        Log.log(Level.FINE, "Starts: tap on X: " + X + ", Y: " + Y);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tapSeq = new Sequence(finger, 1);
        tapSeq.addAction(finger.createPointerMove(Duration.ofSeconds(4), PointerInput.Origin.viewport(), X, Y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tapSeq));
    }

    public void browse(String folderName) {
        Log.log(Level.FINE, "Starts: browse to " + folderName);
        findId(folderName).click();
    }

    protected void browseToFolder(String path) {
        Log.log(Level.FINE, "Starts: Browse to folder: " + path);
        String completePath = Pattern.quote("/");
        String[] route = path.split(completePath);
        Log.log(Level.FINE, "Route length: " + route.length);
        for (int j = 0; j < route.length; j++) {
            Log.log(Level.FINE, "Chunk: " + j + ": " + route[j]);
        }
        if (route.length > 0) { //browse
            int i;
            for (i = 1; i < route.length; i++) {
                Log.log(Level.FINE, "Browsing towards: " + route[i]);
                browse(route[i]);
            }
        }
    }

    //Need to change the context to springboard because buttons are not included in app
    //https://appium.github.io/appium-xcuitest-driver/latest/guides/troubleshooting/#interact-with-dialogs-managed-by-comapplespringboard

    public void acceptLibraryPermission(){
        Log.log(Level.FINE, "Starts: Give full access to library");
        driver.activateApp("com.apple.springboard");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            WebElement allowPermissionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//XCUIElementTypeButton[@label=\"Allow Full Access\"]")));
            allowPermissionButton.click();
        } catch (TimeoutException e) {
            Log.log(Level.FINE, "Permission dialog already granted or not found");
        }
        //return the control to the app
        driver.activateApp("com.owncloud.ios-app");
    }

    public void acceptNotifications(){
        Log.log(Level.FINE, "Starts: accept Notifications Permissions");
        driver.activateApp("com.apple.springboard");
        if (!allow.isEmpty()) {
            allow.get(0).click();
        }
        //return the control to the app
        driver.activateApp("com.owncloud.ios-app");
    }

    public static void takeScreenshot(String name) {
        try {
            String sd = sdf.format(new Timestamp(System.currentTimeMillis()).getTime());
            File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShotFile, new File("screenshots/" + name + "_" + sd + ".png"));
            Log.log(Level.FINE, "Take screenshot " + name + " at: " + sd);
        } catch (IOException e) {
            Log.log(Level.FINE, e.getMessage());
        }
    }

    public static void startRecording() {
        IOSStartScreenRecordingOptions iOSStartScreenRecordingOptions =
                new IOSStartScreenRecordingOptions()
                        .withVideoQuality(VideoQuality.MEDIUM);
        driver.startRecordingScreen(iOSStartScreenRecordingOptions);
    }

    public static void stopRecording(String filename) {
        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        String destinationPath = "video/" + filename + "_" +
                sdf.format(new Timestamp(System.currentTimeMillis()).getTime()) + ".mp4";
        Path path = Paths.get(destinationPath);
        try {
            Files.write(path, data);
        } catch (IOException e) {
            Log.log(Level.FINE, e.getMessage());
        }
    }
}
