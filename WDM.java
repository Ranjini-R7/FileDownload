package demotest;

import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FileDownload {

    public static void main(String[] args) {

        // Use WebDriverManager to automatically download and set up the correct version of EdgeDriver
        WebDriverManager.edgedriver().setup();
        
        // Manually set the download directory
        String downloadFilepath = "C:\\Users\\HP\\Downloads\\CustomDownloadDirectory";  // Replace with your desired path
        
        // Set up EdgeOptions for handling file downloads
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);  // Set the default download directory
        prefs.put("profile.default_content_settings.popups", 0);    // Disable download popups
        prefs.put("download.prompt_for_download", false);           // Disable download prompt
        
        // Allow automatic download of .tbx files without user interaction
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);  // Allow automatic downloads
        
        // Handle .tbx files
        prefs.put("download.extensions_to_open", "application/x-tbx");  // MIME type for TBX files

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
        
        // After downloading, the file should be saved directly to the specified directory without prompting

        driver.quit();
    }
}
