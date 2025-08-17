package com.apiFramework.tests.crud;

import com.apiFramework.base.BaseTest;
import com.apiFramework.endpoints.APIConstants;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestHealthCheck extends BaseTest {


    @Test
    @Description("TC#3  - Verify Health")
    public void testGetHealthCheck(){

        requestSpecification.basePath(APIConstants.PING_URL);
        response= RestAssured.given(requestSpecification).when().get();

        validatableResponse=response.then().log().all();
        validatableResponse.statusCode(201);

        assertActions.verifyTrue(response.asString().contains("Created"));
    }

}