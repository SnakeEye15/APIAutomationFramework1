package com.apiFramework.base;

import com.apiFramework.asserts.AssertActions;
import com.apiFramework.endpoints.APIConstants;
import com.apiFramework.modules.PayloadManager;
import com.beust.ah.A;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    //Common To all Testcase
    //Base URL, Content type

    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;


    @BeforeTest
    public void setup(){
        //we need to setup the baseURL and Header.
        System.out.println("Starting the Test");
        payloadManager=new PayloadManager();
        assertActions=new AssertActions();

     //   requestSpecification= RestAssured.given();
     //   requestSpecification.baseUri(APIConstants.BASE_URL);
     //   requestSpecification.contentType(ContentType.JSON).log().all();

        //we can write above request in following way also, both are same. just syntax is different
        requestSpecification =  new RequestSpecBuilder()
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type", "application/json")
                .build().log().all();

    }

    //@BeforeMethod
    //public void waitCustom() throws InterruptedException {
    //    Thread.sleep(3000);
    //}


    @AfterTest
    public void tearDown(){
        System.out.println("Finish the Test!");
    }
}
