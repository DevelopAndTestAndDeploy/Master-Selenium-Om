package org.selenium.pom.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.devtools.v85.backgroundservice.BackgroundService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.User;

import java.time.Duration;
import java.util.List;

public class CheckoutPage extends BasePage {

    private final By firstNameField = By.id("billing_first_name");
    private final By lastNameField = By.id("billing_last_name");
    private final By addressLineOneField = By.id("billing_address_1");
    private final By billingCityField = By.id("billing_city");
    private final By billingPostcodeField = By.id("billing_postcode");
    private final By billingEmailField = By.id("billing_email");
    private final By placeOrderField = By.id("place_order");

    private final By successNotice =  By.cssSelector(".woocommerce-notice");

    private final By clickHereToLoginLink = By.className("showlogin");
    private final By userNameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.name("login");
    private final By overLay = By.cssSelector(".blockUI.blockOverLay");
    private final By countryDropDown = By.id("billing_country");
    private final By stateDropDown = By.id("billing_state");

    private final By alternativeCountryDropDown = By.id("select2-billing_country-container");

    private final By alternativeStateDropDown = By.id("select2-billing_state-container");

    private final By directBankTransferRadioButton = By.id("payment_method_bacs");

    private final By productName = By.cssSelector("td[class='product-name']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage load() {
        load("/checkout/");
        return this;
    }

    public CheckoutPage enterFirstName(String firstName) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        e.clear();
        e.sendKeys(firstName);
       return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        e.clear();
        e.sendKeys(lastName);
        return this;
    }

    public CheckoutPage selectCountry(String countryName){

//        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(countryDropDown)));
//        select.selectByVisibleText(countryName);

        wait.until(ExpectedConditions.elementToBeClickable(alternativeCountryDropDown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='"+countryName+"']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();
        return this;

    }

    public CheckoutPage enterAddressLineOne(String addressLineOne) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOneField));
        e.clear();
        e.sendKeys(addressLineOne);

        return this;
    }

    public CheckoutPage enterBillingCityField(String billingCity) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingCityField));
        e.clear();
        e.sendKeys(billingCity);

        return this;
    }

    public CheckoutPage enterBillingPostcodeField(String billingPostcode) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingPostcodeField));
        e.clear();
        e.sendKeys(billingPostcode);
          return this;

            }

    public CheckoutPage selectState(String stateName){

//        Select select = new Select(driver.findElement(stateDropDown));
//        select.selectByVisibleText(stateName);
//        return this;

        wait.until(ExpectedConditions.elementToBeClickable(alternativeStateDropDown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='"+stateName+"']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();
        return this;



    }

    public CheckoutPage enterBillingEmailField(String billingEmail) {

        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingEmailField));
        e.clear();
        e.sendKeys(billingEmail);

        return this;
    }

    public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
        return enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .selectCountry(billingAddress.getCountry())
                .enterAddressLineOne(billingAddress.getAddressLineOne())
                .enterBillingCityField(billingAddress.getCity())
                .selectState(billingAddress.getState())
                .enterBillingPostcodeField(billingAddress.getPostcode())
                .enterBillingEmailField(billingAddress.getEmail());

    }

    public CheckoutPage clickPlaceOrder() throws InterruptedException {
        waitForOverLaysToDisappear(overLay);
        Thread.sleep(2000);
        driver.findElement(placeOrderField).click();
        return this;
    }

    public String getSuccessNotice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice)).getText();

    }

    public CheckoutPage clickHereToLoginLinkMethod() throws InterruptedException {
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(clickHereToLoginLink)).click();
        return this;
    }

    public CheckoutPage enterUserName(String username){
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField)).sendKeys(username);
        return this;
    }

    public CheckoutPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        return this;
    }

    public CheckoutPage clickLoginButton(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).click();
        return this;
    }

    private CheckoutPage waitForLoginButtonToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginButton));
        return this;
    }

    public CheckoutPage login(String username, String password) {
        return enterUserName(username)
                .enterPassword(password)
                .clickLoginButton().waitForLoginButtonToDisappear();
    }

    public CheckoutPage selectDirectBankTransfer() {
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioButton));

        if(!e.isSelected()){
            e.click();
        }

        return this;
    }


//    public String getProductName() {
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
//    }

    public String getProductName() throws Exception {
        int i = 5;
        while(i > 0){
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
            }catch (StaleElementReferenceException e){
                System.out.println("NOT FOUND. TRYING AGAIN" + e);
            }
            Thread.sleep(5000);
            i--;
        }
        throw new Exception("Element not found");
    }




    public CheckoutPage login(User user) {
        return null;
    }
}
