package com.apiFramework.asserts;

import com.google.common.base.Optional;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.assertTrue;

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



}
