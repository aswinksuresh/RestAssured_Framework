package com.automation.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RestAssuredUtils {
    private static final Log log = LogFactory.getLog(RestAssuredUtils.class);
    static String endPoint;
   static RequestSpecification requestSpecification = RestAssured.given();
   static Response response;
   public static void setEndPoint(String  endPoint){
       RestAssuredUtils.endPoint = endPoint;
   }
   public static void setHeader(String key, String value){
       requestSpecification.headers(key,value);
   }
   public static void setBody(String fileName){
      try {
          requestSpecification.body(getDataFromJsonFilePath(fileName));
      } catch (Exception e) {
          throw new RuntimeException(e);
      }

   }
    public static void setBody(Object pojo){
        requestSpecification.body(pojo);
    }
    public static int getStatusCode(){
       return response.getStatusCode();
    }

   public static void post(){
       requestSpecification.log().all();
       response=requestSpecification.post(endPoint);
       response.then().log().all();
   }
    public static void put(){
        System.out.println("123");
        requestSpecification.log().all();
        response=requestSpecification.put(endPoint);
        response.then().log().all();
    }
    public static void get(){
        requestSpecification.log().all();
        response=requestSpecification.get(endPoint);
        response.then().log().all();
    }
    public static void delete(){
        requestSpecification.log().all();
        response=requestSpecification.delete(endPoint);
        response.then().log().all();
    }
    public static String  getJsonFieldValue(String jsonPath){
       return response.jsonPath().getString(jsonPath);
    }
    public static String getDataFromJsonFilePath(String fileName) throws Exception {
       String jsonFolder = "src/test/resources/json/";
        Scanner sc = new Scanner(new FileInputStream(jsonFolder+fileName));
        String body =sc.useDelimiter("\\Z").next();
        return body;
    }
    public static Response getResponse(){
       return response;
    }
}
