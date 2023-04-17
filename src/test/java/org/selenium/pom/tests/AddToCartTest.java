package org.selenium.pom.tests;


import io.qameta.allure.*;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataproviders.MyDataProvider;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("askom2.02")
@Feature("ask")
public class AddToCartTest extends BaseTest {

    @Story("Create a story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("12345")
    @Description("This is the description")
    @Test(description = "Should be able to add products from cart")
    public void addToCartFromStorePage() throws IOException {

        Product product = new Product(1215);

        CartPage cartPage = new StorePage(getDriver()).
                load().getProductThumbnail()
                .clickAddToCartButton(product.getName()).clickViewCart();

        Assert.assertEquals(cartPage.getProductName(),product.getName());

    }

    @Step
    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class)
    public void addToCartFeaturedProducts(Product product) {
        CartPage cartPage = new HomePage(getDriver()).load().getProductThumbnail().
                clickAddToCartButton(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());


    }







}
