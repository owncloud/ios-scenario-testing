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
import java.util.logging.Level;

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

    public static void swipe (double startx, double starty, double endx, double endy) {
        Dimension size = driver.manage().window().getSize();
        int startY=(int)(size.height * starty);
        int endY=(int)(size.height * endy);
        int startX=(int)(size.width * startx);
        int endX=(int)(size.height * endx);
        TouchAction ts = new TouchAction(driver);
        ts.longPress(PointOption.point(startX, startY))
                .moveTo(PointOption.point(startX, endY)).release().perform();
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
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
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