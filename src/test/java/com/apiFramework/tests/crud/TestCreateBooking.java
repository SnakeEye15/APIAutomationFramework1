package com.apiFramework.tests.crud;


import com.apiFramework.base.BaseTest;
import com.apiFramework.endpoints.APIConstants;
import com.apiFramework.pojos.responsePOJO.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestCreateBooking extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Dheeraj")
    @Description("TC#1 - Verify that the Booking can be created")
    public void testCreateBookingPOST_POSITIVE(){
        //Setup will First and making the request part-1
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response= RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString()).log().all()
                .post();

        //Extraction the response data part-2
        BookingResponse bookingResponse= payloadManager.bookingResponseJava(response.asString());

        //validation and verification vai the assertJ, TestNG part-3
        assertActions.verifyStatusCode(response,200);
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Dheeraj");


    }

    @Test(groups = "reg", priority = 1)
    @Owner("Dheeraj")
    @Description("TC#2 - Verify that the Booking can't  be created, When payload is Null")
    public void testCreateBookingPOST_NEGATIVE(){

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response=RestAssured.given(requestSpecification).when()
                .body("{}").log().all().post();

        validatableResponse=response.then().log().all();
        validatableResponse.statusCode(500);

    }

    @Test(groups = "reg", priority = 1)
    @Owner("Dheeraj")
    @Description("TC#3 - Verify that the Booking can't  be created, When payload is Chiness")
    public void testCreateBookingPOST_POSITIVE_CHINESS(){

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response=RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsStringWrongBody())
                .log().all().post();

        validatableResponse=response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse= payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());


    }

    @Test(groups = "reg", priority = 1)
    @Owner("Dheeraj")
    @Description("TC#4 - Verify that the Booking can't  be created, When payload is RANDOM")
    public void testCreateBookingPOST_POSITIVE_FAKER_RANDOM_DATA(){
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response=RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingFakerJS())
                .log().all().post();

        validatableResponse=response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse= payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());

    }





}
