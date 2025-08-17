package com.apiFramework.tests.crud;

import com.apiFramework.base.BaseTest;
import com.apiFramework.endpoints.APIConstants;
import com.apiFramework.modules.PayloadManager;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestCreateToken extends BaseTest {

    @Test(groups="reg", priority =1)
    @TmsLink("https://bugz.atlassian.net/browse/BUG-19")
    @Owner("Dheeraj")
    @Description("TC#1 - Create Token and verify")
    public void testTokenPost(){
        requestSpecification.basePath(APIConstants.AUTH_URL);
        response= RestAssured.given(requestSpecification)
                .when()
                .body(payloadManager.setAuthPayload()).post();

        //Extraction(Json String -> Java object)
        String token= payloadManager.getTokenFromJson(response.asString());
        System.out.println(token);

        //validation of request
        assertActions.verifyStringKeyNotNull(token);
    }


    @Test(groups="reg", priority =1)
    @TmsLink("https://bugz.atlassian.net/browse/BUG-19")
    @Owner("Dheeraj")
    @Description("TC#2 - Create INVALID Token and verify")
    public void testTokenPost_Negative(){
        requestSpecification.basePath(APIConstants.AUTH_URL);
        response= RestAssured.given(requestSpecification)
                .when()
                .body("{}").post();

        //Extraction(Json String -> Java object)
        String invalidReason= payloadManager.getTokenFromJson(response.asString());
        System.out.println(invalidReason);

        //validation of request
        assertActions.verifyStringKey(invalidReason,"Bad credentials");
    }
}
