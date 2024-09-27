import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class FileDownload {

    public static void main(String[] args) throws InterruptedException {
        
        // Setup WebDriver to navigate to the page and get the download URL
        System.setProperty("webdriver.edge.driver", "C:\\Users\\HP\\.cache\\selenium\\msedgedriver\\win64\\128.0.2739.79\\msedgedriver.exe");
        
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new EdgeDriver(options);
        driver.manage().window().maximize();

        // Navigate to the desired page
        driver.get("https://pontoon.mozilla.org/");
        
        // Interact with the page to locate the download URL
        WebElement startLocalizingButton = driver.findElement(By.linkText("Start Localizing Now"));
        startLocalizingButton.click();
        
        WebElement filterInput = driver.findElement(By.xpath("//input[@placeholder='Filter teams']"));
        filterInput.sendKeys("India");

        WebElement bengaliIndiaLink = driver.findElement(By.linkText("Bengali (India)"));
        bengaliIndiaLink.click();
        
        WebElement downloadIcon = driver.findElement(By.xpath("//i[@class='fa fa-cloud-download-alt']"));
        downloadIcon.click();
        
        WebElement downloadTerminologyLink = driver.findElement(By.linkText("Download Terminology"));
        String downloadUrl = downloadTerminologyLink.getAttribute("href");  // Get the download URL

        System.out.println("Download URL: " + downloadUrl);
        
        // Close the browser as the download URL is now available
        driver.quit();
        
        // Now download the file via HTTP request
        try {
            downloadFileFromURL(downloadUrl, "C:\\Users\\HP\\Downloads\\CustomDownloadDirectory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to download file via HTTP request
    public static void downloadFileFromURL(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // Check if the response is OK
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");

            if (disposition != null && disposition.contains("filename=")) {
                // Extracts the file name from the "Content-Disposition" header
                fileName = disposition.split("filename=")[1].replace("\"", "");
            } else {
                // Get the file name from the URL if no "Content-Disposition" header is found
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            }

            System.out.println("Downloading file: " + fileName);

            // Open input stream from the HTTP connection
            try (BufferedInputStream in = new BufferedInputStream(httpConn.getInputStream());
                 FileOutputStream out = new FileOutputStream(saveDir + "\\" + fileName)) {

                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                
                // Write the file to the local system
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                System.out.println("File downloaded to: " + saveDir + "\\" + fileName);
            }

        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
}
