package com.Cabs.BDD.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/Cabs/BDD/features", glue = { "com.Cabs.BDD.stepdefs",
        "com.Cabs.BDD.Hooks" }, plugin = {
                "pretty",
                "html:target/cucumber-reports/index.html",
                "json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml"
        }, monochrome = true)
public class TestRunner {
}
