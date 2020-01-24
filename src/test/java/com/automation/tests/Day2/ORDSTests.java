package com.automation.tests.Day2;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSTests {

    private String baseURI = "http://54.175.111.221:1000/ords/hr/";
    private String employees ="employees";
    private Response response;

    @Test
    public void test(){
        response = given().get(baseURI+employees);
        //System.out.println(response.getBody().asString());

        assertEquals(200,response.getStatusCode());
        System.out.println("Status Code : "+response.getStatusCode());

        System.out.println(response.prettyPrint());
        System.out.println(response.getHeader("Content-Type"));

    }

    @Test
    public void test1(){
        response = given().header("Accept","application.json").get(baseURI+employees+"/100");
        System.out.println(response.getHeader("Content-Type"));
        System.out.println(response.prettyPrint());
        assertEquals(200,response.getStatusCode());
    }

    /*
    @GetMapping(/employees/{id})
    public Employee getEmployeeByID(@PathParam String id){
        return repo.getEmployee(id);
    }

     */

    @Test
    public void test2(){
        response =given().get(baseURI+"regions");
        assertEquals(200,response.getStatusCode());
        //to get a specific header
        Header header = response.getHeaders().get("Content-Type");
        for (Header h : response.getHeaders()){
            int i = 0;
            System.out.println(i+" "+h);
            i++;
        }
        //System.out.println("Headers : "+response.getHeaders());
       // System.out.println("Body : "+response.getBody());
        System.out.println(response.prettyPrint());
    }



}
