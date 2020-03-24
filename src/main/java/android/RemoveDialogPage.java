package android;

import org.openqa.selenium.By;

import java.util.logging.Level;

import utils.log.Log;

public class RemoveDialogPage extends CommonPage{

    private final String buttonYes_id = "android:id/button1";
    private final String buttonLocal_id = "android:id/button2";
    private final String buttonNo_id = "android:id/button3";

    public RemoveDialogPage() {
        super();
    }

    public void removeAll(){
        Log.log(Level.FINE, "Starts: Remove from server and local");
        driver.findElement(By.id(buttonYes_id)).click();
    }

    public void dontRemove(){
        Log.log(Level.FINE, "Starts: Cancel Remove");
        driver.findElement(By.id(buttonNo_id)).click();
    }

    public void onlyLocal(){
        Log.log(Level.FINE, "Starts: Remove only from local");
        driver.findElement(By.id(buttonLocal_id)).click();
    }

}
