package com.apiFramework.tests.e2e_integration;

import com.apiFramework.base.BaseTest;
import com.apiFramework.endpoints.APIConstants;
import com.apiFramework.pojos.requestPOJO.Booking;
import com.apiFramework.pojos.responsePOJO.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow2 extends BaseTest {

    // Create Booking -> Delete it -> Verify -> again Create new boooking with same data -> verify


    @Test(groups = "qa", priority = 1)
    @Owner("Dheerja")
    @Description("TC#INT2 - Step 1. Create new booking")
    public void testCreateNewBooking(ITestContext iTestContext){

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response= RestAssured.given(requestSpecification).
                when().body(payloadManager.createPayloadBookingAsString()).post();

        BookingResponse bookingResponse= payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStatusCode(response,200);
        assertActions.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Dheeraj");


        Integer bookingid= bookingResponse.getBookingid();

        iTestContext.setAttribute("bookingid",bookingid);
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Dheeraj")
    @Description("TC#INT2 - Step2. Delete newly created booking")
    public void deleteNewBooking(ITestContext iTestContext){

        Integer bookingid=(Integer) iTestContext.getAttribute("bookingid");

        String token=getToken();

        iTestContext.setAttribute("token",token);
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token",token);
        validatableResponse=RestAssured.given().spec(requestSpecification).
                when().delete().then().log().all();

        validatableResponse.statusCode(201);

    }

    @Test(groups = "qa", priority = 3)
    @Owner("Dheeraj")
    @Description("TC#INT2 - Step3. Verify older booking doesn't exists.")
    public void verifyDeletedBooking(ITestContext iTestContext){
        Integer bookingid=(Integer) iTestContext.getAttribute("bookingid");

        String basePathVerifying=APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
        requestSpecification.basePath(basePathVerifying);
        response=RestAssured.given(requestSpecification).when().get();
        validatableResponse=response.then().log().all();

        validatableResponse.statusCode(404);
    }


    @Test(groups = "qa", priority = 5)
    @Owner("Dheeraj")
    @Description("TC#INT2 - Step4. Create new booking with that older data.")
    public void createNewBookingWithSameData(ITestContext iTestContext){

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response=RestAssured.given(requestSpecification).
                when().body(payloadManager.createPayloadBookingAsString()).log().all().
                post();

        BookingResponse bookingResponse=payloadManager.bookingResponseJava(response.asString());

        assertActions.verifyStatusCode(response,200);
        assertActions.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Dheeraj");

        Integer bookingid2=bookingResponse.getBookingid();
        iTestContext.setAttribute("bookingid2",bookingid2);

    }

    @Test(groups = "qa", priority = 5)
    @Owner("Dheeraj")
    @Description("TC#INT2 - Step5. Verify new created booking by Id")
    public void verifyNewbooking(ITestContext iTestContext){

        Integer bookingid2=(Integer) iTestContext.getAttribute("bookingid2");
        String basePathNewBooking=APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid2;

        requestSpecification.basePath(basePathNewBooking);

        response=RestAssured.given(requestSpecification).when().get();

        validatableResponse=response.then().log().all();

        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(),"Dheeraj");
    }



}
