package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.component.ProductThumbnail;

public class StorePage extends BasePage {

    private final By searchField = By.id("woocommerce-product-search-field-0");
    private final By searchButton = By.cssSelector(".woocommerce-product-search button");

    private final By title = By.xpath("//*[@id=\"main\"]/div/header/h1");

    private ProductThumbnail productThumbnail;

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public StorePage(WebDriver driver) {

        super(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    private StorePage enterTextInSearchField(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchField)).sendKeys(text);
        return this;
    }

    public StorePage load() {
        load("/store");
        return this;
    }



    public StorePage search(String text){
       enterTextInSearchField(text).clickSearchButton();
        return this;
    }


    private StorePage clickSearchButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton)).click();
        return this;
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();


    }


}
