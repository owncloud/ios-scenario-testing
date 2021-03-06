package ios;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
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
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions.VideoQuality;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utils.LocProperties;
import utils.log.Log;

public class CommonPage {

    protected static IOSDriver driver = AppiumManager.getManager().getDriver();
    protected static Actions actions;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    protected final String packag = LocProperties.getProperties().getProperty("appPackage");

    public CommonPage()  {
        actions = new Actions(driver);
    }

    public static void waitByXpath(int timeToWait, String resourceXpath){
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(resourceXpath)));
    }

    public static void waitById(int timeToWait, String resourceId){
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(resourceId)));
    }

    public static void waitById(int timeToWait, MobileElement mobileElement){
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        wait.until(ExpectedConditions.visibilityOf(mobileElement));
    }

    public static void waitByIdInvisible(int timeToWait, String resourceId){
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(resourceId)));
    }

    public static void waitByIdInvisible(int timeToWait, MobileElement mobileElement){
        WebDriverWait wait = new WebDriverWait(driver, timeToWait);
        wait.until(ExpectedConditions.invisibilityOf(mobileElement));
    }

    // The following method should be used only in case implicit/explicit waits are not valid for the
    // scenario. Blocking the thread is not desirable and using it is not a good solution.
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MobileElement findXpath(String xpath){
        return (MobileElement) driver.findElement(MobileBy.xpath(xpath));
    }

    public List<MobileElement> findListXpath(String xpath){
        return (List<MobileElement>) driver.findElements(MobileBy.xpath(xpath));
    }

    public MobileElement findId(String id){
        return (MobileElement) driver.findElement(MobileBy.id(id));
    }

    public List<MobileElement> findListId(String id){
        return (List<MobileElement>) driver.findElements(MobileBy.id(id));
    }

    public static void swipe (double startx, double starty, double endx, double endy)
            throws InterruptedException {
        //Need a short waiter. Should be improved.
        Thread.sleep(1000);
        Dimension size = driver.manage().window().getSize();
        int startX=(int)(size.width * startx);
        int startY=(int)(size.height * starty);
        int endX=(int)(size.width * endx);
        int endY=(int)(size.height * endy);
        TouchAction touchAction = new TouchAction(driver);
        touchAction.longPress(PointOption.point(startX, startY))
                .moveTo(PointOption.point(startX, endY)).perform().release();
    }

    public void browse(String folderName){
        Log.log(Level.FINE, "Starts: browse to " + folderName);
        String xpathFolder = "//XCUIElementTypeCell[@name=\""+folderName+"\"]";
        waitByXpath(10, xpathFolder);
        findXpath(xpathFolder).click();
    }

    protected void browseToFolder(String path){
        Log.log(Level.FINE, "Browse to folder: " + path);
        String completePath = Pattern.quote("/");
        String[] route = path.split(completePath);
        Log.log(Level.FINE, "Route lenght: " + route.length);
        for (int j = 0 ; j < route.length ; j++) {
            Log.log(Level.FINE, "Chunk: " + j + ": " + route[j]);
        }
        if (route.length > 0) { //we have to browse
            int i;
            for (i = 1 ; i < route.length ; i++) {
                Log.log(Level.FINE, "Browsing towards: " + route[i]);
                browse(route[i]);
            }
        }
    }

    // This code comes from the Appium official docu
    // http://appium.io/docs/en/writing-running-appium/tutorial/swipe/simple-element/
    public void swipeElementIOS(MobileElement el, String dir) {
        System.out.println("swipeElementIOS(): dir: '" + dir + "'"); // always log your actions

        // Animation default time:
        //  - iOS: 200 ms
        // final value depends on your app and could be greater
        final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 500; // ms

        // init screen variables
        Dimension dims = driver.manage().window().getSize();
        Rectangle rect = el.getRect();

        // check element overlaps screen
        if (rect.x >= dims.width || rect.x + rect.width <= 0
                || rect.y >= dims.height || rect.y + rect.height <= 0) {
            throw new IllegalArgumentException("swipeElementIOS(): Element outside screen");
        }

        // init borders per your app screen
        // or make them configurable with function variables
        int leftBorder, rightBorder, upBorder, downBorder;
        leftBorder = 0;
        rightBorder = 0;
        upBorder = 0;
        downBorder = 0;

        // find rect that overlap screen
        if (rect.x < 0) {
            rect.width = rect.width + rect.x;
            rect.x = 0;
        }
        if (rect.y < 0) {
            rect.height = rect.height + rect.y;
            rect.y = 0;
        }
        if (rect.width > dims.width)
            rect.width = dims.width;
        if (rect.height > dims.height)
            rect.height = dims.height;

        PointOption pointOptionStart, pointOptionEnd;
        switch (dir) {
            case "DOWN": // from up to down
                pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                        rect.y + upBorder);
                pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                        rect.y + rect.height - downBorder);
                break;
            case "UP": // from down to up
                pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                        rect.y + rect.height - downBorder);
                pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                        rect.y + upBorder);
                break;
            case "LEFT": // from right to left
                pointOptionStart = PointOption.point(rect.x + rect.width - rightBorder,
                        rect.y + rect.height / 2);
                pointOptionEnd = PointOption.point(rect.x + leftBorder,
                        rect.y + rect.height / 2);
                break;
            case "RIGHT": // from left to right
                pointOptionStart = PointOption.point(rect.x + leftBorder,
                        rect.y + rect.height / 2);
                pointOptionEnd = PointOption.point(rect.x + rect.width - rightBorder,
                        rect.y + rect.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeElementIOS(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using TouchAction
        try {
            new TouchAction(driver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeElementIOS(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        wait(ANIMATION_TIME/1000);
    }

    public static void takeScreenshot (String name) {
        try {
            String sd = sdf.format(new Timestamp(System.currentTimeMillis()).getTime());
            File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShotFile, new File("screenshots/"+name+"_"+sd+".png"));
            Log.log(Level.FINE,"Take screenshot " + name + " at: " + sd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startRecording (){
        IOSStartScreenRecordingOptions iOSStartScreenRecordingOptions =
                new IOSStartScreenRecordingOptions()
                        .withVideoQuality(VideoQuality.MEDIUM);
        driver.startRecordingScreen(iOSStartScreenRecordingOptions);
    }

    public static void stopRecording (String filename){
        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        String destinationPath="video/" + filename + "_" +
                sdf.format(new Timestamp(System.currentTimeMillis()).getTime()) + ".mp4";
        Path path = Paths.get(destinationPath);
        try {
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeApp(){
        driver.removeApp(packag);
    }

    public void reinstallApp(){
        if (driver.isAppInstalled(packag)) {
            driver.removeApp(packag);
            driver.launchApp();
        }
    }
}