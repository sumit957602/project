package com.Cabs.BDD.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String screenshotDir = "target/ExtentReports/Screenshots/";
        String screenshotPath = screenshotDir + fileName;

        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDirectory = new File(screenshotDir);
            if (!screenshotDirectory.exists()) {
                screenshotDirectory.mkdirs();
            }

            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);

            FileUtils.copyFile(sourceFile, destFile);

            System.out.println("📸 Screenshot saved: " + screenshotPath);

            // Return relative path for ExtentReports
            return "./Screenshots/" + fileName;

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    public static String getRelativeScreenshotPath(String fullPath) {
        if (fullPath != null && fullPath.contains("Screenshots/")) {
            return "./Screenshots/" + fullPath.substring(fullPath.lastIndexOf("/") + 1);
        }
        return fullPath;
    }
}
