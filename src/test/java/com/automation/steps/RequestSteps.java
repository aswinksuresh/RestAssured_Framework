package com.automation.steps;

import com.automation.pojo.CreateBookingReqPojo;
import com.automation.pojo.CreateTokenPojo;
import com.automation.utils.ConfigReader;
import com.automation.utils.RestAssuredUtils;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.lang.reflect.Field;

public class RequestSteps {
    @Given("user wants to call {string} endpoint")
    public void user_wants_to_call_endpoint(String endPoint) {
        if (endPoint.contains("@id")){
            endPoint=endPoint.replace("@id",ConfigReader.getConfig("booking.id"));
        }
        System.out.println("booking id is :"+endPoint);
        RestAssuredUtils.setEndPoint(endPoint);
    }

    @Given("set header {string} to {string}")
    public void set_header_to(String key, String value) {
        if(value.contains("@token")){
            value =value.replace("@token",ConfigReader.getConfig("token.value"));
        }

          RestAssuredUtils.setHeader(key,value);
    }

    @Given("set body from {string}")
    public void set_body_from(String fileName) {
       RestAssuredUtils.setBody(fileName);
    }

    @When("user performs post call")
    public void user_performs_post_call() {
         RestAssuredUtils.post();
    }






    @And("set body with fields {string} and {string}")
    public void setBodyWithFieldsAnd(String username, String password) {
        CreateTokenPojo tokenPojo = new CreateTokenPojo();
        tokenPojo.setUsername(username);
        tokenPojo.setPassword(password);
        RestAssuredUtils.setBody(tokenPojo);

    }

    @And("set body from {string} with {string} value {string}")
    public void setBodyFromWithValue(String filename, String filed, String fieldValue) throws Exception {
        String content = RestAssuredUtils.getDataFromJsonFilePath(filename);
        ObjectMapper obj = new ObjectMapper();
        CreateBookingReqPojo reqPojo = obj.readValue(content, CreateBookingReqPojo.class);
        Field field = CreateBookingReqPojo.class.getDeclaredField(filed);
        field.setAccessible(true);
        if(Constants.createBookingIntFields.contains(filed)){
            field.set(reqPojo,Integer.valueOf(fieldValue));
        }
       else {
           field.set(reqPojo,fieldValue);
        }
       RestAssuredUtils.setBody(reqPojo);
       ConfigReader.setObject("req.body.put",reqPojo);

    }

    @When("user performs put call")
    public void userPerformsPutCall() {
        RestAssuredUtils.put();
    }

    @When("user performs delete call")
    public void userPerformsDeleteCall() {
        RestAssuredUtils.getStatusCode();
    }
}
