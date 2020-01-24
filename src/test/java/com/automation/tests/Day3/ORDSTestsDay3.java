package com.automation.tests.Day3;

import com.automation.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class ORDSTestsDay3 {

    @BeforeAll
    public void setup(){
        RestAssured.baseURI = ConfigurationReader.getProperty("ords.uri");
    }


}
