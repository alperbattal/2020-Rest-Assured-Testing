package com.automation.tests.Day2;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;



public class ExchangeRatesAPITests {
    private String baseURI = "http://api.openrates.io/";

    @Test
    public void test(){
        Response response = given().baseUri(baseURI+"latest").get();
        assertEquals(200,response.getStatusCode());

        System.out.println(response.prettyPrint());

    }

    @Test
    public void test2(){
        Response response = given().
                get(baseURI+"latest");
        //verify that content type is json
        assertEquals("application/json",response.contentType());

    }


    @Test
    public void test3(){
        //get currency exchange rate fol dollar. By default it is euro.
        Response response = given()
                .baseUri(baseURI).basePath("latest")
                .queryParam("base","USD").get();
        assertEquals(200,response.getStatusCode());
        System.out.println(response.prettyPrint());

    }


    //verify that response body, for latest, contains today's date
    @Test
    public void test4(){
        Response response = given()
                .baseUri(baseURI).basePath("latest")
                .queryParam("base","GBP")
                .get();
        assertEquals(200,response.getStatusCode());
        String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertTrue(response.getBody().asString().contains(todaysDate));
        System.out.println("Today's date : "+todaysDate);

    }


    //year 2000
    //https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-09-01&base=USD
    @Test
    public void test5(){
        Response response = given()
                .baseUri(baseURI).basePath("history")
                .queryParam("start_at","2019-01-01")
                .queryParam("end_at","2019-12-31")
                .queryParam("base","USD")
                .queryParam("symbols","EUR","JPY","TRY","GBP")
                .get();
        assertEquals(200,response.getStatusCode());
        System.out.println(response.prettyPrint());
    }
    /**
     * Given request parameter "base" is "USD"
     * When user sends request to "api.openrates.io"
     * Then response code should be 200
     * And response body must contain "base" : "USD"
     */


}
