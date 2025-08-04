package com.Cabs.BDD.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static String reportPath;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        reportPath = System.getProperty("user.dir") + "/target/ExtentReports/";

        // Create directory if it doesn't exist
        File reportDir = new File(reportPath);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        String reportFile = reportPath + "ExtentReport_" + timestamp + ".html";

        // Create SparkReporter
        sparkReporter = new ExtentSparkReporter(reportFile);

        // Configure SparkReporter
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Call Taxi Service Test Report");
        sparkReporter.config().setReportName("BDD Automation Test Results");
        sparkReporter.config().setEncoding("utf-8");

        // Create ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set system information
        extent.setSystemInfo("Application", "Call Taxi Service");
        extent.setSystemInfo("Test Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));

        System.out.println("✅ ExtentReports initialized: " + reportFile);
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
            System.out.println("✅ ExtentReports flushed successfully");
        }
    }

    public static String getReportPath() {
        return reportPath;
    }
}
