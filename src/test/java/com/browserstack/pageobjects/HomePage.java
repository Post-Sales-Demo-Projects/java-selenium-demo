package com.browserstack.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver webDriver;

    private String selectedProductName;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.selectedProductName = "";
    }

    private final By firstProductName = By.xpath("//*[@id=\"1\"]/p");
    private final By firstProductAddToCartButton = By.xpath("//*[@id=\"1\"]/div[4]");
    private final By cartPane = By.className("float-cart__content");
    private final By productCartText = By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]");

    public void selectFirstProductName() {
        setSelectedProductName(webDriver.findElement(firstProductName).getText());
    }

    public void clickAddToCartButton() {
        webDriver.findElement(firstProductAddToCartButton).click();
    }

    public void waitForCartToOpen() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartPane));
    }

    public String getProductCartText() {
        return webDriver.findElement(productCartText).getText();
    }

    public void setSelectedProductName(String selectedProductName) {
        this.selectedProductName = selectedProductName;
    }

    public String getSelectedProductName() {
        return selectedProductName;
    }
}
