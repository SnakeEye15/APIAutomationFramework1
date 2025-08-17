package com.apiFramework.modules;

import com.apiFramework.pojos.requestPOJO.Auth;
import com.apiFramework.pojos.requestPOJO.Booking;
import com.apiFramework.pojos.requestPOJO.Bookingdates;
import com.apiFramework.pojos.responsePOJO.BookingResponse;
import com.apiFramework.pojos.responsePOJO.InvalidTokenResponse;
import com.apiFramework.pojos.responsePOJO.TokenResponse;
import com.github.javafaker.Faker;
import com.google.gson.Gson;

public class PayloadManager {

    Gson gson;
    Faker faker;

    //Convert the Java Object into the JSon String to use as Payload.
    //Serialization

    public String createPayloadBookingAsString(){
        Booking booking = new Booking();
        booking.setFirstname("Dheeraj");
        booking.setLastname("Saini");
        booking.setTotalprice(113);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2025-08-08");
        bookingdates.setCheckout("2025-08-15");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        //Java Object -> Json

        gson=new Gson();
        String jsonStringBooking=gson.toJson(booking);
        return jsonStringBooking;
    }


    public String createPayloadBookingAsStringWrongBody(){
        Booking booking = new Booking();
        booking.setFirstname("会意; 會意");
        booking.setLastname("会意; 會意");
        booking.setTotalprice(112);
        booking.setDepositpaid(false);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("5025-02-01");
        bookingdates.setCheckout("5025-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("会意; 會意");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

    }

    //Convert the JSON String to java object so that we can verify the response
    //DeSerialization
    public BookingResponse bookingResponseJava(String responseString){
        gson= new Gson();
        BookingResponse bookingResponse=gson.fromJson(responseString,BookingResponse.class);
        return bookingResponse;
    }


    public String createPayloadBookingFakerJS(){
        faker= new Faker();
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(1,100));
        booking.setDepositpaid(faker.random().nextBoolean());

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2025-08-08");
        bookingdates.setCheckout("2025-08-15");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

    }


    public String setAuthPayload(){
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");

        gson= new Gson();
        String jsonPayloadString= gson.toJson(auth);
        System.out.println("Payload set to the ->"+jsonPayloadString);
        return jsonPayloadString;
    }

    //DeSerialization
    public String getTokenFromJson(String tokenResponse){

        gson=new Gson();
        TokenResponse tokenResponse1=gson.fromJson(tokenResponse,TokenResponse.class);
        return tokenResponse1.getToken();
    }

    //DeSerialization
    public String getInvalidResponse(String invalidTokenResponse){

        gson=new Gson();
        InvalidTokenResponse tokenResponse1=gson.fromJson(invalidTokenResponse, InvalidTokenResponse.class);
        return tokenResponse1.getReason();
    }

}
