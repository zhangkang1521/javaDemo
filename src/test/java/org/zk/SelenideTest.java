package org.zk;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    @Test
    public void test1() throws Exception {
        Configuration.browser = "Chrome";
        Configuration.baseUrl="http://localhost:8080";
        open("/");
        $("#books").click();
        Selenide.$(By.xpath("html/body/div")).exists();
    }
}
