package com.apiFramework.asserts;

import com.google.common.base.Optional;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.assertTrue;
import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Locale;

public class AssertActions {
    public void verifyResponseBody(String actual, String expected, String description){
        assertEquals(actual,expected,description);
    }

    public void verifyResponseBody(int actual, int expected, String description){
        assertEquals(actual,expected,description);
    }

    public void verifyStatusCode(Response response, int expected){
        assertEquals(response.getStatusCode(), expected);
    }

    public void verifyStringKey(String keyExpect, String keyActual){
        //AssertJ
        assertThat(keyExpect).isNotNull();
        assertThat(keyExpect).isNotBlank();
        assertThat(keyExpect).isEqualTo(keyActual);
    }

    public void verifyStringKeyNotNull(Integer keyExpect){
        assertThat(keyExpect).isNotNull();
    }

    public void verifyStringKeyNotNull(String keyExpect){
        assertThat(keyExpect).isNotNull();
    }

    public void verifyTrue(Boolean keyExpect){
        //AssertJ
        assertTrue(keyExpect);
    }


    // =========================
    // NEW METHODS (ADDED)
    // =========================

    // Generic non-null check
    public void verifyNotNull(Object actual, String message) {
        if (message == null) message = "Expected value to be non-null";
        assertThat(actual).as(message).isNotNull();
    }

    // Strict boolean equality (not just truthy)
    public void verifyBoolean(Boolean actual, boolean expected) {
        assertThat(actual).as("Actual boolean is null").isNotNull();
        assertThat(actual.booleanValue()).as("Boolean value mismatch").isEqualTo(expected);
    }

    // Strict integer equality
    public void verifyInt(Integer actual, int expected) {
        assertThat(actual).as("Actual integer is null").isNotNull();
        assertThat(actual.intValue()).as("Integer value mismatch").isEqualTo(expected);
    }

    // Strict date format validation (non-lenient)
    public void verifyDateFormat(String dateString, String pattern) {
        assertThat(dateString).as("Date string is null").isNotNull();
        assertThat(pattern).as("Pattern is null").isNotNull();

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ROOT);
        sdf.setLenient(false);
        try {
            Date d = sdf.parse(dateString);
            assertThat(d).as("Parsed date is null for: " + dateString).isNotNull();
            // Re-format and compare to ensure exact match
            String reformatted = sdf.format(d);
            assertThat(reformatted).as("Date does not strictly match pattern " + pattern).isEqualTo(dateString);
        } catch (ParseException e) {
            fail("Date '" + dateString + "' does not match pattern '" + pattern + "': " + e.getMessage());
        }
    }

    // Content-Type contains application/json
    public void verifyContentTypeJSON(Response response) {
        assertThat(response).as("Response is null").isNotNull();
        String contentType = response.getHeader("Content-Type");
        assertThat(contentType).as("Content-Type header is missing").isNotNull();
        assertThat(contentType.toLowerCase(Locale.ROOT))
                .as("Expected Content-Type to contain application/json but was: " + contentType)
                .contains("application/json");
    }

    // Header presence (case-insensitive)
    public void verifyHeaderPresent(Response response, String headerName) {
        assertThat(response).as("Response is null").isNotNull();
        assertThat(headerName).as("Header name is null").isNotNull();

        String direct = response.getHeader(headerName);
        if (direct != null) {
            // present
            return;
        }
        boolean found = response.getHeaders() != null && response.getHeaders().asList().stream()
                .anyMatch(h -> h.getName().equalsIgnoreCase(headerName));
        assertThat(found).as("Expected header '" + headerName + "' to be present").isTrue();
    }

    // Header value contains expected fragment (case-insensitive header lookup)
    public void verifyHeaderValueContains(Response response, String headerName, String expectedFragment) {
        assertThat(response).as("Response is null").isNotNull();
        assertThat(headerName).as("Header name is null").isNotNull();
        assertThat(expectedFragment).as("Expected fragment is null").isNotNull();

        String value = response.getHeader(headerName);
        if (value == null && response.getHeaders() != null) {
            value = response.getHeaders().asList().stream()
                    .filter(h -> h.getName().equalsIgnoreCase(headerName))
                    .map(h -> h.getValue())
                    .findFirst()
                    .orElse(null);
        }
        assertThat(value).as("Header '" + headerName + "' not present").isNotNull();
        assertThat(value).as("Header '" + headerName + "' expected to contain '" + expectedFragment + "'")
                .contains(expectedFragment);
    }

    // Body contains fragment
    public void verifyBodyContains(Response response, String expectedFragment) {
        assertThat(response).as("Response is null").isNotNull();
        String body = response.getBody() != null ? response.getBody().asString() : null;
        assertThat(body).as("Response body is null").isNotNull();
        assertThat(body).as("Expected response body to contain '" + expectedFragment + "'").contains(expectedFragment);
    }

    // Body does not contain fragment
    public void verifyBodyNotContains(Response response, String fragment) {
        assertThat(response).as("Response is null").isNotNull();
        String body = response.getBody() != null ? response.getBody().asString() : null;
        assertThat(body).as("Response body is null").isNotNull();
        assertThat(body).as("Expected response body NOT to contain '" + fragment + "'").doesNotContain(fragment);
    }

    // JSON key presence using JsonPath (supports nested paths, e.g., "booking.firstname")
    public void verifyJsonHasKey(Response response, String key) {
        assertThat(response).as("Response is null").isNotNull();
        assertThat(key).as("Key is null").isNotNull();

        String body = response.getBody() != null ? response.getBody().asString() : null;
        assertThat(body).as("Response body is null").isNotNull();

        try {
            JsonPath jp = new JsonPath(body);
            Object val = jp.get(key);
            assertThat(val).as("Expected JSON to have key '" + key + "' but it was not found or null").isNotNull();
        } catch (Exception e) {
            fail("Failed to parse JSON or locate key '" + key + "': " + e.getMessage());
        }
    }

    // Generic equality for any object types
    public <T> void verifyResponseBodyEquals(T actual, T expected, String message) {
        if (message == null) message = "Body equality assertion failed";
        assertThat(actual).as(message).isEqualTo(expected);
    }


}
