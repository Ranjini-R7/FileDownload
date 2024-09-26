package demotest;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class LaunchBrowser {

    public static void main(String[] args) {
        
        // Set the path for Edge WebDriver manually
        System.setProperty("webdriver.edge.driver", "C:\\Users\\HP\\.cache\\selenium\\msedgedriver\\win64\\128.0.2739.79\\msedgedriver.exe");
        
        // Set up the download directory path
        String parentDirectoryPath = System.getProperty("user.dir");
        String downloadFilepath = parentDirectoryPath + "\\downloads";
        
        // Set up EdgeOptions for handling file downloads
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);  // Set default download directory
        prefs.put("profile.default_content_settings.popups", 0);    // Disable download popups
        prefs.put("download.prompt_for_download", false);           // Disable download prompt
        prefs.put("download.directory_upgrade", true);              // Automatically download without asking
        prefs.put("safebrowsing.enabled", true);                    // Enable safe browsing to prevent download blocking

        // MIME types for automatic downloads (you can add more types if necessary)
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);  // Allow multiple downloads

        // Set the preferences in EdgeOptions
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");  // Add this to bypass origin restrictions
        options.setExperimentalOption("prefs", prefs);
        
        // Create an instance of the EdgeDriver with the options
        WebDriver driver = new EdgeDriver(options);
        
        // Maximize the browser window
        driver.manage().window().maximize();
        
        // Navigate to the desired page
        driver.get("https://pontoon.mozilla.org/");
        
        // Interact with the page
        WebElement startLocalizingButton = driver.findElement(By.linkText("Start Localizing Now"));
        startLocalizingButton.click();
        
        WebElement filterInput = driver.findElement(By.xpath("//input[@placeholder='Filter teams']"));
        filterInput.sendKeys("India");

        WebElement bengaliIndiaLink = driver.findElement(By.linkText("Bengali (India)"));
        bengaliIndiaLink.click();
        
        WebElement downloadIcon = driver.findElement(By.xpath("//i[@class='fa fa-cloud-download-alt']"));
        downloadIcon.click();
        
        WebElement downloadTerminologyLink = driver.findElement(By.linkText("Download Terminology"));
        downloadTerminologyLink.click();
        
        // You can verify the file has been downloaded by checking the downloads folder

        driver.quit();
    }
}
