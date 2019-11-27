package android;

import org.openqa.selenium.By;

public class RemoveDialogPage extends CommonPage{

    private final String buttonYes_id = "android:id/button1";
    private final String buttonLocal_id = "android:id/button2";
    private final String buttonNo_id = "android:id/button3";

    public RemoveDialogPage() {
        super();
    }

    public void removeAll(){
        driver.findElement(By.id(buttonYes_id)).click();
    }

    public void dontRemove(){
        driver.findElement(By.id(buttonNo_id)).click();
    }

    public void onlyLocal(){
        driver.findElement(By.id(buttonLocal_id)).click();
    }

}
