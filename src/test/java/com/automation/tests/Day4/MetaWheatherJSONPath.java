package com.automation.tests.Day4;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;


public class MetaWheatherJSONPath {


    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("meta.uri");
    }


    /**
     * TASK
     * Given accept type is JSON
     * When users sends a GET request to "/search"
     * And query parameter is "Las"
     * Then verify that every item in payload has location_type City
     */
    @Test
    @DisplayName("")
    public void test1(){
        given().accept(ContentType.JSON)
                .queryParam("query","Las")
                .when().get("/search")
                .then().assertThat().body("location_type",everyItem(is("City")))
                .log().all(true);
    }


    @Test
    @DisplayName("")
    public void test2(){
        List<String> expected = Arrays.asList("BBC","Forecast.io","HAMweather","Met Office",
                "OpenWeatherMap","Weather Underground",
                "World Weather Online");

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("woeid",44418)
                .when().get("/location/{woeid}");


        List<String> actual = response.jsonPath().getList("sources.title");
        assertEquals(actual,expected);

    }

}
