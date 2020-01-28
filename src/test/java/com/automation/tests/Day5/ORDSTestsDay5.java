package com.automation.tests.Day5;

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

public class ORDSTestsDay5 {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }


    /**
     * WARMUP
     * given path parameter is "/employees"
     * when user makes get request
     * then user verifies that status code is 200
     * and user verifies that average salary is grater that 5000
     */
    @Test
    @DisplayName("verify that average salary is grater that 5000")
    public void test1(){
        Response response = given().accept(ContentType.JSON).
                            when().get("/employees");

        List<Integer> salaries = response.jsonPath().getList("items.salary");

        System.out.println(salaries);

        int sum = 0;
        for (Integer each : salaries){
            sum+=each;
        }
        int avg = sum/salaries.size();


        assertTrue(avg>5000,"ERROR: average salary is less than $5000 :"+avg);




    }



}
