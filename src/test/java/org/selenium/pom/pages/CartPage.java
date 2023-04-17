package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class CartPage extends BasePage {

//    private final By productName = By.cssSelector(".product-name > a");
//    private final By checkoutButton = By.cssSelector(".checkout-button");
//    private final By cartHeading = By.cssSelector(".has-text-align-center");

    @FindBy(css = ".product-name > a") private WebElement productName;
    @FindBy(how = How.CSS,using = ".checkout-button")@CacheLookup private  WebElement checkoutButton;


    public CartPage(WebDriver driver) {

        super(driver);
        PageFactory.initElements(driver, this);
    }


    public String getProductName() {

        return wait.until(ExpectedConditions.visibilityOf(productName)).getText();
    }

    public CheckoutPage Checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutPage(driver);
    }
}
