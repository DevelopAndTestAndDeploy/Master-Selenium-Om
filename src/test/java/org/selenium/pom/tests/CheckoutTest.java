package org.selenium.pom.tests;

import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest {

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("MyBillingAddress.json", BillingAddress.class);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load().
                 setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrder();
        Assert.assertEquals(checkoutPage.getSuccessNotice(),"Thank you. Your order has been received.");


    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("MyBillingAddress.json", BillingAddress.class);
        String userName = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUsername(userName).
                setPassword("dempwd").
                setEmail(userName + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        Thread.sleep(5000);
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load().
        setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrder();
        Assert.assertEquals(checkoutPage.getSuccessNotice(),"Thank you. Your order has been received.");
    }
}
