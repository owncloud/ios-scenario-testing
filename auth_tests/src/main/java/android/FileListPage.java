package android;

import org.openqa.selenium.By;

public class FileListPage extends CommonPage {

    private String toolbar_id = "toolbar";

    public FileListPage() {
        super();
    }

    public boolean isHeader(){
        return !driver.findElements(By.id(toolbar_id)).isEmpty();
    }
}
