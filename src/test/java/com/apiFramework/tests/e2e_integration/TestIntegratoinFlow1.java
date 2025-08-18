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

public class TestIntegratoinFlow1 extends BaseTest {

    // TestE2EFlow_01

    //  Test E2E Scenario 1

    //  1. Create a Booking -> bookingID

    // 2. Create Token -> token

    // 3. Verify that the Create Booking is working - GET Request to bookingID

    // 4. Update the booking ( bookingID, Token) - Need to get the token, bookingID from above request

    // 5. Delete the Booking - Need to get the token, bookingID from above request


    @Test(groups = "qa", priority = 1)
    @Owner("Dheerja")
    @Description("TC#INT1 - Step 1.Verify that the booking can be created.")
    public void testCreateBooking(ITestContext iTestContext){
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

        Integer bookingid= bookingResponse.getBookingid();
        iTestContext.setAttribute("bookingid",bookingid);
    }
    @Test(groups = "qa", priority = 2)
    @Owner("Dheerja")
    @Description("TC#INT2 - Step 2.Verify the booking by booking ID")
    public void verifyBookingID(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));

        Integer bookingid=(Integer)iTestContext.getAttribute("bookingid");

        String basePathGET=APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
        System.out.println(basePathGET);

        requestSpecification.basePath(basePathGET);
        response=RestAssured.given(requestSpecification)
                .when().get();

        validatableResponse=response.then().log().all();
        //validate Assertion
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());

    }

    @Test(groups = "qa", priority = 3)
    @Owner("Dheerja")
    @Description("TC#INT3 - Step 3.Verify updated booking by  ID")
    public void updateBookingID(ITestContext iTestContext){
        Integer bookingid= (Integer) iTestContext.getAttribute("bookingid");
        String token=getToken();

        iTestContext.setAttribute("token",token);

        String basePathPUTPATCH =APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);
        response=RestAssured.given(requestSpecification).cookie("token",token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();

        validatableResponse=response.then().log().all();
        //validate assertion
        validatableResponse.statusCode(200);

        Booking booking= payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(),"Ajay");

    }

    @Test(groups = "qa", priority = 4)
    @Owner("Dheeraj")
    @Description("TC#INT3 - Step 3. Delete the booking by  ID")
    public void testDeleteBookingByID(ITestContext iTestContext){

        Integer bookingId=(Integer) iTestContext.getAttribute("bookingid");
        String token=(String)iTestContext.getAttribute("token");

        String basePathDELETE=APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingId;

        requestSpecification.basePath(basePathDELETE).cookie("token",token);
        validatableResponse=RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();

        validatableResponse.statusCode(201);
    }



}
