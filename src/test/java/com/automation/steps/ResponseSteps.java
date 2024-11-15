package com.automation.steps;

import com.automation.pojo.CreateBookingReqPojo;
import com.automation.pojo.CreateResponsePojo;
import com.automation.utils.ConfigReader;
import com.automation.utils.RestAssuredUtils;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Assert;

public class ResponseSteps {
    @Then("verify status code {int}")
    public void verify_status_code(int statusCode) {
        Assert.assertEquals(statusCode,RestAssuredUtils.getStatusCode());
    }

    @And("store {string} at {string}")
    public void storeAt(String field, String configValue) {
        ConfigReader.setProperty(configValue,RestAssuredUtils.getJsonFieldValue(field));

    }

    @And("verify response schema with {string}")
    public void verifyResponseSchemaWith(String fileName) {
         Response response = RestAssuredUtils.getResponse();
         response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("json/"+fileName));
    }



    @And("verify response body has filed {string} is {string}")
    public void verifyResponseBodyHasFiledIs(String field, String value) throws Exception {
        Assert.assertEquals(RestAssuredUtils.getJsonFieldValue(field),value);;
    }

    @And("verify response body has same data as request for post")
    public void verifyResponseBodyHasSameDataAsRequestForPost() throws Exception {
        String content = RestAssuredUtils.getDataFromJsonFilePath("create_booking.json");
        ObjectMapper obj = new ObjectMapper();
        CreateBookingReqPojo reqPojo = obj.readValue(content, CreateBookingReqPojo.class);
        Response response=RestAssuredUtils.getResponse();
        CreateResponsePojo responsePojo = response.as(CreateResponsePojo.class);
        Assert.assertEquals(reqPojo,responsePojo.getBooking());
    }

    @And("verify request body same as response for put")
    public void verifyRequestBodySameAsResponseForPut() {
        Response response = RestAssuredUtils.getResponse();
        CreateBookingReqPojo responsePojo = response.as(CreateBookingReqPojo.class);
        CreateBookingReqPojo reqPojo = (CreateBookingReqPojo) ConfigReader.getObject("req.body.update");
        Assert.assertEquals(reqPojo,reqPojo);
    }
}
