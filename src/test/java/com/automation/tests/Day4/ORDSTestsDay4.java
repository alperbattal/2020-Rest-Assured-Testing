package com.automation.tests.Day4;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSTestsDay4 {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }
    /**
     * Warmup!
     *          Given accept type is JSON
     *         When users sends a GET request to "/employees"
     *         Then status code is 200
     *         And Content type is application/json
     *         And response time is less than 3 seconds
     */

    @Test
    public void test1(){
        given().accept(ContentType.JSON).when()
                .get("/employees").then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .time(lessThan(3L), TimeUnit.SECONDS )
                .log().all(true);
    }

    /*
    Given accept type is JSON
    And parameters: q = country_id = US
    When users sends a GET request to "/countries"
    Then status code is 200
    And Content type is application/json
    And country_name from payload is "United States of America"
    {"country_id":"US"}
 */

    @Test
    public void test2(){
        given().accept(ContentType.JSON)
                .when().pathParam("id","US")
                .when().get("/countries/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .assertThat().body("country_name",is("United States of America"))
                .time(lessThan(1L),TimeUnit.SECONDS)
                .log().all(true);


    }


    @Test
    public void test3(){
        Response response = given().
                accept(ContentType.JSON).
               // queryParam("q""\"country_id\":\"US\"").
                get("/countries");

        JsonPath jsonPath = response.jsonPath();
        List<?> links = jsonPath.getList("items.links.href");
        for(Object each : links) {
            System.out.println(each);
        }

    }


    @Test
    @DisplayName("Verify that payload contains 25 countries")
    public void test4(){

        List<?> countries = given().
                accept(ContentType.JSON).
                        get("/countries")
                .thenReturn()
                .jsonPath().getList("items");
        assertEquals(25, countries.size());
    }

    @Test
    public void test5(){
        List<String> expected = List.of("Argentina","Brazil","Canada","Mexico","United States of America");

        Response response = given().accept(ContentType.JSON)
                .queryParam("q","{\"region_id\":\"2\"}").
                when().get("/countries").prettyPeek();

        List<String> actual =response.jsonPath().getList("items.country_name");

        //System.out.println(actual);
        assertEquals(actual,expected);
    }

    @Test
    public void test6(){
        List<String> expected = List.of("Argentina","Brazil","Canada","Mexico","United States of America");
        given().accept(ContentType.JSON)
                .queryParam("q","{\"region_id\":\"2\"}")
                .when().get("/countries")
                .then().assertThat().body("items.country_name",contains("Argentina","Brazil","Canada","Mexico","United States of America"));

    }

    /**
     * given path parameter is "/employees"
     * when user makes get request
     * then assert that status code 200
     * then user verifies that every employee has positive salary
     *
     */

    @Test
    @DisplayName("verifies that every employee has positive salary")
    public void test7(){
        given().accept(ContentType.JSON)
        .when().get("/employees")
        .then().assertThat().statusCode(200)
        .and().assertThat().body("items.salary", everyItem(greaterThan(0)));
    }


    /**
     * given path parameter is "/employees/{id}"
     * and path parameter is 101
     * when user makes get request
     * then assert that status code is 200
     * and verifies that phone number is 515-123-4568
     *
     */
    @Test
    @DisplayName("verify the phone numbers")
    public void test8(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/employees/{id}",101);
        assertEquals(200, response.getStatusCode());
        String expected = "515-123-4568";

        String actual = response.jsonPath().getString("phone_number");
        actual=actual.replace(".","-");
        assertEquals(expected,actual);


    }

    /**
     * given path parameter is "/employees"
     * when user makes get request
     * then assert that status code is 200
     * and verify that body returns following salary information after sorting from higher to lower
     *  24000, 17000, 17000, 12008, 11000,
     *  9000, 9000, 8200, 8200, 8000,
     *  7900, 7800, 7700, 6900, 6500,
     *  6000, 5800, 4800, 4800, 4200,
     *  3100, 2900, 2800, 2600, 2500
     *
     */
    @Test
    @DisplayName("verify that body returns following salary information after sorting from higher to lower")
    public void test9(){
        List<Integer> expectedSalaries = List.of(24000, 17000, 17000, 12008, 11000,
                9000, 9000, 8200, 8200, 8000,
                7900, 7800, 7700, 6900, 6500,
                6000, 5800, 4800, 4800, 4200,
                3100, 2900, 2800, 2600, 2500);

        List<?> list = given().accept(ContentType.JSON)
                .when().get("/employees")
                .jsonPath().getList("items.salary");
        System.out.println(list);


        //assertEquals();
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
    public void test10(){
        given().accept(ContentType.JSON)
                .queryParam("query","Las")
                .when().get("/search")
                .then().assertThat().body("location_type",everyItem(is("City")))
                .log().all(true);
    }





}










