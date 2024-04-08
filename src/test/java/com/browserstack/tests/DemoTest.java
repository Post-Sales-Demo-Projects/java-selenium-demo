package com.browserstack.tests;

import com.browserstack.pageobjects.HomePage;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DemoTest {
    public static WebDriver driver;
    public static HomePage homePage;

    public static void setupWebDriver() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, String> bstackOptions = new HashMap<>();
        capabilities.setCapability("bstack:options", bstackOptions);
        driver = new RemoteWebDriver(
                new URL("https://hub.browserstack.com/wd/hub"), capabilities);

        homePage = new HomePage(driver);
    }

    public static void markTest(String result, String reason) {
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        JSONObject executorObject = new JSONObject();
        JSONObject argumentsObject = new JSONObject();

        if (result.equals("passed")) {
            argumentsObject.put("status", "passed");
            argumentsObject.put("reason", reason);
        } else if (result.equals("failed")) {
            argumentsObject.put("status", "failed");
            argumentsObject.put("reason", reason);
        } else {
            System.out.println("Incorrect string sent to function.");
        }

        executorObject.put("action", "setSessionStatus");
        executorObject.put("arguments", argumentsObject);
        jse.executeScript(String.format("browserstack_executor: %s", executorObject));
    }

    public static void main(String[] args) throws MalformedURLException {
        setupWebDriver();

        driver.get("https://www.bstackdemo.com");
        driver.manage().window().maximize();

        try {
            homePage.selectFirstProductName();
            homePage.clickAddToCartButton();
            Thread.sleep(2000);

            homePage.waitForCartToOpen();
            Assert.assertEquals(homePage.getSelectedProductName(), homePage.getProductCartText());

            markTest("passed", "This test has passed");

            driver.quit();
        } catch (Exception e) {
            markTest("failed", e.toString());
            driver.quit();
        }
    }
}
