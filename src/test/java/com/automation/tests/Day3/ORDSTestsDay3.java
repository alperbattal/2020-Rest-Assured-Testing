package com.automation.tests.Day3;

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;
import static  org.hamcrest.Matcher.*;

public class ORDSTestsDay3 {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    // accept("application/json") shortcut for
    @Test
    public void test1(){
        given().
                accept("application/json").
                get("/employees").
        then().
                assertThat().statusCode(200).
        and().
                assertThat().contentType("application/json").
                log().all(true);
    }

    @Test
    public void test2(){
        given().accept("application/json").
               pathParam("id",100).
        when().get("/employees/{id}").
        then().assertThat().statusCode(200).
        and().assertThat().body("employee_id", is(100),
                        "department_id", is(90),
                                "last_name",is("King")).
                log().all(true);
    }

    /**
     * given path parameter is "/regions/{id"
     * when user makes get request
     * and region id is 1
     * then assert that status code is 200
     * and assert that region name is Europe
     */
    @Test
    public void test3(){
        given().accept("application/json").
                pathParam("id", 1).
                when().get("/regions/{id}").
                then().assertThat().statusCode(200).
                and().assertThat().body("region_name",is("Europe")).
                log().all(true);
    }




    @Test
    public void test4(){
        given().accept("application/json").get("/regions").
        then().assertThat().statusCode(200).
                log().all(true);
    }

    /**
     * given path parameter is "/regions/{id"
     * when user makes get request
     * and region id is 2
     * then assert that status code is 200
     * and assert that region name is America
     */
    @Test
    public void test5(){
        given().accept("application/json").
                pathParam("id", 2).
                get("/regions/{id}").
                then().assertThat().statusCode(200).
                and().assertThat().body("region_name", is("Americas")).
                log().all(true);
    }






    /**
     * given path parameter is "/countries/{id}"
     * when user makes get request
     * and country id is AR
     * then assert that status code is 200
     * and assert that region name is Argentina
     */
    @Test
    public void test6(){
        given().accept("application/json").
                pathParam("id","AR").
        when().get("/countries/{id}").
        then().assertThat().statusCode(200).
        and().body("country_name", is("Argentina")).
                log().all(true);
    }

    @Test
    public void test7(){
        given().accept("application/json").
        when().get("/countries").then().assertThat().statusCode(200).
        and().log().all(true);
    }


    /**
     * given path parameter is "/countries/{id}"
     * when user makes get request
     * and country id is CH
     * then assert that status code is 200
     * and assert that country name is Switzerland
     * and assert that the region id is 1
     */
    @Test
    public void test8(){
        given().accept("application/json").
        when().pathParam("id","CH").
        when().get("/countries/{id}").
        then().assertThat().statusCode(200).
        and().assertThat().body("country_name",is("Switzerland")).
        and().assertThat().body("region_id",is(1)).
                time(lessThan(1L), TimeUnit.SECONDS).
                //log().all(true).  //------ either or ------
                extract().response().prettyPrint(); // prints only body.
    }

    /**
     *  time(lessThan(1L), TimeUnit.SECONDS)
     */

    @Test
    public void test9(){
        JsonPath jsonPath= given().accept("application/json").when().get("/employees").
                thenReturn().jsonPath();
        String namoOfEmployee = jsonPath.getString("items[0].first_name");
        System.out.println(namoOfEmployee);
    }

    @Test
    public void test10(){
        JsonPath json = given().accept("application/json").when().get("/employees").
                thenReturn().jsonPath();
        String namoOfEmployee = json.getString("items[0].last_name");
        System.out.println(namoOfEmployee);

        Map<String, ?> firstEmployee = json.get("items[0]"); // we put ? because it can be also not String
        System.out.println(firstEmployee);

        //since firstEmployee it's a map (key-value pair, we can iterate through it by using Entry. entry represent one key=value pair)
        // put ? as a value (Map<String, ?>), because there are values of different data type: string, integer, etc..
        //if you put String as value, you might get some casting exception that cannot convert from integer(or something else) to string
        for (Map.Entry<String, ?> entry : firstEmployee.entrySet()) {
            System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
        }

//       get and print all last names
//        items it's an object. whenever you need to read some property from the object, you put object.property
//        but, if response has multiple objects, we can get property from every object
        List<String> lastNames = json.get("items.last_name");
        for (String str : lastNames) {
            System.out.println("last name: " + str);
        }


    }

    @Test
    public void test11(){
        List<Integer> salaries = given().accept("application/json")
                .when().get("/employees")
                .thenReturn().jsonPath().get("items.salary");
        Collections.sort(salaries);
        Collections.reverse(salaries);
        System.out.println(salaries);
    }

    @Test // replace each phone number . with -
    public void test12(){
        List<String> phoneNumbers =given().accept("application/json")
                .when().get("/employees")
                .thenReturn().jsonPath().get("items.phone_number");

        phoneNumbers.replaceAll(phone -> phone.replace(".","-"));

        System.out.println(phoneNumbers);
    }

    /**Task: Given accept type as Json
     * And pathparams is id
     * when user sends get request to locations
     * then user verifies that status is 200
     * and user verifies that location_id is 1700
     * and user verifies that postal_code is 98199
     * And user verifies that city Seattle
     * And user verifies that state_province is Washington
     */
    @Test
    public void test13(){
        given().accept("application/json")
                .pathParam("id", "1700")
                .when().get("locations/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().body("location_id",is(1700),
                "postal_code",is("98199"),
                "city",is("Seattle"),
                "state_province",is("Washington") )
                .log().all(true);
    }
    @Test
    public void test14(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", "1700")
                .when().get("locations/{id}");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("location_id",is(1700));
        response.then().assertThat().body("postal_code",is("98199"));
        response.then().assertThat().body("city",is("Seattle"));
        response.then().assertThat().body("state_province",is("Washington"));
        response.prettyPeek();

    }







}
