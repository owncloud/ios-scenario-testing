package android;

import org.openqa.selenium.By;

public class FileListPage extends AppiumManager {

    private String headertext_xpath = "//*[@text='Wrong username or password']";

    public FileListPage() {}

    public boolean isHeader(){
        return driver.findElements(By.xpath(headertext_xpath)).size() > 0;
    }

}
