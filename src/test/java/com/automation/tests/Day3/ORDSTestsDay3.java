package com.automation.tests.Day3;

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.sql.Time;
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
        JsonPath jsonPath= given().accept("application/json").when().get("/employees").
                thenReturn().jsonPath();
        String namoOfEmployee = jsonPath.getString("items[0].last_name");
        System.out.println(namoOfEmployee);
    }



}
