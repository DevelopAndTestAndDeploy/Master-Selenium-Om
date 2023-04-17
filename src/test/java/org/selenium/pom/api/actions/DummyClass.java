package org.selenium.pom.api.actions;

import org.selenium.pom.objects.User;
import org.selenium.pom.utils.FakerUtils;

public class DummyClass {

    public static void main(String[] args) {
        String userName = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUsername(userName).
                setPassword("dempwd").
                setEmail(userName + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();

        System.out.println(signUpApi.register(user));

        System.out.println("REGISTER COOKIES " + signUpApi.getCookies());

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(1215, 1);
        System.out.println("CART COOKIES " + cartApi.getCookies());


    }

    /* <input type="hidden" id="woocommerce-register-nonce" name="woocommerce-register-nonce" value="5bf72bdf82"/> */
}
