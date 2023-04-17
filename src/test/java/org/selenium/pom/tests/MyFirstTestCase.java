package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyFirstTestCase extends BaseTest {


    //@Test
      public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        //Builder Pattern.
//        BillingAddress billingAddress = new BillingAddress()
//        .setFirstName("demo")
//                .setLastName("user")
//        .setAddressLineOne("San Fransisco")
//        .setCity("San Fransisco")
//        .setPostcode("94188")
//        .setEmail("askomdch@gmail.com");

//                "San Fransisco", "San Fransisco",
//                "94188", "askomdch@gmail.com");

        String searchFor = "Blue";

        BillingAddress billingAddress = JacksonUtils.deserializeJson("MyBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);


        StorePage storePage = new HomePage(getDriver())
                .load().getMyHeader()
                .navigateToStoreUsingMenu();

        storePage.search(searchFor);
        Assert.assertEquals(storePage.getTitle(), "Search results: “Blue”");

        //Assert.assertTrue(storePage.getTitle().contains("Search results: "));
        //Assert.assertEquals(storePage.getTitle(),"Search results: “Blue”");

        storePage.getProductThumbnail().clickAddToCartButton(product.getName());

        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();

        Assert.assertEquals(cartPage.getProductName(),product.getName());
       // Assert.assertEquals(
        //        cartPage.getProductName(), product.getName());

        CheckoutPage checkoutPage = cartPage
                .Checkout()
        .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
        .clickPlaceOrder();
        Assert.assertEquals(checkoutPage.getSuccessNotice(),"Thank you. Your order has been received.");

        //Assert.assertEquals(checkoutPage.getSuccessNotice(),
        //        "Thank you. Your order has been received."
        //);

    }


    //@Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        String searchFor = "Blue";

        BillingAddress billingAddress = JacksonUtils.deserializeJson("MyBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        User user = new User(ConfigLoader.getInstance().getUsername(),
                ConfigLoader.getInstance().getPassword());


        StorePage storePage = new HomePage(getDriver())
                .load().getMyHeader()
                .navigateToStoreUsingMenu()
                .search("Blue");

        Assert.assertEquals(storePage.getTitle(),"Search results: “Blue”");


        //Assert.assertTrue(storePage.getTitle().contains("Search results: "));


        //Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");

        storePage.getProductThumbnail().clickAddToCartButton(product.getName());
        ;
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

        //Assert.assertEquals(cartPage.getProductName(), product.getName());
        //Search results: “Blue Shoes”

        CheckoutPage checkoutPage = cartPage.Checkout();
        checkoutPage.clickHereToLoginLinkMethod();



        checkoutPage
                .login(user.getUsername(), user.getPassword())
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrder();

        ;

        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");

//        Assert.assertEquals(checkoutPage.getSuccessNotice(),
//                "Thank you. Your order has been received."
//        );





//        driver.findElement(By.cssSelector("#menu-item-1227 > a")).click();
//        ;
//        driver.findElement(By.id("woocommerce-product-search-field-0")).sendKeys("Blue");
//        driver.findElement(By.cssSelector(".woocommerce-product-search button")).click();
//        Assert.assertEquals(
//                driver.findElement(By.xpath("//*[@id=\"main\"]/div/header/h1")).getText(),
//                "Search results: “Blue”"
//        );
//        ;
//        //driver.findElement(By.cssSelector("a[arial-label='Add `Blue Shoes` to your cart']")).click();
//        driver.findElement(By.xpath("//*[@id=\"main\"]/div/ul/li[1]/div[2]/a[2]")).click();
//        ;
//        driver.findElement(By.cssSelector("[title='View cart']")).click();
//        Assert.assertEquals(driver.findElement(By.cssSelector(".product-name > a")).getText(),
//                    "Blue Shoes");
//        driver.findElement(By.cssSelector(".checkout-button")).click();
//        driver.findElement(By.className("showlogin")).click();
//        ;
//        driver.findElement(By.id("username")).sendKeys("demouser3");
//        driver.findElement(By.id("password")).sendKeys("demopwd");
//        driver.findElement(By.name("login")).click();
//
//
//        driver.findElement(By.id("billing_first_name")).sendKeys("demo");
//        driver.findElement(By.id("billing_last_name")).sendKeys("user");
//        driver.findElement(By.id("billing_address_1")).sendKeys("San Fransisco");
//        driver.findElement(By.id("billing_city")).sendKeys("San Fransisco");
//        driver.findElement(By.id("billing_postcode")).clear();
//        driver.findElement(By.id("billing_postcode")).sendKeys("94188");
//        driver.findElement(By.id("billing_email")).clear();
//        driver.findElement(By.id("billing_email")).sendKeys("askomdch@gmail.com");
//        ;
//        driver.findElement(By.id("place_order")).click();
//        //driver.findElement(By.xpath("//*[@id=\"place_order\"]")).click();
//        ;
//        Assert.assertEquals(driver.findElement(
//                By.cssSelector(".woocommerce-notice")).getText(),
//        "Thank you. Your order has been received."
//        );


    }
}
