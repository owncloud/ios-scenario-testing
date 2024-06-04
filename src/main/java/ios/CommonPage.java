package ios;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
import utils.LocProperties;
import utils.api.AuthAPI;
import utils.log.Log;

public class CommonPage {

    @iOSXCUITFindBy(id = "Don’t Allow")
    protected List<WebElement> dontAllow;

    @iOSXCUITFindBy(id = "Allow")
    protected List<WebElement> allow;

    protected static IOSDriver driver = AppiumManager.getManager().getDriver();
    protected static Actions actions;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    protected final String packag = LocProperties.getProperties().getProperty("appPackage");
    protected String authType = "";

    public CommonPage() {
        actions = new Actions(driver);
        //Determine auth method
        AuthAPI authAPI = new AuthAPI();
        try {
            if (authType.equals("")) { //Check auth type onlyonce
                authType = authAPI.checkAuthMethod();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitByXpath(int timeToWait, String resourceXpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(resourceXpath)));
    }

    public static void waitById(int timeToWait, String resourceId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(resourceId)));
    }

    public static void waitById(int timeToWait, WebElement mobileElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOf(mobileElement));
    }

    public static void waitByIdInvisible(int timeToWait, String resourceId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(resourceId)));
    }

    public static void waitByIdInvisible(int timeToWait, WebElement mobileElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.invisibilityOf(mobileElement));
    }

    // The following method should be used only in case implicit/explicit waits are not valid for the
    // scenario. Blocking the thread is not desirable and using it is not a good solution.
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement findXpath(String xpath) {
        return (WebElement) driver.findElement(AppiumBy.xpath(xpath));
    }

    public List<WebElement> findListXpath(String xpath) {
        return (List<WebElement>) driver.findElements(AppiumBy.xpath(xpath));
    }

    public WebElement findId(String id) {
        return (WebElement) driver.findElement(AppiumBy.id(id));
    }

    public List<WebElement> findListId(String id) {
        return (List<WebElement>) driver.findElements(AppiumBy.id(id));
    }

    public static void swipe(double startx, double starty, double endx, double endy) {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * startx);
        int startY = (int) (size.height * starty);
        int endX = (int) (size.width * endx);
        int endY = (int) (size.height * endy);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    public void browse(String folderName) {
        Log.log(Level.FINE, "Starts: browse to " + folderName);
        //Need a waiter till notification gones. To improve
        wait(5);
        findId(folderName).click();
    }

    protected void browseToFolder(String path) {
        Log.log(Level.FINE, "Browse to folder: " + path);
        String completePath = Pattern.quote("/");
        String[] route = path.split(completePath);
        Log.log(Level.FINE, "Route lenght: " + route.length);
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

    public static void takeScreenshot(String name) {
        try {
            String sd = sdf.format(new Timestamp(System.currentTimeMillis()).getTime());
            File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShotFile, new File("screenshots/" + name + "_" + sd + ".png"));
            Log.log(Level.FINE, "Take screenshot " + name + " at: " + sd);
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}